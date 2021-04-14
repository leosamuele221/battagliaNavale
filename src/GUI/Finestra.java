package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Finestra extends JFrame implements ActionListener {
    private JButton[][] bottoni = new JButton[10][10];
    private boolean naviPosizionate = false;
    private JButton[] butNavi = new JButton[4];
    private Tabella tabellaDiGioco = new Tabella(); //tabella definitiva, dovrà rimanere sempre


    public Finestra(String titolo) {
        super(titolo);
        init();
        this.setVisible(true);
        this.setSize(1000, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /*
    imposta lo stato inziale (in cui bisogna posizionare le navi);
    mostra due tabelle una accanto all'altra in cui
    nella tabella al centro  --> si inseriscono le barche. questa tabella sarà poi la definitiva e rimarrà attiva anche durante il gioco
    mette 4 pulsanti a destra (east) in cui è possibile selezionare le navi da inserire
     */
    public void init() {
        JPanel center = new JPanel(new BorderLayout());
        JPanel east = new JPanel(new FlowLayout());
        JPanel east2 = new JPanel(new GridLayout(4, 1));
        center.add(tabellaDiGioco.getTabella());
        east.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
        // costruisce bottoni per posizionare navi EAST --------------------------------------------------------------------------------

        butNavi[3] = new JButton("4 blocchi (1 disponibili)");
        butNavi[3].addActionListener(this);
        butNavi[3].setActionCommand("but4");
        east2.add(butNavi[3]);

        butNavi[2] = new JButton("3 blocchi (2 disponibili)");
        butNavi[2].addActionListener(this);
        butNavi[2].setActionCommand("but3");
        east2.add(butNavi[2]);

        butNavi[1] = new JButton("2 blocchi (3 disponibili)");
        butNavi[1].addActionListener(this);
        butNavi[1].setActionCommand("but2");
        east2.add(butNavi[1]);

        butNavi[0] = new JButton("1 blocchi (4 disponibili)");
        butNavi[0].addActionListener(this);
        butNavi[0].setActionCommand("but1");
        east2.add(butNavi[0]);

        east.add(east2, BorderLayout.CENTER);

        this.add(center, BorderLayout.CENTER);
        this.add(east, BorderLayout.EAST);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(!naviPosizionate) {
            naviPosizionate = !tabellaDiGioco.areNaviDisponibili();
        }
        JButton but = (JButton)e.getSource();
        switch(e.getActionCommand()) {
            case "but4": //navi 4 blocchi
                butNaviCommand(4, but);
                break;
            case "but3": //navi 3 blocchi
                butNaviCommand(3, but);
                break;
            case "but2":
                butNaviCommand(2, but);
                break;
            case "but1":
                butNaviCommand(1, but);
                break;
        }
    }

    private void butNaviCommand(int grandezza, JButton but) {
        if(grandezza <= 0 && grandezza >= 5) { JOptionPane.showMessageDialog(this, "Errore grandezza"); return; }
        String msg = "Numero massimo di navi di questa dimensione già aggiunti";
        try {
            tabellaDiGioco.inserisciNave(grandezza, but);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this, msg);
            //ex.printStackTrace();
        }
    }
}
