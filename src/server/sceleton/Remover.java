package server.sceleton;

import entity.QueueManager;
import entity.Removable;

public class Remover extends QueueManager<Removable> {

    @Override
    public void actionOnElement(Removable removable) {
        removable.removeIt();
//        ChiefManager.clientsManager.removeElement(client);
//        ChiefManager.clientsManager.removeElement(client);
    }

}
