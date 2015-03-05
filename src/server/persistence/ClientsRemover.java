package server.persistence;

import entity.QueueManager;

public class ClientsRemover extends QueueManager<Client> {

    @Override
    public void actionOnElement(Client client) {
        Manager.clientsManager.removeClient(client);
    }

}
