package server.sceleton;

import server.persistence.ClientsManager;
import server.persistence.Manager;
import server.persistence.RoomsManager;
//import server.persistence.SendersManager;

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
        //загрузка всего
        Manager.init();
    }

    public void loop() throws IOException {

        SocketCatcher socketCatcher = new SocketCatcher(serverSocket);
        socketCatcher.start();
    }

}
