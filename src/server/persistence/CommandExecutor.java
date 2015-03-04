package server.persistence;


import entity.Message;
import entity.Pair;
import entity.Parser;
import entity.ServerMessage;
import server.sceleton.ClientSocket;
import server.sceleton.ClientSocketRemover;
import server.sceleton.HeapServerSenders;
import server.sceleton.database.DBManager;

public class CommandExecutor {

    public static String cmdInfo(ClientSocket clientSocket){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SRV[ans/info]")
                .append("<")
                .append(clientSocket.getServerSender())
                .append(" ")
                .append(clientSocket.getServerSender().isAlive())
                .append(">")
                .append("\n")
                .append(DBManager.getAllEntries())
                .append("\n")
                .append("HeapRoomSize: ")
                .append(HeapRooms.size())
                .append("\n");


//        for (int i = 0; i < HeapRooms.size(); i++) {
//            stringBuilder.append(HeapRooms.getRoomByNumber(i).getName())
//                    .append(" (")
//                    .append(HeapRooms.getRoomByNumber(i).getSize())
//                    .append(").")
//                    .append("\n");
//        }



//        stringBuilder.append(HeapRooms.getDefaultRoom().getName())
//                .append(" (")
//                .append(HeapRooms.getDefaultRoom().getSize())
//                .append(").")
//                .append("\n");

        Client client = clientSocket.getClient();
//        Client client = ClientHandler.getClientBySocket(clientSocket);
        if (client != null) {
            stringBuilder.append(client.getName());
        }
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    public static String cmdSenders(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HeapServerSenders.getList())
                .append("\n")
                .append("Senders:")
                .append("\n")
                .append(HeapSenders.getList())
                .append("\n");
        return stringBuilder.toString();
    }

    public static String cmdRegister(ClientSocket clientSocket, String string) {
        Client client = HeapClients.getClientBySocket(clientSocket);
        if ((client != null) && client.isOnline()) {
            return "SRV You are already online.";
        }
        Pair logAndPass = Parser.parsePair(string, 4);
        if (logAndPass.getKey() == null) {
            return "SRV Wrong command.";
        }
        int id = Integer.valueOf(logAndPass.getKey());
        String pass = logAndPass.getValue();
        if (pass.contains(" ")) {
            return "SRV Login or password contains space symbol.";
        }
        if (DBManager.clientIDExist(id)) return "SRV ID already exists.";

        if (ClientManager.createClient(clientSocket, id)) {
            client = clientSocket.getClient();
//            client = ClientHandler.getClientBySocket(clientSocket);
            DBManager.addClient(client, pass);
            return "SRV User success registered.";

        } else return "SRV Something wrong.";
    }

    public static String cmdOnline() {
        return HeapClients.getOnline() + "Total " + HeapClients.getNumber() + ".";
    }

    public static String cmdLogin(ClientSocket clientSocket, String string) {
        Client client = HeapClients.getClientBySocket(clientSocket);
        if ((client != null) && client.isOnline()) {
            return "SRV You are already online.";
        }
        Pair logAndPass = Parser.parsePair(string, 4);
        if (logAndPass.getKey() == null) {
            return "SRV Wrong command.";
        }
        int id = Integer.valueOf(logAndPass.getKey());
        String pass = logAndPass.getValue();
        if (pass.contains(" ")) {
            return "SRV Login or password contains space symbol.";
        }
        if (!DBManager.clientIDExist(id)) {
            return "SRV ID not exists.";
        }
        if (HeapClients.getClientByID(id) != null) {
            return "SRV ID is online.";
        }
        if (!DBManager.checkPassword(id, pass)) {
            return "SRV Wrong password.";
        } else {
            if (client != null) {
                HeapClients.removeClient(client);
            }
            ClientManager.createClient(clientSocket, id);
            ClientManager.fillClientInfo(id);
            return "SRV Success login.";
        }
    }

    public static String cmdSetName(ClientSocket clientSocket, String string) {
        String nameRoom = Parser.parseOne(string, 9);
        clientSocket.getClient().setName(nameRoom);
        DBManager.refresh(clientSocket.getClient());
        return "SRV Name changed.";
    }

    public static String cmdCreateRoom(ClientSocket clientSocket, String string) {
        String nameRoom = Parser.parseOne(string, 12);
        if (nameRoom == null) {
            return "SRV Wrong name (space).";
        }
        Room room = new Room(nameRoom);
        HeapRooms.addRoom(room);
//        HeapSenders.addRoomToSender(room);
        ClientManager.addClientToRoom(clientSocket.getClient(), room);
        return "SRV Room " + room.getName() + " created.";
    }

    public static String cmdSendMessageToDefaultRoom(ClientSocket clientSocket, String string) {
        String text = string.substring(4).trim();
        if (text.length() > 0) {
            Room defaultRoom = HeapRooms.getDefaultRoom();
//            System.out.println(defaultRoom.getName() + " OK");
//            System.out.println(defaultRoom.getSender() + " sOK");
//            System.out.println(clientSocket.getClient().getName() +" id" + clientSocket.getClient().getId() + " sOK");
            defaultRoom.getSender().addMessage(new Message(clientSocket.getClient(), text, defaultRoom));
            return "SRV OK.";
        } else {
            return "SRV Wrong text";
        }
    }

    public static String cmdAddClientToRoom(ClientSocket clientSocket, String string) {
        String roomName = Parser.parseOne(string, 11);
        if (roomName == null) {
            return "SRV Wrong name (space).";
        }
        Client client = clientSocket.getClient();
        Room room = HeapRooms.getRoomByName(roomName);
        if (room == null) {
            return "SRV Room not found.";
        } else {
            ClientManager.addClientToRoom(client, room);
            return "SRV Now client " + client.getName() + " add " + roomName + " in roomList.";
        }
    }

    public static String cmdEscapeRoom(ClientSocket clientSocket, String string) {
        String nameRoom = Parser.parseOne(string, 9);
        Room myRoom = null;
        Client client = clientSocket.getClient();
        for (Room aRoom: client.getRoomList()) {
            if (aRoom.getName().equalsIgnoreCase(nameRoom)) {
                myRoom = aRoom;
                break;
            }
        }
        if (myRoom != null) {
            ClientManager.escapeRoom(client, myRoom);
            return client.getName() + " escapes " + nameRoom + ".";
        } else {
            return "SRV Not found this room";
        }
    }

    public static String cmdSendMessageToRoom(ClientSocket clientSocket, String string) {
        Pair roomAndText = Parser.parsePair(string, 4);
        if (roomAndText.getKey() == null) {
            return "SRV Wrong command.";
        }
        Room myRoom = null;
        Client client = clientSocket.getClient();
        for (Room room: client.getRoomList()) {
            if (room.getName().equalsIgnoreCase(roomAndText.getKey())) {
                myRoom = room;
            }
        }
        if (myRoom != null) {
//            myRoom.getRoomSender().addMessageToRoom(new Message(client, roomAndText.getValue()));
            Sender sender = myRoom.getSender();
            Message message = new Message(client, roomAndText.getValue(), myRoom);
            sender.addMessage(message);
//            myRoom.getSender().addMessage(new Message(client, roomAndText.getValue(), myRoom));
            return"SRV OK.";
        } else {
            return "SRV Not find this room";
        }
    }

    public static String cmdCreatePrivateRoom(ClientSocket clientSocket, String string) {
        String clientID = Parser.parseOne(string, 10);
        if (clientID == null) {
            return "SRV Wrong name (space).";
        }
        int ID = Integer.parseInt(clientID);
        if (ID == clientSocket.getClient().getId()) {
            return "SRV You try to chat with yourself.";
        }
        Client toClient = HeapClients.getClientByID(ID);
        if (toClient == null) {
            return "SRV There is no user with this name";
        }
        if (HeapRooms.getRoomByName("PrivateRoom_" + clientSocket.getClient().getId() + "_" + ID) != null) {
            return "SRV You already can chat with" + ID + " in " + "PrivateRoom_" + clientSocket.getClient().getId() + "_" + ID + ".";
        } else if (HeapRooms.getRoomByName("PrivateRoom_" + ID + "_" + clientSocket.getClient().getId()) != null) {
            return "SRV You already can chat with " + ID + " in " + "PrivateRoom_" + ID + "_" + clientSocket.getClient().getId() + ".";
        }

        Room room = new Room("PrivateRoom_" + clientSocket.getClient().getId() + "_" + ID);
        HeapRooms.addRoom(room);
//        HeapSenders.addRoomToSender(room);
        ClientManager.addClientToRoom(clientSocket.getClient(), room);
        ClientManager.addClientToRoom(toClient, room);
        toClient.getClientSocket().getServerSender().addServerMessage(new ServerMessage(toClient.getClientSocket(), "SRV[createroom]<" + room.getName() + ">" + clientSocket.getClient().getId() + " wants to chat with you (" + room.getName() + ")."));
        return "SRV Room " + room.getName() + " created.";

    }

    public static String cmdHelp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/reg name password ")
                .append("\n")
                .append("/log name password")
                .append("\n")
                .append("/createroom roomName")
                .append("\n")
                .append("/removeroom roomName")
                .append("\n")
                .append("/addtoroom roomName")
                .append("\n")
                .append("/escroom roomName")
                .append("\n")
                .append("/snd message")
                .append("\n")
                .append("/chatwith name")
                .append("\n")
                .append("/str roomName message")
                .append("\n")
                .append("/online")
                .append("\n")
                .append("/senders")
                .append("\n")
                .append("/help ")
                .append("\n")
                .append("/exit");
        return stringBuilder.toString();
    }

    public static void cmdExit(ClientSocket clientSocket) {
        ClientSocketRemover.addClientSocketToRemove(clientSocket);
    }
}
