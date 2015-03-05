package entity;

public class RoomMessage extends Message {

    public RoomMessage(Addresser addresser, Recipient recipient, String text) {
        super(addresser, recipient, text);
    }

    @Override
    public String toString() {
        return "[" + getRecipient().getName() + "] " + "[" + getStringDate() + "] " + getAddresser().getName() + ": " + getText();
    }

}