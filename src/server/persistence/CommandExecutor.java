package server.persistence;


import entity.Pair;
import entity.Parser;
import server.sceleton.ClientSocket;
import server.sceleton.HeapServerSenders;
import server.sceleton.database.DBHandler;

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
                .append(DBHandler.getAllEntries())
                .append("\n");
        Client client = clientSocket.getClient();
//        Client client = ClientHandler.getClientBySocket(clientSocket);
        if (client != null) {
            stringBuilder.append(client.getName());
        }
        return stringBuilder.toString();
    }

    public static String cmdSenders(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HeapServerSenders.getList());
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
        if (DBHandler.clientIDExist(id)) return "SRV ID already exists.";

        if (ClientHandler.createClient(clientSocket, id)) {
            client = clientSocket.getClient();
//            client = ClientHandler.getClientBySocket(clientSocket);
            DBHandler.addClient(client, pass);
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
        if (!DBHandler.clientIDExist(id)) {
            return "SRV ID not exists.";
        }
        if (HeapClients.getClientByID(id) != null) {
            return "SRV ID is online.";
        }
        if (!DBHandler.checkPassword(id, pass)) {
            return "SRV Wrong password.";
        } else {
            if (client != null) {
                HeapClients.removeClient(client);
            }
            ClientHandler.createClient(clientSocket, id);
            ClientHandler.fillClientInfo(id);
            return "SRV Success login.";
        }
    }

    public static String cmdSetName(ClientSocket clientSocket, String string) {
        String nameRoom = Parser.parseOne(string, 9);
        clientSocket.getClient().setName(nameRoom);
        DBHandler.refresh(clientSocket.getClient());
        return "SRV Name changed.";
    }
}
