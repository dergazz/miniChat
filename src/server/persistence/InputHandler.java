package server.persistence;

import entity.ServerMessage;
import server.sceleton.ClientSocket;

public class InputHandler {

    public static void handle(ClientSocket clientSocket, String inputString) {

        String answer;
        if (inputString.startsWith("/reg")) {
            clientSocket.sendServerMessage(new ServerMessage(clientSocket, "you try to register"));
        }
        else if (inputString.startsWith("/info")) {
            answer = CommandExecutor.cmdInfo(clientSocket);
            clientSocket.sendServerMessage(new ServerMessage(clientSocket, answer));
        } else if (inputString.startsWith("/senders")) {
            answer = CommandExecutor.cmdSenders();
            clientSocket.sendServerMessage(new ServerMessage(clientSocket, answer));
        } else {

            clientSocket.sendServerMessage(new ServerMessage(clientSocket, "echo: " + inputString));
        }

    }

}
