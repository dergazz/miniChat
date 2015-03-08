package server.sceleton;

import entity.*;
import server.persistence.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSocket implements Recipient, Removable {

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
//    private ClientSocketListener clientSocketListener;
    private Resender resender;
    private Client client;

    public ClientSocket(Socket socket) throws IOException {
        this.socket = socket;
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
//        clientSocketListener = new ClientSocketListener(this).start();
        new ClientSocketListener(this).start();
//        clientSocketListener.start();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public void setResender(Resender serverSender) {
        this.resender = serverSender;
    }

    @Override
    public Resender getResender() {
        return resender;
    }

    public String readIn() throws IOException {
        return inputStream.readUTF();
    }

    public synchronized void writeOut(String string) throws IOException {
            outputStream.writeUTF(string);
    }

    public void closeSocket(){
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getName() {
        return null;
    }

    @Override
    public void getMessage(Message message) {
        try {
            writeOut(message.toString());
        } catch (IOException e) {
            ChiefManager.clientSocketsManager.onConnectionBreak(this);
        }

    }

    @Override
    public void removeIt() {
        ChiefManager.clientSocketsManager.close(this);
    }

}
