package server.sceleton;

import entity.Queue;
import entity.ServerMessage;

import java.util.ArrayList;

public class ServerSender extends Thread{

    private Queue<ServerMessage> queue = new Queue<ServerMessage>();
    private ArrayList<ClientSocket> clientSocketArrayList = new ArrayList<ClientSocket>();

    public void addClientSocketToList(ClientSocket clientSocket) {
        clientSocketArrayList.add(clientSocket);
    }

    public void removeClientSocketFromList(ClientSocket clientSocket) {
        clientSocketArrayList.remove(clientSocket);

        if (clientSocketArrayList.isEmpty()) {
            HeapServerSenders.removeSender(this);
        }

    }

    public int getListSize() {
        return clientSocketArrayList.size();
    }

    public boolean listIsEmpty() {
        return clientSocketArrayList.isEmpty();
    }

    public void addServerMessage(ServerMessage serverMessage) {
        queue.add(serverMessage);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            ServerMessage serverMessage = queue.remove();
            ClientSocket toClientSocket = serverMessage.getClientSocket();
            String text = serverMessage.getMessageString();

            if (toClientSocket.writeUTF(text)) {
                //sended
            } else {
                //not sended
                ClientSocketHandler.onConnectionBreak(serverMessage.getClientSocket());
            }
        }

    }

}
