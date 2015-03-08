package server.sceleton;

import entity.*;

import java.util.ArrayList;

public class ResendersManager {

    private int maxSize;
    private String nameSender;
    //    private ArrayList<Resender> list = new ArrayList<Resender>();
    private ArrayList<Pair<Thread, Resender>> list = new ArrayList<Pair<Thread, Resender>>();

    public ResendersManager(int resenderMaxRecipients, String nameSender) {
        maxSize = resenderMaxRecipients ;
        this.nameSender = nameSender;
    }

    private Pair<Thread, Resender> getResender() {
        for (Pair<Thread, Resender> pair : list) {
            if (pair.getValue().getListSize() < maxSize) {
                return pair;
            }
        }
        Resender resender = new Resender();
        Thread thread = new Thread(resender);
        Pair newPair = new Pair<Thread, Resender>(thread, resender);
        list.add(newPair);
        thread.start();
        return newPair;
    }

//    private Resender getResender() {
//        for (Resender aResender : list) {
//            if (aResender.getListSize() < maxSize) {
//                return aResender;
//            }
//        }
//        Resender resender = new Resender(nameSender);
//        list.add(resender);
//        resender.start();
//        return resender;
//    }

    public synchronized boolean checkResender(Resender sender) {
        Pair<Thread, Resender> newPair = null;

        if (sender.listIsEmpty()) {

            for (Pair<Thread, Resender> pair :list) {
                if (sender == pair.getValue()) {
                    newPair = pair;
                    pair.getKey().interrupt();
                    break;
                }
            }
//            newPair.getKey().interrupt();

            list.remove(newPair);
            return true;
        }
        return false;
    }

    public void addRecipientToResender(Recipient recipient) {
        Pair<Thread, Resender> pair = getResender();
//        Resender sender = getResender();
        pair.getValue().addRecipient(recipient);
        recipient.setResender(pair.getValue());
    }

    public void removeRecipientFromResender(Recipient recipient) {
        Resender resender = recipient.getResender();
        resender.removeRecipient(recipient);
        checkResender(resender);
    }

    public void addMessage(Message message) {
        Recipient recipient = message.getRecipient();
        Resender resender = recipient.getResender();
        try {
            resender.addToQueue(message);

        } catch (NullPointerException e) {
            e.printStackTrace();
            System.err.println("ERROR " + message.getRecipient().getName() + " " + message.getText());
        }
//        message.getRecipient().getResender().addToQueue(message);
    }

    public String getListSenders() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Senders" + nameSender)
                .append("\n");
        for (Pair<Thread, Resender> pair :list) {
            stringBuilder.append(pair.getKey())
                    .append(" - ")
                    .append(pair.getValue())
                    .append("{")
                    .append(pair.getValue().getListSize())
                    .append(").")
                    .append("\n");
        }
        stringBuilder.append("Total ")
                .append(list.size());

        return stringBuilder.toString();
    }
}

