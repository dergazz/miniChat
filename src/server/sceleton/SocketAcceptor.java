package server.sceleton;

import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class SocketAcceptor {

    Queue<Socket> queue = new LinkedList<Socket>();

    public void addToAcceptQueue(Socket socket) {
        queue.add(socket);
    }

    public void loop() {
        while (!Thread.interrupted()) {
            queue.remove();
        }
    }

}
