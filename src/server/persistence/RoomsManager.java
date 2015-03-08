package server.persistence;

import entity.InterfaceManager;
import entity.ServerMessage;
import server.sceleton.ChiefManager;

import java.util.ArrayList;
import java.util.List;

public class RoomsManager implements InterfaceManager<Room> {

    private List<Room> roomArrayList = new ArrayList<Room>();

    public RoomsManager() {
        Room room = new Room("DEFAULTROOM");
//        addRoom(room);
        addElement(room);
    }

    public String getRoomArrayList() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nAll rooms:")
                .append("\n");
        for (Room room : roomArrayList) {
            stringBuilder.append(room.getName())
                    .append(" (")
                    .append(room.getSize())
                    .append(")")
                    .append("\n");
        }
        stringBuilder.append("Total ")
                .append(roomArrayList.size());

        return stringBuilder.toString();
    }

    public Room getDefaultRoom() {
        return roomArrayList.get(0);
    }

    public Room createRoom(String roomName) {

        Room room = new Room(roomName);
//        addRoom(room);
        addElement(room);
        return room;
    }

//    public void addRoom(Room room) {
//        roomArrayList.add(room);
//    }

//    public void removeRoom(Room room) {
//        roomArrayList.remove(room);
//    }

    public Room getRoomByName(String name) {
        List<Room> temp = new ArrayList<Room>(roomArrayList);
        for (Room room : temp) {
            if (room.getName().equalsIgnoreCase(name)) return room;
        }
        return null;
    }

    public Room getRoomByName(Client client, String name) {
        for (Room room : client.getRoomList()) {
            if (room.getName().equalsIgnoreCase(name)) return room;
        }
        return null;
    }

    public void removeClientFromRoom(Client client, Room room) {
        room.removeElement(client);

        if (room.getName().startsWith("PrivateRoom")) {
            for (Client aClient : room.getList()) {
                if (!aClient.equals(client)) {
                    String answer = client.getName() + " escapes the room " + room.getName() + ".";
                    ServerMessage message = new ServerMessage(aClient.getClientSocket(), answer);
                    ChiefManager.serverResendersManager.addMessage(message);
                    aClient.removeRoom(room);
//                    aClient.getRoomList().remove(room);
//                    room.removeElement(aClient);
//                    room.removeElement(aClient);
                }
            }
//            room.clearList();
            ChiefManager.roomResendersManager.removeRecipientFromResender(room);
//            removeRoom(room);
            removeElement(room);
        }

    }


    @Override
    public synchronized void addElement(Room element) {
        roomArrayList.add(element);
    }

    @Override
    public synchronized void removeElement(Room element) {
        roomArrayList.remove(element);
    }

    @Override
    public synchronized int getSize() {
        return roomArrayList.size();
    }

    @Override
    public synchronized boolean isEmpty() {
        return roomArrayList.isEmpty();
    }
}
