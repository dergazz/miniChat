package server.sceleton;

import java.util.ArrayList;

public class HeapServerSenders {

    private static ArrayList<ServerSender> list = new ArrayList<ServerSender>();

    private static ServerSender getSender() {
        for (ServerSender aServerSender :list) {
            if (aServerSender.getListSize() < 100) {
                return aServerSender;
            }
        }
        ServerSender serverSender = new ServerSender();
        list.add(serverSender);
        serverSender.start();
        return serverSender;
    }

    public static void addClientSocketToServerSender(ClientSocket clientSocket) {
        ServerSender serverSender = getSender();
        serverSender.addClientSocketToList(clientSocket);
        clientSocket.setServerSender(serverSender);

    }

    private static void removeSender(ServerSender serverSender) {
        list.remove(serverSender);
    }

    private static void print() {
        System.out.println("print all senders");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        System.out.println("end. Totally " + list.size());
    }

    public static String getList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i))
                    .append(" ")
                    .append(list.get(i).getListSize())
                    .append("\n");
        }
        stringBuilder.append("Total ")
                .append(list.size());
        return stringBuilder.toString();
    }

    public static boolean checkSender(ServerSender serverSender) {
        if (serverSender.listIsEmpty()) {
            removeSender(serverSender);
        }
        return false;
    }
}
