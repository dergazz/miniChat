package server.persistence;

import entity.Code;
import entity.ServerMessage;
import server.sceleton.ChiefManager;
import server.sceleton.ClientSocket;

public class InputHandler {

    public static void handle(ClientSocket clientSocket, String inputString) {

        if (inputString == null) {
            return;
        }
        String answer;

        if (inputString.equals("/exit")) {
            CommandExecutor.cmdExit(clientSocket);
            return;
        } else if (inputString.startsWith("/help")) {
            answer = CommandExecutor.cmdHelp();
            ServerMessage message = new ServerMessage(clientSocket, answer);
            ChiefManager.serverResendersManager.addMessage(message);
            return;
        }

        if (clientSocket.getClient() == null) {
            answer = Code.PLEASE_LOG_OR_REGISTER;
            if (inputString.startsWith("/reg")) {
                answer = CommandExecutor.cmdRegister(clientSocket, inputString);
            } else if (inputString.startsWith("/log")) {
                answer = CommandExecutor.cmdLogin(clientSocket, inputString);
            }
        } else {
            answer = Code.WRONG_COMMAND;
            if ((inputString.startsWith("/reg")) || (inputString.startsWith("/log"))) {
                answer = Code.YOU_ALREADY_ONLINE;
            } else if (inputString.startsWith("/setname")) {
                answer = CommandExecutor.cmdSetName(clientSocket, inputString);
            } else if (inputString.startsWith("/createroom")) {
                answer = CommandExecutor.cmdCreateRoom(clientSocket, inputString);
            } else if (inputString.startsWith("/addtoroom")) {
                answer = CommandExecutor.cmdJoinRoom(clientSocket, inputString);
            } else if (inputString.startsWith("/escroom")) {
                answer = CommandExecutor.cmdEscapeRoom(clientSocket, inputString);
            } else if (inputString.startsWith("/snd")) {
                answer = CommandExecutor.cmdSendMessageToDefaultRoom(clientSocket, inputString);
            } else if (inputString.startsWith("/str ")) {
                answer = CommandExecutor.cmdSendMessageToRoom(clientSocket, inputString);
            } else if (inputString.startsWith("/chatwith ")) {
                answer = CommandExecutor.cmdCreatePrivateRoom(clientSocket, inputString);
            } else if (inputString.startsWith("/myrooms")) {
                answer = CommandExecutor.cmdMyRooms(clientSocket);
            } else if (inputString.startsWith("/senders")) {
                answer = CommandExecutor.cmdSenders();
            } else if (inputString.startsWith("/allrooms")) {
                answer = CommandExecutor.cmdAllRooms();
            }
        }
            ServerMessage message = new ServerMessage(clientSocket, answer);
            ChiefManager.serverResendersManager.addMessage(message);
//        ServerSendersManager.addServerMessage(clientSocket, answer);
    }

}
