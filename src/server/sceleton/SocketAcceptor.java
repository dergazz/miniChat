package server.sceleton;

import entity.Queue;

import java.net.Socket;

public class SocketAcceptor extends Thread{

    private Queue<Socket> queue = new Queue<Socket>();
//    private ClientSocketHandler clientSocketHandler = new ClientSocketHandler();

    public SocketAcceptor() {
        super("SocketAcceptor");
    }

    public void addToAcceptQueue(Socket socket) {
        queue.add(socket);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            Socket clientSocket = queue.remove();
            ClientSocketHandler.onConnection(clientSocket);
        }
    }

}
