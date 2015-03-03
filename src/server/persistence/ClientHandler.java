package server.persistence;

import server.sceleton.ClientSocket;
import server.sceleton.database.DBHandler;

import java.io.IOException;

public class ClientHandler {

    public static boolean createClient(ClientSocket clientSocket, int id) {
        try {
            Client client = new Client(clientSocket, id);
            clientSocket.setClient(client);
            HeapClients.addClient(client);


            System.out.println(client.getId() + " in. Total " + HeapClients.getNumber() + ".");
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean fillClientInfo(int id) {
        DBHandler.fillClient(HeapClients.getClientByID(id), id);
        return true;
    }

    public static boolean removeClient(Client client) {
        HeapClients.removeClient(client);
        System.out.println(client.getId() + " is out. Total " + HeapClients.getNumber() + ".");
        return true;
    }

//    public static Client getClientBySocket(ClientSocket clientSocket) {
//        return HeapClients.getClientBySocket(clientSocket);
//    }

}
