package client;

import java.io.DataInputStream;
import java.io.IOException;

public class MessagesAcceptor extends Thread {

    DataInputStream in;
    Printer printer;

    public MessagesAcceptor(DataInputStream in) {
        this.in = in;
        printer = new Printer();
        printer.start();
    }

    @Override
    public void run() {
        while (!Thread.interrupted())
            try {
                String ss = in.readUTF();
                printer.addToQueue(ss);
            } catch (IOException e) {
                System.out.println("exit");
                System.exit(0);
            }
    }

}
