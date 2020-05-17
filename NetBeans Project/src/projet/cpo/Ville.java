package projet.cpo;

/**
 *
 * @author ramba
 */
public class Ville {
    private String ID;
    private String nom;
    
    public Ville() {
    }
    

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    
    public String getID() {
        return ID;
    }

    public String getNom() {
        return nom;
    }
    
    @Override
    public String toString(){
        return nom;
    }

}
