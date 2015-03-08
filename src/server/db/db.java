package server.db;

import java.util.ArrayList;
import java.util.List;

public class db {
    private static List<dbEntry> list = new ArrayList<dbEntry>();

    public static List<dbEntry> getList() {
        return list;
    }

    public static void addEntry(dbEntry entry) {
        list.add(entry);
    }

}
