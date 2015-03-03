package server.sceleton.database;


import server.persistence.Client;

public class DBHandler {

    static DBUsers dbUsers = new DBUsers();

//    public static boolean addClient(int id, String password) {
    public static boolean addClient(Client client, String password) {
        String name = client.getName();
        int id = client.getId();
        String login = String.valueOf(id);
        dbUsers.addEntry(id, name, login, password);
        return true;
    }

    public static boolean refresh(Client client) {
        dbUsers.getEntryByID(client.getId()).name = client.getName();
        return true;
    }


    public static boolean clientIDExist(int id) {
        return dbUsers.findID(id);
    }

    public static boolean checkPassword(int id, String password) {
        String pass = dbUsers.getPassByID(id);
        if (password.equals(pass)) {
            return true;
        }
        return false;
    }

    public static String getAllEntries() {
        return dbUsers.getAllEntrys();
    }

    public static boolean fillClient(Client client, int id) {
        client.setName(dbUsers.getEntryByID(id).getName());
        return false;
    }
}
