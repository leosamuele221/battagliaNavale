package IO;

import java.util.*;
import java.io.*;

/*
La prima riga del file è la porta

1:8090
 */

public class Conf {
    private final String CONF_FILE_NAME = "conf.txt";
    private final int DEFAULT_PORT = 8080;
    private int port = 0;
    private final File f = new File(CONF_FILE_NAME);

    public Conf() {
        leggiFile();
    }

    private void leggiFile() {
        if(!f.exists()) {
            creaFile();
        } else {
            try {
                Scanner s = new Scanner(f);
                port = s.nextInt();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void creaFile() {
        try {
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            fw.write(DEFAULT_PORT);
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int getPort() {
        if(port <= 1024 || port > 65535) {
            port = 8080;
        }
        return port;
    }

}
