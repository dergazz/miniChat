package server.persistence;

import server.sceleton.ClientSocket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class HeapClients {

//    private static ArrayList<Client> list = new ArrayList<Client>();
    private static List<Client> list = new CopyOnWriteArrayList<Client>();


    public static void addClient(Client client) {
        list.add(client);
    }

    public static void removeClient(Client client) {
        list.remove(client);
    }

    public static Client getClientBySocket(ClientSocket clientSocket) {
        for (Client client :list) {
            if (clientSocket == client.getClientSocket()) return client;
        }
        return null;
    }

    public static Client getClientByID(int id) {
        for (Client client :list) {
            if (id == client.getId()) return client;
        }
        return null;
    }

    public static String getOnline() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Client client :list) {
            if (client.isOnline()) stringBuilder.append(client.getId()).append("\n");
        }
        return stringBuilder.toString();
    }

    public static int getNumber() {
        int count = 0;
        for (Client client :list) {
            count++;
        }
        return count;
    }

}
