package entity;

import server.sceleton.Resender;

public interface Recipient {

    String getName();

    public void getMessage(Message message);

    public void setResender(Resender resender);

    public Resender getResender();
}
