package server.persistence;

import server.sceleton.ClientSocket;

import java.io.IOException;

public class Client {

    private ClientSocket clientSocket;
    private int id;
    private String name;
    private int age;
    private boolean isLogin;

    public Client(ClientSocket clientSocket, int id) throws IOException {
        this.id = id;
        this.clientSocket = clientSocket;
        name = "defaultname";
        isLogin = true;
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
}
