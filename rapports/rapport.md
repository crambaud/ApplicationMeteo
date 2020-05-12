---
title: "Rapport CPO"
author: Juan-Pablo Lequen & Clément Rambaud
output: html_document
html:
  toc: true
---


# **<center>Rapport Projet CPO - Application Météo</center>**
---

**<center>Juan Pablo Lequen & Clément Rambaud</center>**
<center><a href=https://github.com/crambaud/ApplicationMeteo>https://github.com/crambaud/ApplicationMeteo</a></center>

---

## **Introduction**


---
## **I- Préparation du projet**

### **1-** Planification du projet
Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.

<br>

### **2-** Cahier des charges
Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.


<br>

---
## **II- Réalisation du projet**

### **1-** Programmation

##### Récupération du fichier de prévisions
Afin de garder les prévisions à jour, il est nécéssaire de pouvoir télécharger le fichier de prévisions à tout moment lors de l'éxecution du programme. Pour cela, nous avons créé une classe **recuperationPrevision.java**. Cette classe permet dans un premier temps de télécharger le fichier JSON de prévisions depuis le site, puis, dans un second temps, de récupérer les informations de ce fichier.
Pour récupérer le fichier JSON, il faut tout d'abord obtenir l'URL. Pour cela, on obtient l'ID correspondant au nom de la ville choisie. Ensuite, on créé l'URL et on télécharge le fichier. En plus de cela, on ajoute une fonctionnalité permettant de vérifier si un fichier de prévision est déjà présent en local. Si c'est le cas le fichier en local sera supprimé avant de télécharger le nouveau.

```Java{.line-numbers}
String dossierCible = "src\\Divers";
String urlTemp = "http://api.openweathermap.org/data/2.5/forecast?id="+idVille+"&appid=d009966028263d785ee6f4e927f9aefd";
String url = urlTemp.replaceAll("\\s+","");
try {
  download(url, dossierCible);
} catch (IOException ex) {
  Logger.getLogger(IHMPrincipale.class.getName()).log(Level.SEVERE, null, ex);
}
```
Code de la fonction ``download(String sourceURL, String dossierCible)`` :
```Java{.line-numbers}
private static Path download(String sourceURL, String dossierCible) throws IOException {
  URL url = new URL(sourceURL);
  String fileName = "forecast.json";
  Path targetPath = new File(dossierCible + File.separator + fileName).toPath();
  long copy = Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
  return targetPath;
}
```

##### Lecture du fichier JSON
Dans cette même classe **recuperationPrevision.java**, nous avons créé une méthode ``getTableauPrevision()``. Cette méthode permet, quand elle est appelée, de récupérer les informations de prévisions à partir du fichier **forecast.JSON** téléchargé précédemment. Pour cela on utilise la bibliothèque JSON-simple.

Code de la méthode ``getTableauPrevision()`` :
```Java{.line-numbers}
public ArrayList<Prevision> getTableauPrevision(){
  tableauPrevision = new ArrayList<>();
  Prevision prevision;

  JSONParser parser = new JSONParser();
  try (Reader fichierJSON = new FileReader("src\\Divers\\forecast.json")) {
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

  } catch (IOException | ParseException e) {}
  return tableauPrevision;
}
```


<br>

### **2-** Design de l'interface

##### Partie visuelle
Concernant l'aspect visuel, nous avons dévidé de réaliser une interface moderne, facilement compréhensible et facilement utilisable. Nous avons donc consacré beaucoup de temps à l'élaboration de cette interface, temps qui aurait pu être consacré à l'ajout de certaines fonctionnalités, comme par exemple un système de login. Cependant, nous avons jugé que l'interface était un élément majeur de ce projet, plus important qu'un sytème de login pour reprendre cet exemple.
L'interface a été conçu grace à l'outil de création d'IHM de Netbeans. Cela nous a permis d'etre plus ambitieux concernant son apparence, sans pour autant compliquer le code derrière.
Voici donc l'apparence finale de l'interface :
![interfaceProjetCPO](/assets/interfaceProjetCPO.png)



##### Partie technique



<br>

---
## **Conclusion**
