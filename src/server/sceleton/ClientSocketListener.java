package server.sceleton;

import server.persistence.InputHandler;

import java.io.IOException;

public class ClientSocketListener extends Thread {

    ClientSocket clientSocket;

    public ClientSocketListener(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {

            try {
                InputHandler.handle(clientSocket, clientSocket.readIn());
            } catch (IOException e) {

                ClientSocketManager.onConnectionBreak(clientSocket);
                break;
            }
        }

    }

}
