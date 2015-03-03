package test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientCreator {



    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 1111;
        Socket socket = new Socket("localhost", 1111);
    }

}
