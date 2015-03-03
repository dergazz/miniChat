package server.persistence;

import server.sceleton.ClientSocket;

import java.io.IOException;

public class Client {

    ClientSocket clientSocket;
    int id;

    public Client(ClientSocket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
    }

}
