package projet.cpo;

public class Date {
    private final String jour;
    private String mois;
    private final String annee;
    private final String heure;
    private final String moisLettre;

    public Date(String date) {
        this.annee = date.substring(0,4);
        this.mois = date.substring(5,7);
        this.jour = date.substring(8,10);
        this.heure = date.substring(11,13);
        
        switch(mois){
            case "01": 
                moisLettre = "Janvier";
                break;
            case "02": 
                moisLettre = "Février";
                break;
            case "03": 
                moisLettre = "Mars";
                break;
            case "04": 
                moisLettre = "Avril";
                break;
            case "05": 
                moisLettre = "Mai";
                break;
            case "06": 
                moisLettre = "Juin";
                break;
            case "07": 
                moisLettre = "Juillet";
                break;
            case "08": 
                moisLettre = "Aout";
                break;
            case "09": 
                moisLettre = "Septembre";
                break;
            case "10": 
                moisLettre = "Octobre";
                break;
            case "11":
                moisLettre = "Novembre";
                break;
            case "12": 
                moisLettre = "Décembre";
                break;
            default: 
                moisLettre = "Default";
        }
        
        
    }

    public String getAnnee() {
        return annee;
    }

    public String getMois() {
        return mois;
    }
    
    public String getMoisLettre(){
        return moisLettre;
    }

    public String getJour() {
        return jour;
    }

    public String getHeure() {
        return heure;
    }
     
    @Override
    public String toString(){
        return "Le " + jour + " / " + mois + " / " + annee + ", à " + heure + "h00";
    }
}
