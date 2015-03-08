package server.sceleton;

import server.persistence.Client;

import java.io.IOException;
import java.net.Socket;

public class ClientSocketsManager {

//    public ClientSocketsRemover clientSocketsRemover = new ClientSocketsRemover();
    ClientSocketsCreator clientSocketsCreator = new ClientSocketsCreator();


    public ClientSocketsManager() {
//        clientSocketsRemover.start();
        clientSocketsCreator.start();
    }

    public void addToCreatorQueue(Socket socket) {
        clientSocketsCreator.addToQueue(socket);
    }

    public boolean onConnection(Socket socket) {
        try {
            ClientSocket clientSocket = new ClientSocket(socket);
//            ServerSendersManager.addClientSocketToResender(clientSocket);
            ChiefManager.serverResendersManager.addRecipientToResender(clientSocket);


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
            ChiefManager.remover.addToQueue(client);
//            ChiefManager.clientsManager.removeElement(client);
//            System.out.println("ChiefManager.clientsManager.onConnectionBreak(client)");

//            ChiefManager.clientsManager.onConnectionBreak(client);
        }
//        ClientSocketsRemover.addClientSocketToRemove(clientSocket);
//        clientsRemover.addToQueue(client);
        ChiefManager.remover.addToQueue(clientSocket);

//        clientSocketsRemover.addToQueue(clientSocket);
//        ClientSocketsRemover.addToQueue(clientSocket);
    }

    public boolean close(ClientSocket clientSocket) {

        ChiefManager.serverResendersManager.removeRecipientFromResender(clientSocket);
//        ServerSendersManager.removeClientSocketFromResender(clientSocket);


        clientSocket.closeSocket();
        System.out.println("socket closed");
        return true;
    }

}
