package server.persistence;

import entity.ServerMessage;
import server.sceleton.ClientSocket;

public class InputHandler {

    public static void handle(ClientSocket clientSocket, String inputString) {

        if (inputString == null) {
            return;
        }
        String answer = "";

        if (inputString.equals("/exit")) {
            CommandExecutor.cmdExit(clientSocket);
            return;
        }

        if (inputString.startsWith("/help")) {
            answer = CommandExecutor.cmdHelp();
            clientSocket.sendServerMessage(new ServerMessage(clientSocket, answer));
            return;
        }


        if (clientSocket.getClient() == null) {

            answer = "SRV Please login or register.";
            if (inputString.startsWith("/reg")) {
                answer = CommandExecutor.cmdRegister(clientSocket, inputString);
            }

            if (inputString.startsWith("/log")) {
                answer = CommandExecutor.cmdLogin(clientSocket, inputString);
            }
        } else {
            answer = "SRV Wrong command.";
            if (inputString.startsWith("/online")) {
                answer = CommandExecutor.cmdOnline();
            }
            if (inputString.startsWith("/senders")) {
                answer = CommandExecutor.cmdSenders();
            }
            if (inputString.startsWith("/setname")) {
                answer = CommandExecutor.cmdSetName(clientSocket, inputString);
            }
            if (inputString.startsWith("/createroom")) {
                answer = CommandExecutor.cmdCreateRoom(clientSocket, inputString);
            }
            if (inputString.startsWith("/addtoroom")) {
                answer = CommandExecutor.cmdAddClientToRoom(clientSocket, inputString);
            }
            if (inputString.startsWith("/escroom")) {
                answer = CommandExecutor.cmdEscapeRoom(clientSocket, inputString);
            }
            if (inputString.startsWith("/snd")) {
                answer = CommandExecutor.cmdSendMessageToDefaultRoom(clientSocket, inputString);
            }
            if (inputString.startsWith("/str ")) {
                answer = CommandExecutor.cmdSendMessageToRoom(clientSocket, inputString);
            }
            if (inputString.startsWith("/chatwith ")) {
                answer = CommandExecutor.cmdCreatePrivateRoom(clientSocket, inputString);
            }
        }

        clientSocket.sendServerMessage(new ServerMessage(clientSocket, answer));

//        clientSocket.sendServerMessage(new ServerMessage(clientSocket, "echo: " + inputString));
//        if (inputString.startsWith("/echo")) {
//        }

    }

}
