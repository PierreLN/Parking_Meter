package Borne_Informatise;

public class CarteCredit {

    private int dateExpAAAA;
    private int dateExpMM;
    private String noCarte;
    private double solde;

    public CarteCredit (double solde, String noCarte, int dateExpAAAA, int dateExpMM) {
        this.solde = solde;
        this.noCarte = noCarte;
        this.dateExpAAAA = dateExpAAAA;
        this.dateExpMM = dateExpMM;
    }

    public double getSolde() {
        return solde;
    }
    public int getDateExpAAAA() {
        return dateExpAAAA;
    }
    public int getDateExpMM() {
        return dateExpMM;
    }
    public String getNoCarte() {
        return noCarte;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }
    public void setNoCarte(String noCarte) {
        this.noCarte = noCarte;
    }
    public void setDateExpMM(int dateExpMM) {
        this.dateExpMM = dateExpMM;
    }
    public void setDateExpAAAA(int dateExpAAAA) {
        this.dateExpAAAA = dateExpAAAA;
    }
}
