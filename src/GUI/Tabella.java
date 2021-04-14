package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;

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

public class Tabella extends JFrame implements ActionListener {
    private final int COLONNE = 10;
    private final int RIGHE = 10;
    private final int NAVI_DA1 = 4;
    private final int NAVI_DA2 = 3;
    private final int NAVI_DA3 = 2;
    private final int NAVI_DA4 = 1;
    private int conta = 0;
    private JButton butTmp;
    private String[] punti = new String[2];
    private int grandezza = 0;
    private JButton[][] bottoni = new JButton[RIGHE+1][COLONNE+1];
    private JPanel tabella;
    //ogni tabella deve avere max queste navi
    private int[] naviMax = {NAVI_DA1, NAVI_DA2, NAVI_DA3, NAVI_DA4}; //pos 0 --> navi da 1, pos 1 --> navi da 2, pos 2 --> navi da 3, pos 3 navi da 4, serve a sapere il numero totale di navi
    private HashMap<String, Integer> blocchiNavi = new HashMap<>();

    public Tabella() {
        tabella = creaTabellaVuota();
    }

    private JPanel creaTabellaVuota() {
        JPanel pannello = new JPanel();
        pannello.setLayout(new GridLayout(RIGHE+1, COLONNE+1));
        //char lettera = 'A'-1;
        char[] lettera = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        for(int riga = 0; riga < RIGHE+1; riga++) {
            for(int col = 0; col < COLONNE+1; col++) {
                if(riga == 0 && col > 0) { //riga con le lettere
                    bottoni[riga][col] = new JButton(""+ lettera[col-1]);
                    pannello.add(bottoni[riga][col]);
                    bottoni[riga][col].setEnabled(false);
                } else if(col == 0 && riga > 0) { //colonna i numeri
                    bottoni[riga][col] = new JButton(""+ riga);
                    pannello.add(bottoni[riga][col]);
                    bottoni[riga][col].setEnabled(false);
                } else if(col > 0){ //bottoni vuoti centrali
                    bottoni[riga][col] = new JButton(""+lettera[col-1]+riga);
                    bottoni[riga][col].setActionCommand(""+lettera[col-1]+riga);
                    bottoni[riga][col].addActionListener(this);
                    pannello.add(bottoni[riga][col]);
                } else { //bottone in alto a sinistra
                    bottoni[riga][col] = new JButton("");
                    bottoni[riga][col].setEnabled(false);
                    pannello.add(bottoni[riga][col]);
                }
            }
        }
        return pannello;
    }

    public void inserisciNave(int grandezza, JButton but) throws Exception{
        if(grandezza > 0 && grandezza < 5) {
            if(isGrandezzaTerminata(grandezza)) {
                throw new Exception("Navi di questa dimensione terminate");
            } else {
                this.grandezza = grandezza;
                butTmp = but;
            }
        } else {
            throw new Exception("Dimensione errata");
        }
    }

    private void inserisciNavePriv(JButton but) {
        //controlli prima di inserire le navi
        if(isGrandezzaTerminata(grandezza)) {
            JOptionPane.showMessageDialog(null, "Numero massimo di navi di questa dimensione già aggiunti");
            return;
        }
        if(!arePuntiValidi(punti[0], punti[1])) {
            JOptionPane.showMessageDialog(null, "Punti non validi");
            return;
        }
        //-----------------------------------
        //inserimento navi
        //inserisce la nave (nera) al posto giusto e imposta i pulasanti intorno non editabili (gialli) per evitare di mettere navi accanto
        coloraBlocchi(punti[0], punti[1]);

        //---------------------------------------------------------------------------------------------------------------------------------
        //dopo aver inserito la nave decrementa il pulsante e l'array con tutte
        naviMax[grandezza-1]--;
        int tmp = Character.getNumericValue(butTmp.getText().charAt(11));
        butTmp.setText(grandezza+" blocchi ("+ --tmp +" disponibili)");
    }

    /*
    colora i blocchi (da testa a coda) di nero per vedere le navi
    disabilita (o rende non cliccabili) i pulsanti intorno per evitare che si possano cliccare
    mette le navi nell'HashMap blocchiNavi, così al momento dell'attaco da parte del nemico viene facile trovarli
     */
    private void coloraBlocchi(String testa, String coda) {
    // -------> testa : A10                 coda = J10
            //  riga = 10 col = A
    // -------> ChartAt(1) = riga          CharAt(0) = colonna

        int testaRiga = Integer.parseInt(testa.substring(1));
        int codaRiga = Integer.parseInt(coda.substring(1));

        if(testaRiga == codaRiga) { //sono sulla stessa riga
            if(testa.charAt(0) < coda.charAt(0)) { //es. testa: A1 coda: C1 (3 blocchi)
                for (char k = testa.charAt(0); k < coda.charAt(0)+1; k++) { //va da A a C
                    bottoni[testaRiga][k-'A'+1].setBackground(Color.BLACK);
                }
            } else { //es. testa: E2 coda: C2 (3 blocchi)
                for (char k = coda.charAt(0); k < testa.charAt(0)+1; k++) { //va da C a A
                    bottoni[testaRiga][k-'A'+1].setBackground(Color.BLACK);
                }
            }
        } else { //stessa colonna
            if(testaRiga < codaRiga) { //es. testa: A1 coda: A3 (3 blocchi)
                for (int k = testaRiga; k < codaRiga+1; k++) { //va da 1 a 3
                    bottoni[k][testa.charAt(0)-'A'+1].setBackground(Color.BLACK);
                }
            } else { //es. testa: A3 coda: A1 (3 blocchi)
                for (int k = testaRiga; k > codaRiga-1; k--) { //va da 3 a 1
                    bottoni[k][testa.charAt(0)-'A'+1].setBackground(Color.BLACK);
                }
            }
        }
    }

    /*
    dice se una coppia di punti (testa e coda) di una nave sono validi anche in relazione alla grandezza della nave
     */
    private boolean arePuntiValidi(String testa, String coda) {
        int intTestaTmp = Integer.parseInt(testa.substring(1)); //intTestaTmp = 10 --> A10
        int intCodaTmp = Integer.parseInt(coda.substring(1)); //intCodaTmp = 9 --> B9
        if(grandezza > 1) {
            if(testa.charAt(0) == coda.charAt(0)) { //sono sulla stessa riga
                //testa: A2 coda: A4
                return (intTestaTmp != intCodaTmp) && (intCodaTmp - intTestaTmp +1 == grandezza || intTestaTmp - intCodaTmp +1 == grandezza); //devono essere sulla stessa colonna
            } else { //NON sono sulla stessa riga
                int testaTmp = Character.getNumericValue(testa.charAt(0));
                int codaTmp = Character.getNumericValue(coda.charAt(0));
                return (intTestaTmp == intCodaTmp)  && (codaTmp - testaTmp +1 == grandezza || testaTmp - codaTmp +1 == grandezza);
            }
        } else {
            return (testa.charAt(0) == coda.charAt(0) && intTestaTmp == intCodaTmp);
        }
    }

    public JPanel getTabella() {
        return tabella;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(grandezza != 0) {
            if (conta == 0) {
                punti[0] = e.getActionCommand();
                conta++;
            } else {
                punti[1] = e.getActionCommand();
                conta = 0;
                inserisciNavePriv((JButton)e.getSource());
            }
        }
    }

    private boolean isGrandezzaTerminata(int n) {
        if (n > 0 && n < 5) {
            if (naviMax[n - 1] == 0) {
                this.grandezza = 0;
                return true;
            }
            else return false;
        }
        return false;
    }

    public boolean areNaviDisponibili() {
        for(int k = 0; k < naviMax.length; k++) {
            if(naviMax[k] != 0) return true;
        }
        return false;
    }
}