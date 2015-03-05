package server.sceleton;

import server.persistence.Client;
import server.persistence.ClientsManager;
import server.persistence.Manager;

import java.io.IOException;
import java.net.Socket;

public class ClientSocketsManager {

    public ClientSocketsRemover clientSocketsRemover = new ClientSocketsRemover();
    ClientSocketsCreator clientSocketsCreator = new ClientSocketsCreator();

    public ClientSocketsManager() {
        clientSocketsRemover.start();
        clientSocketsCreator.start();
    }

    public void addToCreatorQueue(Socket socket) {
        clientSocketsCreator.addToQueue(socket);
    }

    public boolean onConnection(Socket socket) {
        try {
            ClientSocket clientSocket = new ClientSocket(socket);
//            ServerSendersManager.addClientSocketToResender(clientSocket);
            Manager.serverResendersManager.addRecipientToResender(clientSocket);


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        System.out.println("socket connected");
        return true;
    }

    public void onConnectionBreak(ClientSocket clientSocket) {
        Client client = clientSocket.getClient();
        if (clientSocket.getClient() != (null)) {
            Manager.clientsManager.onConnectionBreak(client);
        }
//        ClientSocketsRemover.addClientSocketToRemove(clientSocket);
        clientSocketsRemover.addToQueue(clientSocket);
//        ClientSocketsRemover.addToQueue(clientSocket);
    }

    public boolean close(ClientSocket clientSocket) {

        Manager.serverResendersManager.removeRecipientFromResender(clientSocket);
//        ServerSendersManager.removeClientSocketFromResender(clientSocket);


        clientSocket.closeSocket();
        System.out.println("socket closed");
        return true;
    }

}
