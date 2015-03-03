package server.sceleton;

import java.io.IOException;
import java.net.Socket;

public class ClientSocketHandler {

//    static private ServerSender serverSender = new ServerSender();
//
//    public static void init() {
//        serverSender.start();
//    }

    public static boolean onConnection(Socket socket) {
        try {
            ClientSocket clientSocket = new ClientSocket(socket);
            HeapServerSenders.addClientSocketToServerSender(clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean onConnectionBreak(ClientSocket clientSocket) {

        clientSocket.getServerSender().removeClientSocketFromList(clientSocket);
        return true;
    }

}
