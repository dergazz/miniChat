package server.persistence;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class HeapRooms {

    private static List<Room> roomArrayList;

    public static void init() {
        roomArrayList = new CopyOnWriteArrayList<Room>();
        Room room = new Room("DEFAULTROOM");
//        room.setName();
        HeapRooms.addRoom(room);
        roomArrayList.add(room);
//        HeapSenders.addRoomToSender(room);

    }

    public static Room getDefaultRoom() {
        return roomArrayList.get(0);
    }

    public static void addRoom(Room room) {
        roomArrayList.add(room);
    }

    public static void removeRoom(Room room) {
        roomArrayList.remove(room);
    }

    public static int size() {
        return roomArrayList.size();
    }

    public static Room getRoomByNumber(int i) {
        return roomArrayList.get(i);
    }

    public static Room getRoomByName(String name) {
        for (Room room : roomArrayList) {
            if (room.getName().equals(name)) return room;
        }

        return null;
    }


}
