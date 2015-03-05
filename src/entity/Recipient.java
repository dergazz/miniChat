package entity;

import java.io.IOException;

public interface Recipient {

    String getName();

    public  void send(Message message);

    public void setResender(Resender resender);

    public Resender getResender();
}
