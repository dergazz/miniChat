package server.persistence;

import entity.InterfaceManager;
import entity.Message;
import entity.Pair;
import server.sceleton.ChiefManager;
import server.sceleton.ClientSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientsManager implements InterfaceManager<Client> {


//    private ClientsRemover clientsRemover = new ClientsRemover();
    private ClientsCreator clientsCreator = new ClientsCreator();
    private List<Client> list = new ArrayList<Client>();

    public ClientsManager() {
//        clientsRemover.start();
        clientsCreator.start();
    }

    public void addToCreatorQueue(ClientSocket clientSocket, String id) {
        clientsCreator.addToQueue(new Pair<ClientSocket, String>(clientSocket, id));
    }

    public Client getClientByID(String id) {
        List<Client> temp = new ArrayList<Client>(list);
        for (Client client : temp) {
            if (id.equals(client.getId())) return client;
        }
        return null;
    }

    public Client createClient(ClientSocket clientSocket, String id) {
        try {
            Client client = new Client(clientSocket, id);
            clientSocket.setClient(client);
            addElement(client);

//            client.setName(String.valueOf(id));
            client.addRoom(ChiefManager.roomsManager.getDefaultRoom());
            ChiefManager.roomsManager.getDefaultRoom().addElement(client);

            client.setOnline();

            System.out.println(client.getId() + " in. Total " + list.size() + ".");
            return client;
        } catch (IOException e) {
            return null;
        }
    }

    public boolean fillClientInfo(String id) {
        ChiefManager.dbManager.fillClient(getClientByID(id), id);
        return true;
    }

    public boolean addClientToRoom(Client client, Room room){
        room.addElement(client);
        client.addRoom(room);
        return true;
    }

    public void escapeRoom(Client client, Room room) {
        client.removeRoom(room);
        ChiefManager.roomsManager.removeClientFromRoom(client, room);
    }

    public void changeName(Client client, String name) {
        client.setName(name);
        ChiefManager.dbManager.refresh(client);
    }

//    public void sendMessage(ClientSocket clientSocket, String text) throws IOException {
////        clientSocket.getMessage();
//        clientSocket.writeOut(text);
//    }
//    public void sendMessage(ClientSocket clientSocket, String text) throws IOException {
////        clientSocket.getMessage();
//        clientSocket.writeOut(text);
//    }

//    public void sendMessage(Client client, String text) throws IOException {
//        sendMessage(client.getClientSocket(), text);
//    }
//    public void sendMessage(Client client, String text) throws IOException {
//        sendMessage(client.getClientSocket(), text);
//    }

    public void onConnectionBreak(Client client) {
        client.setOffline();
        removeElement(client);
//        clientsRemover.addToQueue(client);
    }

    @Override
    public synchronized void addElement(Client element) {
        list.add(element);
    }

    @Override
    public synchronized void removeElement(Client element) {
        List<Room> roomList = element.getRoomList();
        for (Room room : roomList) {
            ChiefManager.roomsManager.removeClientFromRoom(element, room);
        }

        list.remove(element);
        System.out.println(element.getId() + " is out. Total " + list.size() + ".");
    }

    @Override
    public synchronized int getSize() {
        return 0;
    }

    @Override
    public synchronized boolean isEmpty() {
        return false;
    }

    public void sendMessage(Client client, Message message) {
        client.getMessage(message);
    }
}
