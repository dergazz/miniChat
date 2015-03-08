package server.persistence;

import entity.*;
import server.sceleton.ChiefManager;
import server.sceleton.Resender;

import java.util.ArrayList;
import java.util.List;

public class Room implements Recipient, InterfaceManager<Client> {

    private String name;
    private Resender sender;
    private List<Client> list = new ArrayList<Client>();

    public Room(String name) {
        this.name = name;
        ChiefManager.roomResendersManager.addRecipientToResender(this);
    }

    @Override
    public synchronized void addElement(Client element) {
        list.add(element);
    }

    @Override
    public synchronized void removeElement(Client element) {
        list.remove(element);
    }

    @Override
    public synchronized int getSize() {
        return list.size();
    }

    @Override
    public synchronized boolean isEmpty() {
        return list.isEmpty();
    }

    public  List<Client> getList() {
        List<Client> clients = new ArrayList<Client>(list);
        return clients;
    }

    public boolean clearList() {
        list = new ArrayList<Client>();
        return true;
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
    public void getMessage(Message message) {
        List<Client> clientList = list;
        for (Client client :clientList) {
//            try {
//                ChiefManager.clientsManager.sendMessage(client, message.toString());
                ChiefManager.clientsManager.sendMessage(client, message);
//                client.getMessage(message);
//            } catch (IOException e) {
//                ChiefManager.clientsManager.onConnectionBreak(client);
//            }
        }
    }

}
