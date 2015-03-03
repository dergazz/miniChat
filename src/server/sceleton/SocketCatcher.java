package server.sceleton;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketCatcher {

    ServerSocket serverSocket;
    SocketAcceptor socketAcceptor = new SocketAcceptor();


    public SocketCatcher(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void loop() throws IOException {
        socketAcceptor.loop();
        while (!Thread.interrupted()) {
            Socket clientSocket = serverSocket.accept();
            socketAcceptor.addToAcceptQueue(clientSocket);
        }
    }

}
