package server.sceleton;

import entity.Message;
import entity.QueueManager;
import entity.Recipient;
import java.util.ArrayList;
import java.util.List;

public class Resender extends QueueManager<Message> implements Runnable {


    private List<Recipient> listRecipients = new ArrayList<Recipient>();

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
    public void actionOnElement(Message element) {
        element.getRecipient().getMessage(element);

    }

}
