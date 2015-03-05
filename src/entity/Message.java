package entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Message {

    private Addresser addresser;
    private Recipient recipient;
    private String text;
    private Date date;


    public Message(Addresser addresser, Recipient recipient, String text) {
        this.addresser = addresser;
        this.recipient = recipient;
        this.text = text;
        date = new Date();
    }

    public Message(Recipient recipient, String text) {
        this.addresser = null;
        this.recipient = recipient;
        this.text = text;
        date = new Date();
    }

    public Addresser getAddresser() {
        return addresser;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public String getText() {
        return text;
    }

    public String getStringDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        return format.format(date);
    }

    @Override
    public abstract String toString();

}
