package server.persistence;

import entity.Message;
import entity.Queue;
import server.sceleton.ClientSocket;
import server.sceleton.ClientSocketManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sender extends Thread{

    private Queue<Message> queue = new Queue<Message>();
    private List<Room> roomList = new ArrayList<Room>();

    public Sender() {
        super("Sender");
    }

    public void addMessage(Message message) {
//        System.out.println("msg added");
        queue.add(message);
    }

    public void addRoom(Room room) {
        roomList.add(room);
    }

    public void removeRoom(Room room) {
        roomList.remove(room);
        HeapSenders.checkSender(this);

    }

    public int getListSize() {
        return roomList.size();
    }

    public boolean listIsEmpty() {
        return roomList.isEmpty();
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            Message message = queue.remove();
            List<Client> clientList = message.getRoom().getList();
            for (Client client :clientList) {
                try {
                    client.getClientSocket().writeOut("[" + message.getRoom().getName() + "] " + message.toString());
                } catch (IOException e) {
                    ClientSocketManager.onConnectionBreak(client.getClientSocket());
                }
            }


        }
    }
}
