package server.persistence;

import entity.Pair;
import server.sceleton.ClientSocket;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientsManager {


    private ClientsRemover clientsRemover = new ClientsRemover();
    private ClientsCreator clientsCreator = new ClientsCreator();
    private List<Client> list = new CopyOnWriteArrayList<Client>();

    public ClientsManager() {
        clientsRemover.start();
        clientsCreator.start();
    }

    public void addToCreatorQueue(ClientSocket clientSocket, Integer id) {
        clientsCreator.addToQueue(new Pair<ClientSocket, Integer>(clientSocket, id));
    }

    public void addClient(Client client) {
        list.add(client);
    }

    public Client getClientByID(int id) {
        for (Client client :list) {
            if (id == client.getId()) return client;
        }
        return null;
    }

    public Client createClient(ClientSocket clientSocket, int id) {
        try {
            Client client = new Client(clientSocket, id);
            clientSocket.setClient(client);
            addClient(client);

            client.addRoom(Manager.roomsManager.getDefaultRoom());
            Manager.roomsManager.getDefaultRoom().addClient(client);

            client.setOnline();

            System.out.println(client.getId() + " in. Total " + list.size() + ".");
            return client;
        } catch (IOException e) {
            return null;
        }
    }

    public boolean fillClientInfo(int id) {
        Manager.dbManager.fillClient(getClientByID(id), id);
        return true;
    }

    public void removeClient(Client client) {
        List<Room> roomList = client.getRoomList();

        for (Room room :roomList) {
//            escapeRoom(client, room);
            Manager.roomsManager.removeClientFromRoom(client, room);
        }
//        client.setRoomList(null);
        list.remove(client);
        System.out.println(client.getId() + " is out. Total " + list.size() + ".");
    }

    public boolean addClientToRoom(Client client, Room room){
        room.addClient(client);
        client.addRoom(room);
        return true;
    }

    public void escapeRoom(Client client, Room room) {
        client.removeRoom(room);
        Manager.roomsManager.removeClientFromRoom(client, room);
    }

    public void changeName(Client client, String name) {
        client.setName(name);
        Manager.dbManager.refresh(client);
    }

    public void send(ClientSocket clientSocket, String text) throws IOException {
//        clientSocket.send();
        clientSocket.writeOut(text);
    }

    public void send(Client client, String text) throws IOException {
        send(client.getClientSocket(), text);
    }

    public void onConnectionBreak(Client client) {
        client.setOffline();
        clientsRemover.addToQueue(client);
    }
}
