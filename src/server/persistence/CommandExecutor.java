package server.persistence;


import entity.*;
import server.sceleton.ChiefManager;
import server.sceleton.ClientSocket;

public class CommandExecutor {

    public static String cmdRegister(ClientSocket clientSocket, String string) {
        Pair<String, String> logAndPass = Parser.parsePair(string, 4);
        if (logAndPass.getKey() == null) {
            return Code.WRONG_COMMAND;
        }
        String id = logAndPass.getKey();
        if (id.length() < 4) return Code.TOO_SHORT_ID;

        String pass = logAndPass.getValue();

        if (pass.contains(" ")) {
            return Code.LOG_PASS_CONTAINS_SPACE;
        }
        if (pass.length() < 4) return Code.TOO_SHORT_PASS;


        if (ChiefManager.dbManager.clientIDExist(id)) return Code.ID_ALREADY_EXISTS;

        if (id.length() < 4) return Code.TOO_SHORT_ID;
        if (pass.length() < 4) return Code.TOO_SHORT_PASS;

        ChiefManager.dbManager.addIDAndPass(id, pass);
        ChiefManager.clientsManager.addToCreatorQueue(clientSocket, id);
        return Code.cmdRegister_SUCCESS;
    }

    public static String cmdLogin(ClientSocket clientSocket, String string) {
        Pair<String, String> logAndPass = Parser.parsePair(string, 4);
        if (logAndPass.getKey() == null) {
            return Code.WRONG_COMMAND;
        }
        String id = logAndPass.getKey();
        if (id.length() < 4) return Code.TOO_SHORT_ID;
        String pass = logAndPass.getValue();
        if (pass.contains(" ")) {
            return Code.LOG_PASS_CONTAINS_SPACE;
        }
        if (pass.length() < 4) return Code.TOO_SHORT_PASS;
        if (!ChiefManager.dbManager.clientIDExist(id)) {
            return Code.ID_NOT_EXISTS;
        }
        if (ChiefManager.clientsManager.getClientByID(id) != null) {
            return Code.ID_IS_ONLINE;
        }
        if (!ChiefManager.dbManager.checkPassword(id, pass)) {
            return Code.WRONG_PASSWORD;
        }

        Client client = ChiefManager.clientsManager.createClient(clientSocket, id);
        if (client == null) {
            return Code.CANT_CREATE_NEW_CLIENT;
        }
        ChiefManager.clientsManager.fillClientInfo(id);
        return Code.cmdLOGIN_SUCCESS;

    }

    public static String cmdSendMessageToRoom(ClientSocket clientSocket, String string) {
        Pair<String, String> roomAndText = Parser.parsePair(string, 4);
        if (roomAndText.getKey() == null) {
            return Code.WRONG_COMMAND;
        }
        String nameRoom = roomAndText.getKey();
        if (nameRoom.length() < 5) {
            return Code.TOO_SHORT_ROOM_NAME;
        }
        if (roomAndText.getValue().length() < 1) {
            return Code.TOO_SHORT_MESSAGE_LENGTH;
        }

        Room myRoom = ChiefManager.roomsManager.getRoomByName(nameRoom);
        if (myRoom == null) {
            return Code.ROOM_IS_NOT_EXISTS;
        }
//        SendersManager.addMessage(myRoom, clientSocket.getClient(), roomAndText.getValue());

        Message message = new RoomMessage(clientSocket.getClient(), myRoom, roomAndText.getValue());
        ChiefManager.roomResendersManager.addMessage(message);

        return Code.cmdSENDMESSAGETOROOM_SUCCESS;
    }

    public static String cmdCreatePrivateRoom(ClientSocket clientSocket, String string) {
        String clientID = Parser.parseOne(string, 10);
        if (clientID == null) {
            return Code.WRONG_COMMAND;
        }
        String ID = clientID;
        if (ID.equals(clientSocket.getClient().getId())) {
            return Code.TRY_TO_CHAT_WITH_YOURSELF;
        }
        Client toClient = ChiefManager.clientsManager.getClientByID(ID);
        if (toClient == null) {
            return Code.NO_USER_WITH_THIS_NAME;
        }
        if (ChiefManager.roomsManager.getRoomByName("PrivateRoom_" + clientSocket.getClient().getId() + "_" + ID) != null) {

            ChiefManager.serverResendersManager.addMessage(new ServerMessage(clientSocket.getClient(), "You already can chat with" + ID + " in " + "PrivateRoom_" + clientSocket.getClient().getId() + "_" + ID + "."));
            return Code.ROOM_IS_EXISTS;


        } else if (ChiefManager.roomsManager.getRoomByName("PrivateRoom_" + ID + "_" + clientSocket.getClient().getId()) != null) {

            ChiefManager.serverResendersManager.addMessage(new ServerMessage(clientSocket.getClient(), "You already can chat with " + ID + " in " + "PrivateRoom_" + ID + "_" + clientSocket.getClient().getId() + "."));
            return Code.ROOM_IS_EXISTS;
        }
        Room room = ChiefManager.roomsManager.createRoom("PrivateRoom_" + clientSocket.getClient().getId() + "_" + ID);
        ChiefManager.clientsManager.addClientToRoom(clientSocket.getClient(), room);
        ChiefManager.clientsManager.addClientToRoom(toClient, room);

//        ServerSendersManager.addServerMessage(toClient, clientSocket.getClient().getId() + " wants to chat with you (" + room.getName() + ").");

        Message message = new ServerMessage(toClient, clientSocket.getClient().getId() + " wants to chat with you (" + room.getName() + ").");

//        ChiefManager.roomResendersManager.addMessage(message);

        ChiefManager.serverResendersManager.addMessage(message);
        ChiefManager.serverResendersManager.addMessage(new ServerMessage(clientSocket.getClient(), "Room " + room.getName() + " created."));

        return Code.cmdCREATEPRIVATEROOM_SUCCESS;

    }

    public static String cmdCreateRoom(ClientSocket clientSocket, String string) {
        String nameRoom = Parser.parseOne(string, 12);
        if (nameRoom == null) {
            return Code.WRONG_COMMAND;
        }
        if (nameRoom.length() < 5) {
            return Code.TOO_SHORT_ROOM_NAME;
        }
        if (ChiefManager.roomsManager.getRoomByName(nameRoom) != null) {
            return Code.ROOM_IS_EXISTS;
        }
        Room room = ChiefManager.roomsManager.createRoom(nameRoom);
        ChiefManager.clientsManager.addClientToRoom(clientSocket.getClient(), room);

//        ChiefManager.serverResendersManager.addMessage(new ServerMessage(clientSocket.getClient(), "Room " + room.getName() + " created."));
//
//        ChiefManager.serverResendersManager.addMessage(new ServerMessage(clientSocket.getClient(), Code.ADD_TO_ROOM_SUCCESS));

        return Code.cmdCREATEROOM_SUCCESS;
    }

    public static String cmdJoinRoom(ClientSocket clientSocket, String string) {
        String roomName = Parser.parseOne(string, 11);
        if (roomName == null) {
            return Code.WRONG_COMMAND;
        }
        Client client = clientSocket.getClient();
        Room room = ChiefManager.roomsManager.getRoomByName(roomName);
        if (room == null) {
            return Code.ROOM_IS_NOT_EXISTS;
        }
        ChiefManager.clientsManager.addClientToRoom(client, room);

        ChiefManager.serverResendersManager.addMessage(new ServerMessage(client, "Now client " + client.getName() + " add " + roomName + " in roomList."));
        return Code.cmdJOINROOM_SUCCESS;

    }

    public static String cmdEscapeRoom(ClientSocket clientSocket, String string) {
        String nameRoom = Parser.parseOne(string, 9);
        Client client = clientSocket.getClient();
        Room room = ChiefManager.roomsManager.getRoomByName(client, nameRoom);
        if (room == null) {
            return Code.ROOM_IS_NOT_IN_CLIENT_ROOM_LIST;
        }
        ChiefManager.clientsManager.escapeRoom(client, room);
        ChiefManager.serverResendersManager.addMessage(new ServerMessage(client, client.getName() + " escapes " + nameRoom + "."));
        return Code.cmdESCAPEROOM_SUCCESS;
    }

    public static String cmdSendMessageToDefaultRoom(ClientSocket clientSocket, String string) {
        String text = string.substring(4).trim();
        if (text.length() < 1) {
            return Code.TOO_SHORT_MESSAGE_LENGTH;
        }
        Room defaultRoom = ChiefManager.roomsManager.getDefaultRoom();


//        SendersManager.addMessage(defaultRoom, clientSocket.getClient(), text);

        Message message = new RoomMessage(clientSocket.getClient(),defaultRoom, text);
        ChiefManager.roomResendersManager.addMessage(message);

        return Code.cmdSENDMESSAGETOROOM_SUCCESS;
    }

    public static String cmdSetName(ClientSocket clientSocket, String string) {
        String name = Parser.parseOne(string, 9);
        Client client = clientSocket.getClient();
        ChiefManager.clientsManager.changeName(client,name);
        return Code.cmdSETNAME_SUCCESS;
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
//        ChiefManager.clientSocketsManager.clientSocketsRemover.addToQueue(clientSocket);
        ChiefManager.remover.addToQueue(clientSocket);
    }

    public static String cmdMyRooms(ClientSocket clientSocket) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nMy rooms:").append("\n");
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
        return ChiefManager.roomsManager.getRoomArrayList();
    }

    public static String cmdSenders() {

        return "\n" + ChiefManager.roomResendersManager.getListSenders() + "\n" + ChiefManager.serverResendersManager.getListSenders();
    }
}
