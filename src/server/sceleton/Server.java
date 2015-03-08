package server.sceleton;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void init() {
        //загрузка всего
        ChiefManager.init();
        SocketCatcher socketCatcher = new SocketCatcher(serverSocket);
        socketCatcher.start();
    }


}
