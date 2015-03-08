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
//                System.out.println("client listener");
                ChiefManager.clientSocketsManager.onConnectionBreak(clientSocket);
                break;
            }
        }

    }

}
