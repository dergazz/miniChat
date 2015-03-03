package server.sceleton;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    int port;
    ServerSocket serverSocket = new ServerSocket(port);

    public Server(int port) throws IOException {
        this.port = port;
    }

    public void loop() throws IOException {
        SocketCatcher socketCatcher = new SocketCatcher(serverSocket);
        socketCatcher.loop();
    }

}
