package Borne_Informatise;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Transaction {

    private int tempsTransaction;
    private double numeroTransaction;
    private boolean typeTransactionCC;
    private double total;
    private int compteurCC;
    private int compteurCCMax;

    public Transaction() {
        tempsTransaction = 0;
        numeroTransaction = 0;
        typeTransactionCC = false;
        total = 0;
        compteurCC = 0;
        compteurCCMax = 0;
    }

    public int getTempsTransaction() {
        return tempsTransaction;
    }
    public double getTotal() {
        return total;
    }

    public void setTempsTransaction(int tempsTransaction) {
        this.tempsTransaction = tempsTransaction;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public void setCompteurCC(int compteurCC) {
        this.compteurCC = compteurCC;
    }
    public void setCompteurCCMax(int compteurCCMax) {
        this.compteurCCMax = compteurCCMax;
    }

    public boolean isTypeTransactionCC() {
        return typeTransactionCC;
    }

    // Generation des numeros de transaction
    public String generationNumeroTransaction(Borne borne) {
        // Formatage des numero de transaction
        DecimalFormat df = new DecimalFormat("0000");
        numeroTransaction++;
        return borne.getNumeroBorne() + "-" + String.valueOf(df.format(numeroTransaction));
    }

    // Transaction pas cash
    public void payerPiece(Pieces pieces) {
        tempsTransaction += pieces.getPiece() * 20;

        if (tempsTransaction > 120) {
            tempsTransaction = 120;
        }
        typeTransactionCC = false;
    }

    // Transaction pas carte 0.25$
    public void payerMontantCarteCredit(CarteCredit carteCredit) {
        compteurCC = 0;
        if (carteCredit.getSolde() >= 0.25) {
            tempsTransaction += 5;
            carteCredit.setSolde(carteCredit.getSolde() - 0.25);
            compteurCC=1;
            compteurCCMax++;
        }
        if (tempsTransaction > 120) {
            tempsTransaction = 120;
        }
        typeTransactionCC = true;
    }

    // transaction par carte MAX
    public void payerMaxCarteCredit (CarteCredit carteCredit) {
        if (carteCredit.getSolde() >= 0.25) {
            tempsTransaction = 120;
            compteurCC = 24 - compteurCCMax;
            carteCredit.setSolde(carteCredit.getSolde() - (compteurCC * 0.25));
        }
    }

    // temps de debut de stationnement
    public Date debutStationnement() {
        GregorianCalendar present = new GregorianCalendar();
        return present.getTime();
    }

    // temps de fin de stationnement
    public Date dureeDeStationnement() {
        GregorianCalendar tempsRestant = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        tempsRestant.add(Calendar.MINUTE, tempsTransaction);
        return tempsRestant.getTime();
    }

    // Montant total de la transaction
    public void montantTotal(Pieces pieces) {
        total += pieces.getPiece();
        total += compteurCC * 0.25;
    }
}


