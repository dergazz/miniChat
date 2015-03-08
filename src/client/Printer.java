package client;

import entity.Queue;
import entity.QueueManager;

public class Printer extends QueueManager<String> {


    @Override
    public void actionOnElement(String string) {
        System.out.println(string);
//        if (getQueueSize() > 10) System.out.println("IN QUEUE: " + getQueueSize());
    }
}
//package client;
//
//import entity.Queue;
//
//public class Printer extends Thread {
//
//    private Queue<String> queueMessages;
//
//    public Printer() {
//        super("Printer");
//        queueMessages = new Queue<String>();
//    }
//
//    public void addMessage(String string) {
//            queueMessages.add(string);
//    }
//
//    @Override
//    public void run() {
//        while (!Thread.interrupted()) {
//            String string = queueMessages.remove();
//            System.out.println(string + " IN QUEUE: " + queueMessages.getSize());
//            if ( queueMessages.getSize() > 10) {
//                System.out.println("IN QUEUE: " + queueMessages.getSize());
//            }
//        }
//    }
//}
