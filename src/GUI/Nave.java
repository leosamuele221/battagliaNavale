package GUI;

/*
 1 navi da 4
 2 navi da 1
 3 navi da 2
 4 navi da 1
 */

import java.util.ArrayList;

public class Nave {
    private String posizioneTesta = "";
    private String posizioneCoda = "";
    private int grandezza;
    private boolean affondata = false;
    private ArrayList<String> nave;

    /*
    grandezza = grandezza ESATTA della nave (non la posizione dell'array) se la nave è di 4 blocchi, anche se nell'array la posizione è 3, questo prende 4;
     */
    public Nave(int grandezza, String posTesta, String posCoda) throws Exception {
        if(grandezza > 0 && grandezza < 5) {
            this.grandezza = grandezza;
            nave = new ArrayList<>(grandezza);
        } else {
            throw new Exception("Grandezza errata");
        }
    }
}