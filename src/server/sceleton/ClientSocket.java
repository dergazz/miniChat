package server.sceleton;

import entity.ServerMessage;

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

    public ClientSocket(Socket socket) throws IOException {
        this.socket = socket;
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
        clientSocketListener = new ClientSocketListener(this);
        clientSocketListener.start();
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

    public String readUTF() throws IOException {
        return inputStream.readUTF();
    }

    public boolean writeUTF(String string){
        try {
            outputStream.writeUTF(string);
            outputStream.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }





}
