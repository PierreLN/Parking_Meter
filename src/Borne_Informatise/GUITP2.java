package Borne_Informatise;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;

public class GUITP2 {
    private JPanel panel1;
    private JLabel labelLogo;
    private JPanel panelNumeros;
    private JPanel panelDroite;
    private JPanel panelComptant;
    private JLabel champMessage;
    private JButton bouton25;
    private JButton bouton100;
    private JButton bouton200;
    private JPanel panelCredit;

    private JButton bouton25Credit;
    private JButton boutonMaxCredit;
    private JButton boutonOk;
    private JTextArea zoneRecu;
    private JButton boutonRapport;
    private JFormattedTextField champNumeroCarte;
    private JFormattedTextField champDateExp;
    private JComboBox comboBox1;


    private EcouteurNumero ecouteurNumero;
    private EcouteurCarteCredit ecouteurCarteCredit;
    private EcouteurMonnaie ecouteurMonnaie;
    private EcouteurControles ecouteurControles;
    private EcouteurEntree ecouteurEntree;

    // variables utiles pour vous
    DecimalFormat df = new DecimalFormat("0.00");
    String place =""; //place de stationnement choisie
    Borne borne; // borne à créer dans le constructeur   borne null pour le moment
    CarteCredit carteCredit = new CarteCredit(20,"0",0,0);
    Transaction transaction = new Transaction();
    Pieces pieces = new Pieces(0);
    double total = 0;
    double montantDansBorne = 0;

    public GUITP2() throws ParseException {


        //labelLogo.setIcon(new ImageIcon("logo.png")); // placer à la racine du dossier de votrre projet
        //Création des écouteurs
        ecouteurNumero = new EcouteurNumero();
        ecouteurCarteCredit = new EcouteurCarteCredit();
        ecouteurMonnaie = new EcouteurMonnaie();
        ecouteurControles = new EcouteurControles();
        ecouteurEntree = new EcouteurEntree();


        // panelNumeros avec la grille
        GridBagLayout gl = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();


        panelNumeros.setLayout(gl);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        for (int i = 0; i < 15; i++) {
            JButton temp = new JButton();
            temp.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
            temp.setForeground(new Color(0, 174, 239));
            temp.addActionListener(ecouteurNumero);
            if (i == 0)
                temp.setText("A");

            else if (i == 1)
                temp.setText("B");
            else if (i == 2)
                temp.setText("C");

            else if (i == 3) {
                c.gridwidth = GridBagConstraints.REMAINDER; //end row
                temp.setText("D");
            } else if (i <= 6) {
                c.weightx = 1;
                c.gridwidth = 1;
                temp.setText(String.valueOf(i - 4));
            } else if (i == 7) {
                c.gridwidth = GridBagConstraints.REMAINDER; //end row
                temp.setText(String.valueOf(i - 4));
            } else if (i <= 10) {
                c.weightx = 1;
                c.gridwidth = 1;
                temp.setText(String.valueOf(i - 4));
            } else if (i == 11) {
                c.gridwidth = GridBagConstraints.REMAINDER; //end row
                temp.setText(String.valueOf(i - 4));
            } else if (i <= 13) {
                c.weightx = 1;
                c.gridwidth = 1;
                temp.setText(String.valueOf(i - 4));
            } else if (i == 14) {
                c.gridwidth = GridBagConstraints.REMAINDER; //end row
                temp.setText("entrée");
                temp.removeActionListener(ecouteurNumero);
                temp.addActionListener(ecouteurEntree);
            }
            gl.setConstraints(temp, c);
            panelNumeros.add(temp);

        }
        // inscrire les sources à l'écouteur
        bouton25.addActionListener(ecouteurMonnaie);
        bouton100.addActionListener(ecouteurMonnaie);
        bouton200.addActionListener(ecouteurMonnaie);

        bouton25Credit.addActionListener(ecouteurCarteCredit);
        boutonMaxCredit.addActionListener(ecouteurCarteCredit);
        boutonOk.addActionListener(ecouteurControles);
        boutonRapport.addActionListener(ecouteurControles);

        // Créer objet Borne
        borne = new Borne("");

        // Desactivation des boutons
        boutonOk.setEnabled(false);
        boutonCashOff();
        boutonCarteCreditOff();

        // Vérifier si la Borne est en fonction
        if (borne.verificationHeureGratuit()) {
            champMessage.setText("Borne active, entrez le montant");
        }
        else {
            champMessage.setText("Heure gratuite");
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        try {
            champNumeroCarte = new JFormattedTextField(new MaskFormatter("#### #### #### ####"));
            champDateExp = new JFormattedTextField(new MaskFormatter("##/##"));
        }
        catch ( ParseException pe)
        {
            pe.printStackTrace();
        }
    }
    private void $$$setupUI$$$() {
        createUIComponents();
    }
    private class EcouteurNumero implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {

            //lettre ou chiffre du bouton qu'on a cliqué dessus
           String lettreChiffre = ((JButton)e.getSource()).getText();
           boutonNumeroLettre_actionPerformed( lettreChiffre);
           

        }
    }

    private class EcouteurEntree implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            boutonEntree_actionPerformed();
        }
    }

    private class EcouteurMonnaie implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            if ( e.getSource() == bouton25)
                bouton25_actionPerformed();
            else if ( e.getSource()==bouton100)
                bouton100_actionPerformed();
            else if ( e.getSource() == bouton200)
                bouton200_actionPerformed();
        }
    }

    private class EcouteurCarteCredit implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {

             if ( e.getSource() == bouton25Credit)
                bouton25Credit_actionPerformed();
            else if (e.getSource() == boutonMaxCredit)
                boutonMaxCredit_actionPerformed();
        }
    }

    private class EcouteurControles implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if ( e.getSource() == boutonOk)
                boutonOK_actionPerformed();
            else if ( e.getSource() == boutonRapport)
                boutonRapport_actionPerformed();
        }
    }

    public void boutonCashOn() {
        bouton25.setEnabled(true);
        bouton100.setEnabled(true);
        bouton200.setEnabled(true);
    }
    public void boutonCashOff() {
        bouton25.setEnabled(false);
        bouton100.setEnabled(false);
        bouton200.setEnabled(false);
    }
    public void boutonCarteCreditOn() {
        bouton25Credit.setEnabled(true);
        boutonMaxCredit.setEnabled(true);
        champDateExp.setEnabled(true);
        champNumeroCarte.setEnabled(true);
    }
    public void boutonCarteCreditOff() {
        bouton25Credit.setEnabled(false);
        boutonMaxCredit.setEnabled(false);
        champDateExp.setEnabled(false);
        champNumeroCarte.setEnabled(false);
    }

    private void bouton25_actionPerformed() {
        // Ajout d'un 0.25$
        pieces.setPiece(0.25);
        // Ajout du temps et montant
        borne.transactionUnifierPieces(transaction, pieces);
        // Verification du montant
        if (borne.montantMaxAtteint(transaction)) {
            boutonCashOff();
            champMessage.setText(transaction.getTempsTransaction() + " mins, Montant total maximal atteint: " + df.format(transaction.getTotal()) + "$ ");
        }
        else {
            champMessage.setText(transaction.getTempsTransaction() + "mins, Montant total: " + df.format(transaction.getTotal()) + "$");
        }
        boutonCarteCreditOff();
        boutonOk.setEnabled(true);
    }

    private void bouton100_actionPerformed() {
        // Ajout d'un 1.00$
        pieces.setPiece(1);
        // Ajout du temps et montant
        borne.transactionUnifierPieces(transaction, pieces);
        // Verification du montant
        if (borne.montantMaxAtteint(transaction)) {
            boutonCashOff();
            champMessage.setText(transaction.getTempsTransaction() + " mins, Montant total maximal atteint: " + df.format(transaction.getTotal()) + "$ ");
        }
        else {
            champMessage.setText(transaction.getTempsTransaction() + "mins, Montant total: " + df.format(transaction.getTotal()) + "$");
        }
        boutonCarteCreditOff();
        boutonOk.setEnabled(true);
    }

    private void bouton200_actionPerformed() {
        // Ajout d'un 2.00$
        pieces.setPiece(2);
        // Ajout du temps et montant
        borne.transactionUnifierPieces(transaction, pieces);
        // Verification du montant
        if (borne.montantMaxAtteint(transaction)) {
            boutonCashOff();
            champMessage.setText(transaction.getTempsTransaction() + " mins, Montant total maximal atteint: " + df.format(transaction.getTotal()) + "$ ");
        }
        else {
            champMessage.setText(transaction.getTempsTransaction() + "mins, Montant total: " + df.format(transaction.getTotal()) + "$");
        }
        boutonCarteCreditOff();
        boutonOk.setEnabled(true);
    }

    private void bouton25Credit_actionPerformed() {
        // Capture le numero de carte de credit
        String noCarteCredit = champNumeroCarte.getText();
        carteCredit.setNoCarte(noCarteCredit);

        String text1 = champDateExp.getText();
        if (text1.length() >= 4) {
            // Capturer le mois d'expiration
            String moisExp = text1.substring(0, 2);
            int moisExpCC = Integer.parseInt(moisExp);
            carteCredit.setDateExpMM(moisExpCC);

            // Capturer l'annee d'expiration
            String anneeExp = "20" + text1.substring(3,5);
            int anneeExpCC = Integer.parseInt(anneeExp);
            carteCredit.setDateExpAAAA(anneeExpCC);
        }
        else {
            champMessage.setText("Date d'expiration de la carte de crédit est invalide, recommencez!");
        }


        // Verification du numero de la CC et de la date d'expiration
        champMessage.setText(borne.AffichagemontantMaxAtteintCC_025(transaction, carteCredit, pieces));
        if (borne.montantMaxAtteint(transaction)) {
            boutonCashOff();
            boutonCarteCreditOff();
        }
        boutonOk.setEnabled(true);
    }

    private void boutonMaxCredit_actionPerformed() {
        // Capture le numero de carte de credit
        String noCarteCredit = champNumeroCarte.getText();
        carteCredit.setNoCarte(noCarteCredit);

        String text1 = champDateExp.getText();
        if (text1.length() >= 4) {
            // Capturer le mois d'expiration
            String moisExp = text1.substring(0, 2);
            int moisExpCC = Integer.parseInt(moisExp);
            carteCredit.setDateExpMM(moisExpCC);

            // Capturer l'annee d'expiration
            String anneeExp = "20" + text1.substring(3,5);
            int anneeExpCC = Integer.parseInt(anneeExp);
            carteCredit.setDateExpAAAA(anneeExpCC);
        }
        else {
            champMessage.setText("Date d'expiration de la carte de crédit est invalide, recommencez!");
        }

        // Verification du numero de la CC et de la date d'expiration
        champMessage.setText(borne.AffichagemontantMaxAtteintCC_MAX(transaction, carteCredit, pieces));
        if (borne.montantMaxAtteint(transaction)) {
            boutonCashOff();
            boutonCarteCreditOff();
        }
        boutonOk.setEnabled(true);
    }

    public void boutonNumeroLettre_actionPerformed(String lettreChiffre)
    {
        // Numero de la borne
        place += lettreChiffre;
        champMessage.setText(place);
    }

    private void boutonEntree_actionPerformed() {

        // Verification du numero de la borne
        String texte = champMessage.getText();
        borne.setNumeroBorne(texte);

        if (borne.verificationNumeroBorne()){
            champMessage.setText("Place dispobile, insérez un montant");
            boutonCarteCreditOn();
            boutonCashOn();
        }
        else {
            champMessage.setText("Borne invalide, recommencez");
            place = "";
            boutonCarteCreditOff();
            boutonCashOff();
            boutonOk.setEnabled(false);
            }
    }

    private void boutonOK_actionPerformed() {
        // Finalisation de la transaction et affichage de la transaction
        zoneRecu.setText(borne.affichagebouttonOK(carteCredit, transaction, borne));

        // Verouillage des boutons pour transaction additionelle
        boutonCarteCreditOff();
        boutonCashOff();

        // Ajout au montant total dans la borne
        montantDansBorne += transaction.getTotal();

        // Netoyage des parametres de la borne
        champMessage.setText("");
        place = "";
        champNumeroCarte.setText("");
        champDateExp.setText("");
        //champDateExp;
        borne.nettoyageBorne(borne, transaction, carteCredit, pieces);
    }

    private void boutonRapport_actionPerformed()
    {
      champMessage.setText("Montant total dans la borne: " + String.valueOf(df.format(montantDansBorne)) + "$");
    }

    public static void main(String[] args) {
        try {
            JFrame frame = new JFrame("GUITP2");
            frame.setContentPane(new GUITP2().panel1);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 950);
            //frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
        catch ( Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
