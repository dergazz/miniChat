package server.sceleton;

import entity.Message;
import entity.Recipient;
import entity.Resender;

import java.util.ArrayList;

public class ResendersManager {

    private int maxSize;
    private ArrayList<Resender> list = new ArrayList<Resender>();

    public ResendersManager(int resenderMaxRecipients) {
        maxSize = resenderMaxRecipients ;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    private Resender getResender() {
        for (Resender aResender : list) {
            if (aResender.getListSize() < maxSize) {
                return aResender;
            }
        }
        Resender resender = new Resender();
        list.add(resender);
        resender.start();
        return resender;
    }

    public boolean checkResender(Resender sender) {
        if (sender.listIsEmpty()) {
            list.remove(sender);
            return true;
        }
        return false;
    }

    public void addRecipientToResender(Recipient recipient) {
        Resender sender = getResender();
        sender.addRecipient(recipient);
        recipient.setResender(sender);
    }

    public void removeRecipientFromResender(Recipient recipient) {
        recipient.getResender().removeRecipient(recipient);
    }

    public void addMessage(Message message) {
        message.getRecipient().getResender().addMessage(message);
    }
}

