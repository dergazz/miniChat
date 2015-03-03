package server.sceleton.database;


public class DBHandler {

    static DBUsers dbUsers = new DBUsers();

    public static boolean addClient(int id, String password) {
        String name = "defaultname";
        String login = String.valueOf(id);
        dbUsers.addEntry(id, name, login, password);
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

}
