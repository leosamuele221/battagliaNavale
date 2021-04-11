package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Tabella extends Finestra implements ActionListener {
    private JButton[][] bottoni = new JButton[10][10];

    public static void main(String[] args) {
        Tabella tab = new Tabella();
        tab.setVisible(true);
    }

    public Tabella() {
        this.setSize(1000, 500); //una sola tab 500x500
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(creaTabellaVuota(), BorderLayout.CENTER);
        add(creaTabellaVuota(), BorderLayout.WEST);
    }

    private JPanel creaTabellaVuota() {
        JPanel pannello = new JPanel();
        pannello.setLayout(new GridLayout(10, 10));

        char lettera = '@';
        for(int riga = 0; riga < 10; riga++) {
            for(int col = 0; col < 10; col++) {
                if(riga == 0 && col > 0) {
                    bottoni[riga][col] = new JButton(""+col);
                    pannello.add(bottoni[riga][col]);
                } else if(col == 0 && riga > 0) {
                    bottoni[riga][col] = new JButton(""+ ++lettera);
                    pannello.add(bottoni[riga][col]);
                } else if(col > 0){
                    bottoni[riga][col] = new JButton("");
                    bottoni[riga][col].setActionCommand(""+lettera+col);
                    pannello.add(bottoni[riga][col]);
                } else {
                    pannello.add(new Button("")); //bottone in alto a sinistra
                }
            }
        }
        return pannello;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

}