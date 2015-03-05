package server.sceleton;

import entity.Message;
import entity.Recipient;
import entity.Resender;
import server.persistence.Client;
import server.persistence.Manager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSocket implements Recipient {

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private ClientSocketListener clientSocketListener;
    private Resender resender;
    private Client client;

    public ClientSocket(Socket socket) throws IOException {
        this.socket = socket;
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
        clientSocketListener = new ClientSocketListener(this);
        clientSocketListener.start();
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
        if (socket.isConnected())
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
    public synchronized void send(Message message) {
        try {
            writeOut(message.toString());
        } catch (IOException e) {
            Manager.clientSocketsManager.onConnectionBreak(this);
        }

    }
}
