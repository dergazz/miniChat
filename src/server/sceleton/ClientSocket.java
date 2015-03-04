package server.sceleton;

import entity.ServerMessage;
import server.persistence.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSocket {

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private ClientSocketListener clientSocketListener;
    private ServerSender serverSender;
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

    public void setServerSender(ServerSender serverSender) {
        this.serverSender = serverSender;
    }

    public ServerSender getServerSender() {
        return serverSender;
    }

    public void sendServerMessage(ServerMessage serverMessage) {
        serverSender.addServerMessage(serverMessage);
    }

    public String readIn() throws IOException {
        return inputStream.readUTF();
//        try {
//        } catch (IOException e) {
//            return "ERROR";
//        }
    }

    public synchronized void writeOut(String string) throws IOException {
        outputStream.writeUTF(string);
//        try {
//            outputStream.flush();
//        } catch (IOException e) {
//            return false;
//        }
//        return true;
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



}
