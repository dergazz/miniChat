package server.persistence;

import entity.ServerMessage;
import server.sceleton.ClientSocket;

public class InputHandler {

    public static void handle(ClientSocket clientSocket, String inputString) {

        clientSocket.sendServerMessage(new ServerMessage(clientSocket, "echo: " + inputString));

    }

}
