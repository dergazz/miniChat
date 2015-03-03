package entity;

import server.sceleton.ClientSocket;

public class ServerMessage {

    ClientSocket clientSocket;
    String messageString;

    public ServerMessage(ClientSocket clientSocket, String messageString) {
        this.clientSocket = clientSocket;
        this.messageString = messageString;
    }

    public ClientSocket getClientSocket() {
        return clientSocket;
    }

    public String getMessageString() {
        return messageString;
    }

}
