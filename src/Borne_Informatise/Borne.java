package Borne_Informatise;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Borne {

    private String numeroBorne;

    public Borne(String numeroBorne) {
        this.numeroBorne = numeroBorne;
    }

    public String getNumeroBorne() {
        return numeroBorne;
    }
    public void setNumeroBorne(String numeroBorne) {
        this.numeroBorne = numeroBorne;
    }

    // Formatage
    DecimalFormat df = new DecimalFormat("0.00");

    // Verification du numero de la borne
    public boolean verificationNumeroBorne() {
        Pattern p = Pattern.compile("[A-D]\\d{3}");
        Matcher m = p.matcher(getNumeroBorne());
        if (getNumeroBorne().length() >= 5)
            return false;
        return m.find();
    }

    // Verification de la date expiration de la carte
    public boolean verificationDateExpCC(CarteCredit carteCredit) {
        GregorianCalendar present = new GregorianCalendar();
        if (carteCredit.getDateExpAAAA() == present.get(Calendar.YEAR)) {
            if (carteCredit.getDateExpMM() < 13) {
                // Le Calendrier utilise les mois {0-11} ... oh well
                if (carteCredit.getDateExpMM() - 1 > present.get(Calendar.MONTH)) {
                    return true;
                }
            }
        } else if (carteCredit.getDateExpAAAA() > present.get(Calendar.YEAR)) {
            if (carteCredit.getDateExpMM() < 13) {
                return true;
            }
        }
        return false;
    }

    // Verification du numero de la carte
    public boolean verificationNoCartedeCredit(CarteCredit carteCredit) {
        Pattern p = Pattern.compile("\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}");
        Matcher m = p.matcher(carteCredit.getNoCarte());
        return m.find();
    }

    // Verification des heures payables
    public boolean verificationHeureGratuit() {
        GregorianCalendar present = new GregorianCalendar();
        GregorianCalendar semainePasGratuitDebut = new GregorianCalendar();
        GregorianCalendar semainePasGratuitFin = new GregorianCalendar();
        GregorianCalendar samediPasGratuitDebut = new GregorianCalendar();
        GregorianCalendar samediPasGratuitFin = new GregorianCalendar();
        GregorianCalendar dimanchePasGratuitDebut = new GregorianCalendar();
        GregorianCalendar dimanchePasGratuitFin = new GregorianCalendar();

        dimanchePasGratuitDebut.set(Calendar.HOUR_OF_DAY, 13);
        dimanchePasGratuitFin.set(Calendar.HOUR_OF_DAY, 18);
        samediPasGratuitDebut.set(Calendar.HOUR_OF_DAY, 9);
        samediPasGratuitFin.set(Calendar.HOUR_OF_DAY, 18);
        semainePasGratuitDebut.set(Calendar.HOUR_OF_DAY, 9);
        semainePasGratuitFin.set(Calendar.HOUR_OF_DAY, 21);

        if (present.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            if (present.get(Calendar.HOUR_OF_DAY) >= dimanchePasGratuitDebut.get(Calendar.HOUR_OF_DAY)) {
                return present.get(Calendar.HOUR_OF_DAY) <= dimanchePasGratuitFin.get(Calendar.HOUR_OF_DAY);
            }
        } else if (present.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            if (present.get(Calendar.HOUR_OF_DAY) >= samediPasGratuitDebut.get(Calendar.HOUR_OF_DAY)) {
                return present.get(Calendar.HOUR_OF_DAY) <= samediPasGratuitFin.get(Calendar.HOUR_OF_DAY);
            }
        } else {
            if (present.get(Calendar.HOUR_OF_DAY) >= semainePasGratuitDebut.get(Calendar.HOUR_OF_DAY)) {
                return present.get(Calendar.HOUR_OF_DAY) <= semainePasGratuitFin.get(Calendar.HOUR_OF_DAY);
            }
        }

        return false;
    }

    // Affichage des donnees de transaction
    public String affichagebouttonOK(CarteCredit carteCredit, Transaction transaction, Borne borne) {
        String noCarteCredit = carteCredit.getNoCarte();
        String affichage;
        if (transaction.isTypeTransactionCC()) {
            affichage = "Type de paiement - Carte de Crédit: **** **** ****" + noCarteCredit.substring(14, 19)
                    + " \nNuméro de transaction: " + transaction.generationNumeroTransaction(borne)
                    + " \nCoût de la transaction: " + df.format(transaction.getTotal()) + "$"
                    + " \nDébut de la durée de stationnement : " + transaction.debutStationnement()
                    + " \nFin de la durée du stationnement: " + transaction.dureeDeStationnement()
                    + " \nL'espace de stationnement: " + borne.getNumeroBorne()
                    + " \nDurée de stationnement: " + transaction.getTempsTransaction() + " mins";
        } else {
            affichage = "Type de paiement - Comptant: "
                    + " \nNuméro de transaction: " + transaction.generationNumeroTransaction(borne)
                    + " \nCoût de la transaction: " + df.format(transaction.getTotal()) + "$"
                    + " \nDébut de la durée de stationnement : " + transaction.debutStationnement()
                    + " \nFin de la durée du stationnement: " + transaction.dureeDeStationnement()
                    + " \nL'espace de stationnement: " + borne.getNumeroBorne()
                    + " \nDurée de stationnement: " + transaction.getTempsTransaction() + " mins";
        }
        return affichage;
    }

    // Netoyage des parametres de la borne
    public void nettoyageBorne(Borne borne, Transaction transaction, CarteCredit carteCredit, Pieces pieces) {
        borne.setNumeroBorne("");

        transaction.setTotal(0);
        transaction.setTempsTransaction(0);
        transaction.setCompteurCCMax(0);
        transaction.setCompteurCC(0);
        pieces.setPiece(0);
        carteCredit.setNoCarte("");
        carteCredit.setDateExpAAAA(0);
        carteCredit.setDateExpMM(0);

    }

    // Combine la methode d'ajout du temps et du montant de la class Transaction
    public void transactionUnifierPieces(Transaction transaction, Pieces pieces) {
        transaction.montantTotal(pieces);
        transaction.payerPiece(pieces);
    }

    public boolean montantMaxAtteint(Transaction transaction) {
        return transaction.getTotal() >= 6;
    }


    public String AffichagemontantMaxAtteintCC_025(Transaction transaction, CarteCredit carteCredit, Pieces pieces) {
        String message;
        if (verificationNoCartedeCredit(carteCredit)) {
            if (verificationDateExpCC(carteCredit)) {

                // Ajouter le montant au total
                transaction.payerMontantCarteCredit(carteCredit);
                transaction.montantTotal(pieces);

                if (montantMaxAtteint(transaction)) {
                    message = (transaction.getTempsTransaction() + " mins, Montant total maximal atteint: " + df.format(transaction.getTotal()) + "$ ");
                }
                else {
                    message = (transaction.getTempsTransaction() + "mins, Montant total: " + df.format(transaction.getTotal()) + "$");
                }
            }
            else {
                message = "Date d'expiration de la carte de crédit est invalide, recommencez!";
            }
        }
        else {
            message = "Numéro de carte de crédit invalide, recommencez!";
        }
        return message;
    }

    public String AffichagemontantMaxAtteintCC_MAX(Transaction transaction, CarteCredit carteCredit, Pieces pieces) {
        String message;
        if (verificationNoCartedeCredit(carteCredit)) {
            if (verificationDateExpCC(carteCredit)) {

                // Ajouter le montant au total
                transaction.payerMaxCarteCredit(carteCredit);
                transaction.montantTotal(pieces);
                if (montantMaxAtteint(transaction)) {
                    message = (transaction.getTempsTransaction() + " mins, Montant total maximal atteint: " + df.format(transaction.getTotal()) + "$ ");
                }
                else {
                    message = (transaction.getTempsTransaction() + "mins, Montant total: " + df.format(transaction.getTotal()) + "$");
                }
            }
            else {
                message = "Date d'expiration de la carte de crédit est invalide, recommencez!";
            }
        }
        else {
            message = "Numéro de carte de crédit invalide, recommencez!";
        }
        return message;
    }



}

