package server.sceleton.database;

import server.persistence.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DBUsers {

//    private ArrayList<DBUserEntry> entries = new ArrayList<DBUserEntry>();
    private List<DBUserEntry> entries = new CopyOnWriteArrayList<DBUserEntry>();


    public boolean addEntry(int id, String name, String login, String password) {
        DBUserEntry dbUserEntry = new DBUserEntry(id, name, login, password);
        entries.add(dbUserEntry);
     return true;
    }

    public boolean findID(int id) {
        for (DBUserEntry dbUserEntry :entries) {
            if (id == dbUserEntry.id) {
                return true;
            }
        }
        return false;
    }

    public String getPassByID(int id) {
        for (DBUserEntry dbUserEntry :entries) {
            if (id == dbUserEntry.id) {
                return dbUserEntry.password;
            }
        }
        return null;
    }
    public DBUserEntry getEntryByID(int id) {
        for (DBUserEntry dbUserEntry :entries) {
            if (id == dbUserEntry.id) {
                return dbUserEntry;
            }
        }
        return null;
    }

    public String getAllEntrys() {
        StringBuilder stringBuilder = new StringBuilder();
        for (DBUserEntry entry : entries) {
            stringBuilder.append(entry.id)
                    .append(" ")
                    .append(entry.name)
                    .append(" ")
                    .append(entry.password)
                    .append("\n");
        }
        return stringBuilder.toString();
    }

    public class DBUserEntry {
        int id;
        String name;
        ArrayList<Client> friends = new ArrayList<Client>();
        String login;
        String password;

        public DBUserEntry(int id, String name, String login, String password) {
            this.id = id;
            this.name = name;
            this.login = login;
            this.password = password;
        }

        public String getName() {
            return name;
        }
    }

}
