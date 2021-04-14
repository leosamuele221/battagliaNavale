package IO;

import java.io.*;
import java.net.*;

public class IO{

    private PrintWriter writer;
    private BufferedReader reader;
    private char mode;
    private static Socket socket;

    public IO(char mode, Socket socket) throws Exception{
        if(socket != null){
            try{
                if(mode == 'R'){
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                }else if(mode == 'W'){
                    writer = new PrintWriter(socket.getOutputStream());
                }else throw new Exception("ModeNotValid");
                this.mode = mode;
            }catch(IOException e){
                System.err.println("[ERROR]: errore durante l'inizializzazione dello stream");
                e.printStackTrace();
            }
        }else{
            throw new NullPointerException();
        }
    }

    public void write(String data) throws IOException{
        if(mode == 'W'){
            writer.println(data);
            writer.flush();
        }else{
            System.err.println("[ALERT]: non puoi non puoi effettuare la scrittura se sei in modalità lettura\nOperazine fallita");
        }
    }

    public String read() throws IOException{
        if(mode == 'R'){
            return reader.readLine();
        }else{
            System.err.println("[ALERT]: non puoi non puoi effettuare la lettura se sei in modalità scrittura\nOperazine fallita");
            return null;
        }
    }

    public void close() throws IOException{
        if(mode == 'R') reader.close();
        else writer.close();
    }
}