package server.sceleton;

import java.io.IOException;
import java.net.Socket;

public class ClientSocketHandler {

    public static boolean onConnection(Socket socket) {
        try {
            ClientSocket clientSocket = new ClientSocket(socket);
            HeapServerSenders.addClientSocketToServerSender(clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        System.out.println("socket connected");
        return true;
    }

    public static boolean onConnectionBreak(ClientSocket clientSocket) {
        ClientSocketRemover.addClientSocketToRemove(clientSocket);
        return true;
    }

    public static boolean close(ClientSocket clientSocket) {
        clientSocket.getServerSender().removeClientSocketFromList(clientSocket);
        clientSocket.closeSocket();
        System.out.println("socket closed");
        return true;
    }

}
