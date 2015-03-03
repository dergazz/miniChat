package server.sceleton;

import entity.Queue;

public class ClientSocketRemover extends Thread{

    private static Queue<ClientSocket> queue = new Queue<ClientSocket>();

    public ClientSocketRemover() {
        super("ClientSocketRemover");
    }

    public String getThread() {
        return super.getName() + isAlive();
    }

    public static void addClientSocketToRemove(ClientSocket clientSocket) {
        queue.add(clientSocket);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            ClientSocket clientSocket = queue.remove();

            ClientSocketHandler.close(clientSocket);

        }
    }

}
