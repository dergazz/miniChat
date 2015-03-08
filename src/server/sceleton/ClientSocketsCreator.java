package server.sceleton;

import entity.QueueManager;

import java.net.Socket;

public class ClientSocketsCreator extends QueueManager<Socket> {

    @Override
    public void actionOnElement(Socket socket) {
        ChiefManager.clientSocketsManager.onConnection(socket);
    }

}

