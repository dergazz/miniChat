package server.persistence;

import entity.ServerMessage;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RoomsManager {

    private List<Room> roomArrayList = new CopyOnWriteArrayList<Room>();

    public RoomsManager() {
        Room room = new Room("DEFAULTROOM");
        addRoom(room);
    }

    public List<Room> getRoomArrayList() {
        return roomArrayList;
    }

    public Room getDefaultRoom() {
        return roomArrayList.get(0);
    }

    public Room createRoom(String roomName) {
        Room room = new Room(roomName);
        addRoom(room);
        return room;
    }

    public void addRoom(Room room) {
        roomArrayList.add(room);
    }

    public void removeRoom(Room room) {
        roomArrayList.remove(room);
    }

    public Room getRoomByName(String name) {
        for (Room room : roomArrayList) {
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
        room.removeClient(client);

        if (room.getName().startsWith("PrivateRoom")) {
            for (Client aClient : room.getList()) {
                if (!aClient.equals(client)) {
                    String answer = client.getName() + " escapes the room " + room.getName() + ".";
                    ServerMessage message = new ServerMessage(aClient.getClientSocket(), answer);
                    Manager.serverResendersManager.addMessage(message);
                    aClient.getRoomList().remove(room);
                    room.removeClient(aClient);
                }
            }
            Manager.roomResendersManager.removeRecipientFromResender(room);
            removeRoom(room);
        }

    }


}
