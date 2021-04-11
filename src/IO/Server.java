package IO;

import java.net.*;

public class Server{
    private ServerSocket server;
    private Socket client;
    private IO reader;
    private IO writer;

    public Server() {
        connect();
    }

    private void connect(){
        Conf cf = new Conf();
        int port = cf.getPort();
        try{
            server = new ServerSocket(port);
            client = server.accept();
        }catch(Exception io){
            io.printStackTrace();
        }
    }

    private String receiveMessage() {
        String line = "";
        try {
            reader = new IO('R', client);
            line = reader.read();
            reader.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return line;
    }

    private void sendMessage(String risp) {
        try {
            writer = new IO('W', client);
            writer.write(risp);
            writer.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}