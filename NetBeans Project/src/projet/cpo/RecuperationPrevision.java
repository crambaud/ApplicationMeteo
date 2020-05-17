package projet.cpo;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import static java.lang.String.valueOf;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import static oracle.jrockit.jfr.events.Bits.floatValue;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ramba
 */
public class RecuperationPrevision {
    
    private String nomVille;
    private String idVille;
    private final ArrayList<Ville> listeVilleID;
    private ArrayList<Prevision> tableauPrevision;

    public RecuperationPrevision(ArrayList<Ville> listeVilleID) {
        this.listeVilleID = listeVilleID;
    }

    public void telechargerDepuisNom(String nomVille) {
        this.nomVille = nomVille;
        
        for(int i=0; i<listeVilleID.size(); i++){
            if(nomVille.equals(listeVilleID.get(i).getNom())){
                idVille=listeVilleID.get(i).getID();
            }
        }
        
        String dossierCible = "src\\projet\\cpo\\fichiers";
        String urlTemp = "http://api.openweathermap.org/data/2.5/forecast?id="+idVille+"&appid=d009966028263d785ee6f4e927f9aefd";
        String url = urlTemp.replaceAll("\\s+","");
        try {
            download(url, dossierCible);
        } catch (IOException ex) {
            Logger.getLogger(IHMPrincipale.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    
    public ArrayList<Prevision> getTableauPrevision(){
        tableauPrevision = new ArrayList<>();
        Prevision prevision;
        
        JSONParser parser = new JSONParser();       
        try (Reader fichierJSON = new FileReader("src\\projet\\cpo\\fichiers\\forecast.json")) {

            JSONObject previsionJSON = (JSONObject) parser.parse(fichierJSON);
            
            JSONObject previsionVille = (JSONObject) previsionJSON.get("city");
            String nomVillePrevision = (String) previsionVille.get("name");
            String idVillePrevision = valueOf(previsionVille.get("id"));
            
            JSONArray previsionCreneau = (JSONArray) previsionJSON.get("list");
            Iterator<JSONObject> iteratorList = previsionCreneau.iterator();
            
            while (iteratorList.hasNext()) {
                prevision = new Prevision();
                prevision.setVille(nomVillePrevision);
                prevision.setId(idVillePrevision);
                JSONObject creneauActuel = iteratorList.next();
                
                Date previsionDT = new Date((String) creneauActuel.get("dt_txt"));
		prevision.setDate(previsionDT);
                
                JSONObject previsionMain = (JSONObject) creneauActuel.get("main");
                prevision.setTemp((float) floatValue(previsionMain.get("temp")));
                prevision.setTemp_ressentie((float) floatValue(previsionMain.get("feels_like")));
                prevision.setTemp_min((float) floatValue(previsionMain.get("temp_min")));
                prevision.setTemp_max((float) floatValue(previsionMain.get("temp_max")));
                prevision.setHumidite((float) floatValue(previsionMain.get("humidity")));
                
                JSONArray previsionWeather = (JSONArray) creneauActuel.get("weather");
                for(Object o : previsionWeather){
                    JSONObject jsonLineItem = (JSONObject) o;
                    prevision.setSymbol((String) jsonLineItem.get("main"));
                    prevision.setDescriptionSymbole((String) jsonLineItem.get("description"));
                }
                
                JSONObject previsionWind = (JSONObject) creneauActuel.get("wind");
                prevision.setVent_vitesse((float) floatValue(previsionWind.get("speed")));
                
                JSONObject previsionPrecipitation = (JSONObject) creneauActuel.get("rain");
                prevision.setPrecipitation((float) floatValue(previsionWind.get("3h")));
                
                tableauPrevision.add(prevision);
            }
  
        } catch (IOException | ParseException e) {
        }
        return tableauPrevision;
    }
    
    
    
    
    
    private static Path download(String sourceURL, String dossierCible) throws IOException {
        URL url = new URL(sourceURL);
        String fileName = "forecast.json";
        Path targetPath = new File(dossierCible + File.separator + fileName).toPath();
        long copy = Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

    return targetPath;}
    
    
}
