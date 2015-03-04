package server.persistence;

import server.sceleton.ClientSocket;
import server.sceleton.ServerSender;

import java.util.ArrayList;

public class HeapSenders {

    private static ArrayList<Sender> list;

    public static void init() {
        list = new ArrayList<Sender>();
    }

    private static Sender getSender() {
        for (Sender aSender :list) {
            if (aSender.getListSize() < 100) {
                return aSender;
            }
        }
        Sender sender = new Sender();
        list.add(sender);
        sender.start();
        return sender;
    }

    public static boolean addRoomToSender(Room room) {
        Sender sender = getSender();
        sender.addRoom(room);
        room.setSender(sender);
        return true;
    }

    public static boolean checkSender(Sender sender) {
        if (sender.listIsEmpty()) {
            removeSender(sender);
            return true;
        }
        return false;
    }

    public static void removeSender(Sender sender) {
        list.remove(sender);
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

}
