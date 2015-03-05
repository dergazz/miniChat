package server.persistence;

import entity.Addresser;
import entity.Message;
import entity.Recipient;
import entity.Resender;
import server.sceleton.ClientSocket;
import server.sceleton.ClientSocketsManager;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client implements Recipient, Addresser{

    private ClientSocket clientSocket;
    private int id;
    private String name;
    private boolean isLogin;
    private List<Room> roomList = new CopyOnWriteArrayList<Room>();


    public Client(ClientSocket clientSocket, int id) throws IOException {
        this.id = id;
        this.clientSocket = clientSocket;
        name = String.valueOf(id);
        isLogin = false;
    }

    public ClientSocket getClientSocket() {
        return clientSocket;
    }

    public void setOnline() {
        isLogin = true;
    }

    public void setOffline() {
        isLogin = false;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public synchronized void send(Message message) {
        try {
            clientSocket.writeOut(message.toString());
        } catch (IOException e) {
            Manager.clientSocketsManager.onConnectionBreak(clientSocket);
        }
    }

    @Override
    public void setResender(Resender resender) {
        clientSocket.setResender(resender);
    }

    @Override
    public Resender getResender() {
        return clientSocket.getResender();
    }

    public synchronized void addRoom(Room room) {
        roomList.add(room);
    }

    public synchronized void removeRoom(Room room) {
        roomList.remove(room);
    }

    public synchronized List<Room> getRoomList() {
        return roomList;
    }
}
