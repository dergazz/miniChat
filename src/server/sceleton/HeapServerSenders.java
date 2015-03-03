package server.sceleton;

import java.util.ArrayList;

public class HeapServerSenders {

    private static ArrayList<ServerSender> list = new ArrayList<ServerSender>();

    private static ServerSender getSender() {
        for (ServerSender aServerSender :list) {
            if (aServerSender.getListSize() < 2) {
                return aServerSender;
            }
        }
        ServerSender serverSender = new ServerSender();
        System.out.println("new serverSender created");
        list.add(serverSender);
        serverSender.start();
        return serverSender;
    }

    public static void addClientSocketToServerSender(ClientSocket clientSocket) {
        ServerSender serverSender = getSender();
        serverSender.addClientSocketToList(clientSocket);
        clientSocket.setServerSender(serverSender);

    }

    public static void removeSender(ServerSender serverSender) {
        if (list.size() > 1) {
            list.remove(serverSender);
        }
    }

    private static void print() {
        System.out.println("print all senders");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        System.out.println("end. Totally " + list.size());
    }

}
