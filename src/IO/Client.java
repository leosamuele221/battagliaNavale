package IO;

import java.io.IOException;
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
public class Client{

    private Socket client;
    private IO reader = null;
    private IO writer = null;

    public static void main(String[]args) {
        Client c = new Client("127.0.0.1");
        c.sendMessage("aygyghhbulla");
        System.out.println(c.getMessage());
    }

    public Client(String host) {
        InetAddress ip = null;
        if(isValid(host)) {
            try {
                ip = InetAddress.getByName(host);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            creaSocket(ip);
        } else {
            throw new IllegalArgumentException("IP non valido");
        }
    }

    public static boolean isValid(String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return ip.matches(PATTERN);
    }

    private void creaSocket(InetAddress ip) {
        Conf cf = new Conf();
        try {
            client = new Socket(ip, cf.getPort());
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getMessage() {
        String line = "";
        try {
            if(reader == null) reader = new IO('R', client);
            line = reader.read();
            //reader.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return line;
    }

    public void sendMessage(String risp) {
        try {
            if(writer == null) writer = new IO('W', client);
            writer.write(risp);
            //writer.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}