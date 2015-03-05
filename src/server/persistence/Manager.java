package server.persistence;

import server.sceleton.ResendersManager;
import server.sceleton.ClientSocketsManager;
import server.sceleton.database.DBManager;

public class Manager {


    public static ResendersManager roomResendersManager = new ResendersManager(100);
    public static ResendersManager serverResendersManager = new ResendersManager(500);
    public static RoomsManager roomsManager = new RoomsManager();
    public static ClientsManager clientsManager = new ClientsManager();
    public static ClientSocketsManager clientSocketsManager = new ClientSocketsManager();
    public static DBManager dbManager = new DBManager();

    public static void init() {

    }

}
