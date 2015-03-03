package server.sceleton;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketCatcher extends Thread{

    private ServerSocket serverSocket;
    private SocketAcceptor socketAcceptor;


    public SocketCatcher(ServerSocket serverSocket) {
        super("SocketCatcher");
        this.serverSocket = serverSocket;
        socketAcceptor = new SocketAcceptor();
        socketAcceptor.start();
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
            socketAcceptor.addToAcceptQueue(clientSocket);
        }
    }

}
