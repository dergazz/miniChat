package server.persistence;

import entity.Pair;
import entity.QueueManager;
import server.sceleton.ClientSocket;

public class ClientsCreator extends QueueManager<Pair<ClientSocket, Integer>> {

    @Override
    public void actionOnElement(Pair<ClientSocket, Integer> pair) {
        Manager.clientsManager.createClient(pair.getKey(), pair.getValue());
    }
}
