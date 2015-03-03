package server.persistence;


import server.sceleton.ClientSocket;
import server.sceleton.ClientSocketRemover;
import server.sceleton.HeapServerSenders;

public class CommandExecutor {

    public static String cmdInfo(ClientSocket clientSocket){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SRV[ans/info]")
                .append("<")
                .append(clientSocket.getServerSender())
                .append(" ")
                .append(clientSocket.getServerSender().isAlive())
                .append(">");

        return stringBuilder.toString();
    }

    public static String cmdSenders(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HeapServerSenders.getList());
        return stringBuilder.toString();
    }

}
