package server;

import server.sceleton.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        int port = 1111;

        try {
            Server server = new Server(port);
            server.loop();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
