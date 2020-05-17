package projet.cpo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.simple.parser.ParseException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        
        // Création de la liste des villes + ID :
        
        ArrayList<Ville> listeVillesID = new ArrayList<>();
        Ville ville;
        try (Scanner scanner = new Scanner(new File("src\\projet\\cpo\\fichiers\\villes.csv"))) {
            scanner.useDelimiter(";");
            while (scanner.hasNext()) {
                ville = new Ville();
                ville.setID(scanner.next());
                ville.setNom(scanner.next());
                listeVillesID.add(ville);
            }
        }
        
        
        // Lecture du fichier JSON & création de l'arryList des prévisions
        
        RecuperationPrevision firstRecup = new RecuperationPrevision(listeVillesID);
        firstRecup.telechargerDepuisNom(firstRecup.getTableauPrevision().get(0).getVille());
        
        
        // Ouverture de l'IHM
        
        IHMPrincipale IHM = new IHMPrincipale(listeVillesID, firstRecup.getTableauPrevision());
        IHM.setVisible(true);
    
    }
    
}