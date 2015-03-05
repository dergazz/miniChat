package entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Resender extends Thread {

    private Queue<Message> queue = new Queue<Message>();
    private List<Recipient> listRecipients = new ArrayList<Recipient>();

    public Resender() {
        super("Sender");
    }

    public void addMessage(Message message) {
        queue.add(message);
    }

    public void addRecipient(Recipient recipient) {
        listRecipients.add(recipient);
    }
    public void removeRecipient(Recipient recipient) {
        listRecipients.remove(recipient);
    }

    public int getListSize() {
        return listRecipients.size();
    }

    public boolean listIsEmpty() {
        return listRecipients.isEmpty();
    }


    @Override
    public void run() {
        while (!Thread.interrupted()) {
            Message message = queue.remove();
            message.getRecipient().send(message);
        }
    }



}


