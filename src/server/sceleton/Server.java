package server.sceleton;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private int port;
    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
    }

    public void loop() throws IOException {
        SocketCatcher socketCatcher = new SocketCatcher(serverSocket);
        socketCatcher.start();
    }

}
