package server.db;

import entity.Pair;
import entity.Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileLoaderThread {

    db base = new db();

    public static void main(String[] args) throws IOException {
        new FileLoaderThread().doIt();
        for (dbEntry entry :db.getList()) {
            System.out.println(entry.getId() + " " + entry.getPassword());
        }

    }



    public static void doIt() throws IOException {

        BufferedReader in = new BufferedReader(new FileReader("D:/text.txt"));
        StringBuilder stringBuilder = new StringBuilder();
        String fin;
        while ((fin = in.readLine()) != null) {
            stringBuilder.append(fin).append("\n");
            Pair<String, String> temp = Parser.parsePair(fin, 0);
            dbEntry entry = new dbEntry(temp.getKey(), temp.getValue());
            db.addEntry(entry);
//            System.out.printf("name %s pass %s \n", temp.getKey(), temp.getValue());
        }
        in.close();


    }


}
