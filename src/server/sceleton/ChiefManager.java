package server.sceleton;

import server.persistence.ClientsManager;
import server.persistence.RoomsManager;
import server.sceleton.Remover;
import server.sceleton.ResendersManager;
import server.sceleton.ClientSocketsManager;
import server.sceleton.database.DBManager;

public class ChiefManager {


    public static ResendersManager roomResendersManager = new ResendersManager(100, "RoomResender");
    public static ResendersManager serverResendersManager = new ResendersManager(500, "ServerResender");
    public static RoomsManager roomsManager = new RoomsManager();
    public static ClientsManager clientsManager = new ClientsManager();
    public static ClientSocketsManager clientSocketsManager = new ClientSocketsManager();
    public static DBManager dbManager = new DBManager();
    public static Remover remover = new Remover();

    public static void init() {
        remover.start();
    }

}
