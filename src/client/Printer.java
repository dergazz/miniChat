package client;

import entity.Queue;

public class Printer extends Thread {

    private Queue<String> queueMessages;
//    private ClientHandler clientHandler;


    public Printer() {
        super("Printer");
        queueMessages = new Queue<String>();
    }

    public void addMessage(String string) {
            queueMessages.add(string);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            String string = queueMessages.remove();
            System.out.println(string + " IN QUEUE: " + queueMessages.getSize());
//            try {
//                sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

        }
    }
}
