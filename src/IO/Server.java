package IO;

import java.net.*;

/*
Client manda:
una lettera, un numero (es. A8)
Lettere per le colonnne (da A a J)
Cifre per le righe (da 1 a 10)
Il client può mandare uno 0 in caso di abbandono

Host risponde:
-1 errore
0 acqua
1 colpito
2 affondato
3 finito (tutte le navi affondate)
 */
public class Server{
    private ServerSocket server;
    private Socket client;
    private IO reader;
    private IO writer;

    public static void main(String[]args) {
        Server c = new Server();

        System.out.println("server:"+c.getMessage());
        c.sendMessage("ao figghi i bulla");
    }

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

    private String getMessage() {
        String line = "";
        try {
            reader = new IO('R', client);
            line = reader.read();
            //reader.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return line;
    }

    private void sendMessage(String risp) {
        try {
            writer = new IO('W', client);
            writer.write(risp);
            //writer.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    /*
    public void close() {
        client.close();
    }

     */
}