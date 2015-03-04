package server.persistence;

import server.sceleton.ClientSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client {

    private ClientSocket clientSocket;
    private int id;
    private String name;
    private int age;
    private boolean isLogin;
//    private List<Room> roomList = new ArrayList<Room>();
    private List<Room> roomList = new CopyOnWriteArrayList<Room>();


    public Client(ClientSocket clientSocket, int id) throws IOException {
        this.id = id;
        this.clientSocket = clientSocket;
        name = "defaultname";
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

    public boolean isOnline() {
        return isLogin;
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

    public void addRoom(Room room) {
        roomList.add(room);
//        getRoomList().add(room);
    }

    public void removeRoom(Room room) {
//        getRoomList().remove(room);
        roomList.remove(room);
    }

    public synchronized List<Room> getRoomList() {
        return roomList;
    }
}
