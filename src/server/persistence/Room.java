package server.persistence;

import entity.ServerMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Room {

    private String id;
    private String name;
    private Sender sender;
    //    private ArrayList<Client> list = new ArrayList<Client>();
    private List<Client> list = new CopyOnWriteArrayList<Client>();


    public Room() {
        this("defaultroomname");
    }

    public Room(String name) {
        this.name = name;
//        sender =
        HeapSenders.addRoomToSender(this);
    }

    public void addClient(Client client) {
        list.add(client);
    }

    public void removeClient(Client client) {
        list.remove(client);

        if (name.startsWith("PrivateRoom")) {
            for (Client aClient : list) {
                if (!aClient.equals(client)) {
                    aClient.getClientSocket().sendServerMessage(new ServerMessage(aClient.getClientSocket(), "SRV[escroom]<" + name + ">" + client.getName() + " escapes the room " + name + "."));
                    aClient.getRoomList().remove(this);
                    list.remove(aClient);
                }
            }

            sender.removeRoom(this);
            HeapRooms.removeRoom(this);
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return list.size();
    }

    public List<Client> getList() {
        return list;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Sender getSender() {
        return sender;
    }
}
