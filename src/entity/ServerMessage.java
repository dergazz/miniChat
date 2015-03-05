package entity;

import server.sceleton.ClientSocket;

public class ServerMessage extends Message {

    public ServerMessage(Recipient recipient, String text) {
        super(recipient, text);
    }

    @Override
    public String toString() {
        return "SRV " + "[" + getStringDate() + "] " + getText();
    }
}