package server.db;

public class dbEntry {
    private String id;
    private String password;

    public dbEntry(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return "id" + id;
    }

    public String getPassword() {
        return password;
    }
}

