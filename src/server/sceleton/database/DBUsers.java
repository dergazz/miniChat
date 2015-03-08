package server.sceleton.database;

import server.persistence.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DBUsers {

//    private ArrayList<DBUserEntry> entries = new ArrayList<DBUserEntry>();
    private List<DBUserEntry> entries = new ArrayList<DBUserEntry>();


    public synchronized boolean addEntry(String id, String name, String login, String password) {
        DBUserEntry dbUserEntry = new DBUserEntry(id, name, login, password);
        entries.add(dbUserEntry);

     return true;
    }

    public synchronized boolean findID(String id) {
        for (DBUserEntry dbUserEntry :entries) {
            if (id.equals(dbUserEntry.id)) {
                return true;
            }
        }
        return false;
    }

    public String getPassByID(String id) {
        for (DBUserEntry dbUserEntry :entries) {
            if (id.equals(dbUserEntry.id)) {
                return dbUserEntry.password;
            }
        }
        return null;
    }
    public DBUserEntry getEntryByID(String id) {
        for (DBUserEntry dbUserEntry :entries) {
            if (id.equals(dbUserEntry.id)) {
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
        String id;
        String name;
        ArrayList<Client> friends = new ArrayList<Client>();
        String login;
        String password;

        public DBUserEntry(String id, String name, String login, String password) {
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
