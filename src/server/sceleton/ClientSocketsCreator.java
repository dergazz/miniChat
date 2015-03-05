package server.sceleton;

import entity.QueueManager;
import server.persistence.Manager;

import java.net.Socket;

public class ClientSocketsCreator extends QueueManager<Socket> {

    @Override
    public void actionOnElement(Socket socket) {
        Manager.clientSocketsManager.onConnection(socket);
    }

}

