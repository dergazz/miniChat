package server.persistence;

import entity.Pair;
import entity.QueueManager;
import server.sceleton.ChiefManager;
import server.sceleton.ClientSocket;

public class ClientsCreator extends QueueManager<Pair<ClientSocket, String>> {

    @Override
    public void actionOnElement(Pair<ClientSocket, String> pair) {
        ChiefManager.clientsManager.createClient(pair.getKey(), pair.getValue());
    }
}
