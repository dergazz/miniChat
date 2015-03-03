package server.persistence;

import entity.ServerMessage;
import server.sceleton.ClientSocket;

public class InputHandler {

    public static void handle(ClientSocket clientSocket, String inputString) {

        String answer;
        if (inputString.startsWith("/reg")) {
            answer = CommandExecutor.cmdRegister(clientSocket, inputString);
            clientSocket.sendServerMessage(new ServerMessage(clientSocket, answer));
        }
        if (inputString.startsWith("/log")) {
            answer = CommandExecutor.cmdLogin(clientSocket, inputString);
            clientSocket.sendServerMessage(new ServerMessage(clientSocket, answer));
        }
        if (inputString.startsWith("/info")) {
            answer = CommandExecutor.cmdInfo(clientSocket);
            clientSocket.sendServerMessage(new ServerMessage(clientSocket, answer));
        }
        if (inputString.startsWith("/online")) {
            answer = CommandExecutor.cmdOnline();
            clientSocket.sendServerMessage(new ServerMessage(clientSocket, answer));
        }
        if (inputString.startsWith("/senders")) {
            answer = CommandExecutor.cmdSenders();
            clientSocket.sendServerMessage(new ServerMessage(clientSocket, answer));
        }
        if (inputString.startsWith("/setname")) {
            answer = CommandExecutor.cmdSetName(clientSocket, inputString);
            clientSocket.sendServerMessage(new ServerMessage(clientSocket, answer));
        }
        clientSocket.sendServerMessage(new ServerMessage(clientSocket, "echo: " + inputString));
        if (inputString.startsWith("/echo")) {
        }

    }

}
