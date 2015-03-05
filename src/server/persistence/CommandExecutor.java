package server.persistence;


import entity.*;
import server.sceleton.ClientSocket;

public class CommandExecutor {

    public static String cmdRegister(ClientSocket clientSocket, String string) {
        Pair<String, String> logAndPass = Parser.parsePair(string, 4);
        if (logAndPass.getKey() == null) {
            return "Wrong command.";
        }
        int id = Integer.valueOf(logAndPass.getKey());
        String pass = logAndPass.getValue();
        if (pass.contains(" ")) {
            return "Login or password contains space symbol.";
        }
        if (Manager.dbManager.clientIDExist(id)) return "ID already exists.";

        Manager.dbManager.addIDAndPass(id, pass);
        Manager.clientsManager.addToCreatorQueue(clientSocket, id);
        return "User success registered.";
    }

    public static String cmdLogin(ClientSocket clientSocket, String string) {
        Pair<String, String> logAndPass = Parser.parsePair(string, 4);
        if (logAndPass.getKey() == null) {
            return "Wrong command.";
        }
        int id = Integer.valueOf(logAndPass.getKey());
        String pass = logAndPass.getValue();
        if (pass.contains(" ")) {
            return "Login or password contains space symbol.";
        }
        if (!Manager.dbManager.clientIDExist(id)) {
            return "ID not exists.";
        }
        if (Manager.clientsManager.getClientByID(id) != null) {
            return "ID is online.";
        }
        if (!Manager.dbManager.checkPassword(id, pass)) {
            return "Wrong password.";
        }
        Client client = Manager.clientsManager.createClient(clientSocket, id);
        if (client == null) {
            return "Problem..";
        }
        Manager.clientsManager.fillClientInfo(id);
        return "Success login.";

    }

    public static String cmdSendMessageToRoom(ClientSocket clientSocket, String string) {
        Pair<String, String> roomAndText = Parser.parsePair(string, 4);
        if (roomAndText.getKey() == null) {
            return "Wrong command.";
        }
        Room myRoom = Manager.roomsManager.getRoomByName(roomAndText.getKey());
        if (myRoom == null) {
            return "Room is not exist.";
        }
//        SendersManager.addMessage(myRoom, clientSocket.getClient(), roomAndText.getValue());

        Message message = new RoomMessage(clientSocket.getClient(), myRoom, roomAndText.getValue());
        Manager.roomResendersManager.addMessage(message);

        return"OK.";
    }

    public static String cmdCreatePrivateRoom(ClientSocket clientSocket, String string) {
        String clientID = Parser.parseOne(string, 10);
        if (clientID == null) {
            return "Wrong name (space).";
        }
        int ID = Integer.parseInt(clientID);
        if (ID == clientSocket.getClient().getId()) {
            return "You try to chat with yourself.";
        }
        Client toClient = Manager.clientsManager.getClientByID(ID);
        if (toClient == null) {
            return "There is no user with this name";
        }
        if (Manager.roomsManager.getRoomByName("PrivateRoom_" + clientSocket.getClient().getId() + "_" + ID) != null) {
            return "You already can chat with" + ID + " in " + "PrivateRoom_" + clientSocket.getClient().getId() + "_" + ID + ".";
        } else if (Manager.roomsManager.getRoomByName("PrivateRoom_" + ID + "_" + clientSocket.getClient().getId()) != null) {
            return "You already can chat with " + ID + " in " + "PrivateRoom_" + ID + "_" + clientSocket.getClient().getId() + ".";
        }
        Room room = Manager.roomsManager.createRoom("PrivateRoom_" + clientSocket.getClient().getId() + "_" + ID);
        Manager.clientsManager.addClientToRoom(clientSocket.getClient(), room);
        Manager.clientsManager.addClientToRoom(toClient, room);

//        ServerSendersManager.addServerMessage(toClient, clientSocket.getClient().getId() + " wants to chat with you (" + room.getName() + ").");

        Message message = new ServerMessage(toClient, clientSocket.getClient().getId() + " wants to chat with you (" + room.getName() + ").");
        Manager.roomResendersManager.addMessage(message);

        return "Room " + room.getName() + " created.";

    }

    public static String cmdCreateRoom(ClientSocket clientSocket, String string) {
        String nameRoom = Parser.parseOne(string, 12);
        if (nameRoom == null) {
            return "Wrong name (space).";
        }
        Room room = Manager.roomsManager.createRoom(nameRoom);
        Manager.clientsManager.addClientToRoom(clientSocket.getClient(), room);
        return "Room " + room.getName() + " created.";
    }

    public static String cmdJoinRoom(ClientSocket clientSocket, String string) {
        String roomName = Parser.parseOne(string, 11);
        if (roomName == null) {
            return "Wrong name (space).";
        }
        Client client = clientSocket.getClient();
        Room room = Manager.roomsManager.getRoomByName(roomName);
        if (room == null) {
            return "Room not found.";
        }
        Manager.clientsManager.addClientToRoom(client, room);
        return "Now client " + client.getName() + " add " + roomName + " in roomList.";

    }

    public static String cmdEscapeRoom(ClientSocket clientSocket, String string) {
        String nameRoom = Parser.parseOne(string, 9);
        Client client = clientSocket.getClient();
        Room room = Manager.roomsManager.getRoomByName(client, nameRoom);
        if (room == null) {
            return "Room not found.";
        }
        Manager.clientsManager.escapeRoom(client, room);
        return client.getName() + " escapes " + nameRoom + ".";
    }

    public static String cmdSendMessageToDefaultRoom(ClientSocket clientSocket, String string) {
        String text = string.substring(4).trim();
        if (text.length() < 2) {
            return "Wrong text";
        }
        Room defaultRoom = Manager.roomsManager.getDefaultRoom();


//        SendersManager.addMessage(defaultRoom, clientSocket.getClient(), text);

        Message message = new RoomMessage(clientSocket.getClient(),defaultRoom, text);
        Manager.roomResendersManager.addMessage(message);

        return "OK.";
    }

    public static String cmdSetName(ClientSocket clientSocket, String string) {
        String name = Parser.parseOne(string, 9);
        Client client = clientSocket.getClient();
        Manager.clientsManager.changeName(client,name);
        return "Name changed.";
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
                .append("/help ")
                .append("\n")
                .append("/exit");
        return stringBuilder.toString();
    }

    public static void cmdExit(ClientSocket clientSocket) {
        Manager.clientSocketsManager.clientSocketsRemover.addToQueue(clientSocket);
    }

    public static String cmdMyRooms(ClientSocket clientSocket) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("My rooms:").append("\n");
        for (Room room :clientSocket.getClient().getRoomList()) {
            stringBuilder.append(room.getName())
                    .append(" (")
                    .append(room.getSize())
                    .append(")")
                    .append("\n");
        }
        stringBuilder.append("Total ")
                .append(clientSocket.getClient().getRoomList().size());
        return stringBuilder.toString();
    }

    public static String cmdAllRooms() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("All rooms:")
                .append("\n");
        for (Room room : Manager.roomsManager.getRoomArrayList()) {
            stringBuilder.append(room.getName())
                    .append(" (")
                    .append(room.getSize())
                    .append(")")
                    .append("\n");
        }
        stringBuilder.append("Total ")
                .append(Manager.roomsManager.getRoomArrayList().size());
        return stringBuilder.toString();
    }
}
