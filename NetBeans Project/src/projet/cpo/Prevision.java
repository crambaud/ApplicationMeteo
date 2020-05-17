package projet.cpo;

public class Prevision {
    
    private Date date;
    private float temp;
    private float temp_ressentie;
    private float temp_max;
    private float temp_min;
    private String symbol;
    private String descriptionSymbole;
    private float humidite;
    private float vent_vitesse;
    private float precipitation;
    private String ville;
    private String id;

    public Prevision() {}

    public float getPrecipitation() {
        return precipitation;
    }    
    
    public float getHumidite() {
        return humidite;
    }

    public String getSymbol() {
        return symbol;
    }

    public float getTemp() {
        return temp;
    }

    public float getTemp_max() {
        return temp_max;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public float getTemp_ressentie() {
        return temp_ressentie;
    }

    public Date getDate() {
        return date;
    }

    public float getVent_vitesse() {
        return vent_vitesse;
    }

    public String getDescriptionSymbole() {
        return descriptionSymbole;
    }

    public String getVille() {
        return ville;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrecipitation(float precipitation) {
        this.precipitation = precipitation;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
    
    public void setHumidite(float humidite) {
        this.humidite = humidite;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setTemp(float temp) {
        this.temp = temp-273;
    }

    public void setTemp_max(float temp_max) {
        this.temp_max = temp_max-273;
    }

    public void setTemp_min(float temp_min) {
        this.temp_min = temp_min-273;
    }

    public void setTemp_ressentie(float temp_ressentie) {
        this.temp_ressentie = temp_ressentie-273;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setVent_vitesse(float vent_vitesse) {
        this.vent_vitesse = vent_vitesse;
    }

    public void setDescriptionSymbole(String descriptionSymbole) {
        this.descriptionSymbole = descriptionSymbole;
    }
    
    @Override
    public String toString(){
        return date + " :\nTempérature : " + temp + " °C\nTempérature ressentie : " + temp_ressentie + " °C | Température min : " + temp_min + " °C | Température max : " + temp_max + " °C\nVitesse du vent : " + vent_vitesse + " m/s\nHumidité : " + humidite + " %\n" + descriptionSymbole + "\n" + symbol + "\n\n";
    }
    
}
