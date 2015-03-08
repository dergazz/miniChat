package server.sceleton.database;


import server.persistence.Client;

import java.io.*;

public class DBManager {

    DBUsers dbUsers = new DBUsers();
//    DataOutputStream outputStream ;
//
//    public DBManager() {
//        try {
//            outputStream =  new DataOutputStream(new FileOutputStream("D:/text2.txt"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    //    public boolean addClient(int id, String password) {
    public boolean addClient(Client client, String password) {
        String name = client.getName();
        String id = client.getId();
        String login = String.valueOf(id);
        dbUsers.addEntry(id, name, login, password);



        return true;
    }

//    public boolean
    public boolean addIDAndPass(String id, String password) {
        String login = String.valueOf(id);
        dbUsers.addEntry(id, "defaultname", login, password);

//        try {
////            FileWriter fileWriter = new FileWriter("D:/text2.txt");
//            outputStream.writeUTF(id + " " + password + "\n");
//            outputStream.flush();
////            outputStream.close();
////
////            fileWriter.write(id + " " + password + "\n");
////            fileWriter.flush();
////            fileWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return true;
    }

    public boolean refresh(Client client) {
        dbUsers.getEntryByID(client.getId()).name = client.getName();
        return true;
    }


    public boolean clientIDExist(String id) {
        return dbUsers.findID(id);
    }

    public boolean checkPassword(String id, String password) {
        String pass = dbUsers.getPassByID(id);
        if (password.equals(pass)) {
            return true;
        }
        return false;
    }

    public String getAllEntries() {
        return dbUsers.getAllEntrys();
    }

    public boolean fillClient(Client client, String id) {
        DBUsers.DBUserEntry entry = dbUsers.getEntryByID(id);
        String name = entry.getName();
        client.setName(name);
        return false;
    }
}
