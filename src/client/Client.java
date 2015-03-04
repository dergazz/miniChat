package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    public Client(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        System.out.println("Ok. Socket is created!");
    }

    public void start() throws IOException {
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);
        MessagesAcceptor printer = new MessagesAcceptor(in);

        printer.start();
        String string;
        while (true) {
            string = scanner.nextLine();
            if (!string.equals("")) {
                out.writeUTF(string);
                out.flush();
            }
        }
    }
}
