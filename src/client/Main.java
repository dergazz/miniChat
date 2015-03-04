package client;


import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

//        String ip = args[0];
//        int port = Integer.parseInt(args[1]);
        //        socket = new Socket("localhost", 7777);

//        String ip = "188.120.227.114";
        String ip = "localhost";
        int port = 1111;
        System.out.println("Try to connect...");
        Client client = new Client(ip, port);
        client.start();
    }
}
