package server.sceleton;

import server.persistence.Manager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketCatcher extends Thread{

    private ServerSocket serverSocket;
    private ClientSocketsCreator clientSocketsCreator;


    public SocketCatcher(ServerSocket serverSocket) {
        super("SocketCatcher");
        this.serverSocket = serverSocket;
        clientSocketsCreator = new ClientSocketsCreator();
        clientSocketsCreator.start();
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
            Manager.clientSocketsManager.addToCreatorQueue(clientSocket);
        }
    }

}
