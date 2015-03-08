package server.persistence;

import entity.*;
import server.sceleton.ChiefManager;
import server.sceleton.ClientSocket;
import server.sceleton.Resender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Client implements Recipient, Addresser, Removable{

    private ClientSocket clientSocket;
    private String id;
    private String name;
    private boolean isLogin;
//    private List<Room> roomList = new CopyOnWriteArrayList<Room>();
    private List<Room> roomList = new ArrayList<Room>();


    public Client(ClientSocket clientSocket, String id) throws IOException {
        this.id = id;
        this.clientSocket = clientSocket;
        name = "defaultname";
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

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public synchronized void addRoom(Room room) {
        roomList.add(room);
    }

    public synchronized void removeRoom(Room room) {
        roomList.remove(room);
    }

    public synchronized List<Room> getRoomList() {
        return new ArrayList<Room>(roomList);
    }

    @Override
    public void getMessage(Message message) {
            clientSocket.getMessage(message);
    }

    @Override
    public void setResender(Resender resender) {
        clientSocket.setResender(resender);
    }

    @Override
    public Resender getResender() {
        return clientSocket.getResender();
    }

    @Override
    public void removeIt() {
        ChiefManager.clientsManager.removeElement(this);
    }

}
