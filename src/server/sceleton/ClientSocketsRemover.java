package server.sceleton;

import entity.QueueManager;
import server.persistence.Manager;

public class ClientSocketsRemover extends QueueManager<ClientSocket> {
    @Override
    public void actionOnElement(ClientSocket clientSocket) {
        Manager.clientSocketsManager.close(clientSocket);
    }
}