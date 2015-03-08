package server.sceleton;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketCatcher extends Thread{

    private ServerSocket serverSocket;

    public SocketCatcher(ServerSocket serverSocket) {
        super("SocketCatcher");
        this.serverSocket = serverSocket;
        new ClientSocketsCreator().start();
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ChiefManager.clientSocketsManager.addToCreatorQueue(clientSocket);
        }
    }

}
