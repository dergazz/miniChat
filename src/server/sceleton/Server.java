package server.sceleton;

import server.persistence.HeapClients;
import server.persistence.HeapRooms;
import server.persistence.HeapSenders;
import server.persistence.Room;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private int port;
    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
    }

    public void init() {
        HeapSenders.init();
        HeapRooms.init();
        HeapServerSenders.init();
        HeapClients.init();


    }

    public void loop() throws IOException {

        SocketCatcher socketCatcher = new SocketCatcher(serverSocket);
        socketCatcher.start();
    }

}
