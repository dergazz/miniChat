package server.persistence;

import server.sceleton.ClientSocket;
import server.sceleton.database.DBManager;

import java.io.IOException;
import java.util.List;

public class ClientManager {

    public static boolean createClient(ClientSocket clientSocket, int id) {
        try {
            Client client = new Client(clientSocket, id);
            clientSocket.setClient(client);
            HeapClients.addClient(client);

            client.addRoom(HeapRooms.getDefaultRoom());
            HeapRooms.getDefaultRoom().addClient(client);

            client.setOnline();

            System.out.println(client.getId() + " in. Total " + HeapClients.getNumber() + ".");
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean fillClientInfo(int id) {
        DBManager.fillClient(HeapClients.getClientByID(id), id);
        return true;
    }

    public static boolean removeClient(Client client) {
        List<Room> roomList = client.getRoomList();
        for (Room room :roomList) {
            room.removeClient(client);
            client.removeRoom(room);
        }
        client.getClientSocket().getServerSender().removeClientSocketFromList(client.getClientSocket());


        HeapClients.removeClient(client);
        System.out.println(client.getId() + " is out. Total " + HeapClients.getNumber() + ".");
        return true;
    }

    public static boolean addClientToRoom(Client client, Room room){
        room.addClient(client);
        client.addRoom(room);
        return true;
    }
    public static void escapeRoom(Client client, Room room) {
        client.removeRoom(room);
        room.removeClient(client);
    }

}
