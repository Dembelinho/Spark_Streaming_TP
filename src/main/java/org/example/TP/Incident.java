package org.example.TP;

import java.io.Serializable;

public class Incident implements Serializable {
    private String Id;
    private String titre;
    private String description;
    private String service;
    private String date;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Incident(String date,String description,String id,  String service,String titre ) {
        this.Id = id;
        this.titre = titre;
        this.description = description;
        this.service = service;
        this.date = date;
    }
// constructeur, getters et setters

    // Assurez-vous de définir un constructeur sans arguments pour Spark
    public Incident() {}

    // Autres méthodes si nécessaire
}

