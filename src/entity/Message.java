package entity;

import server.persistence.Client;
import server.persistence.Room;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

    private Client client;
    private String string;
    private Room room;
    private String dateAndTime;

    public Message(Client client, String string, Room room) {
        this.client = client;
        this.string = string;
        this.room = room;
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        dateAndTime = format.format(date);
    }

    public Client getClient() {
        return client;
    }

    public String getString() {
        return string;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return "[" + dateAndTime + "] " + client.getName() + ": " + string;
    }
}
