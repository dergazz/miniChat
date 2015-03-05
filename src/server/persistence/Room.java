package server.persistence;

import entity.Message;
import entity.Recipient;
import entity.Resender;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Room implements Recipient {

    private String name;
    private Resender sender;
    private List<Client> list = new CopyOnWriteArrayList<Client>();

    public Room(String name) {
        this.name = name;
        Manager.roomResendersManager.addRecipientToResender(this);
    }

    public void addClient(Client client) {
        list.add(client);
    }

    public void removeClient(Client client) {
        list.remove(client);
    }

    public int getSize() {
        return list.size();
    }

    public List<Client> getList() {
        return list;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setResender(Resender sender) {
        this.sender = sender;
    }

    @Override
    public Resender getResender() {
        return sender;
    }

    @Override
    public synchronized void send(Message message) {

        List<Client> clientList = list;
        for (Client client :clientList) {
            try {
                Manager.clientsManager.send(client, message.toString());
            } catch (IOException e) {
                Manager.clientsManager.onConnectionBreak(client);
            }
        }
    }
}
