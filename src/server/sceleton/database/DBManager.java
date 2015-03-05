package server.sceleton.database;


import server.persistence.Client;

public class DBManager {

    DBUsers dbUsers = new DBUsers();

//    public boolean addClient(int id, String password) {
    public boolean addClient(Client client, String password) {
        String name = client.getName();
        int id = client.getId();
        String login = String.valueOf(id);
        dbUsers.addEntry(id, name, login, password);
        return true;
    }

//    public boolean
    public boolean addIDAndPass(int id, String password) {
        String login = String.valueOf(id);
        dbUsers.addEntry(id, "defaultname", login, password);
        return true;
    }

    public boolean refresh(Client client) {
        dbUsers.getEntryByID(client.getId()).name = client.getName();
        return true;
    }


    public boolean clientIDExist(int id) {
        return dbUsers.findID(id);
    }

    public boolean checkPassword(int id, String password) {
        String pass = dbUsers.getPassByID(id);
        if (password.equals(pass)) {
            return true;
        }
        return false;
    }

    public String getAllEntries() {
        return dbUsers.getAllEntrys();
    }

    public boolean fillClient(Client client, int id) {
        client.setName(dbUsers.getEntryByID(id).getName());
        return false;
    }
}
