package com.doc.maven.readRwite;

import javax.print.DocFlavor.STRING;

public class Voiture {
    String matricule;
	String marque;
	String couleur;
    Double prix;
    Double kilometrage;
    Double vitesse;

    public Voiture(String marque, String matricule, String couleur, double prix, double kilometrage, double vitesse) {
        this.marque = marque;
        this.matricule = matricule;
        this.couleur = couleur;
        this.prix = prix;
        this.kilometrage = kilometrage;
        this.vitesse = vitesse;
    }
	
	public String getMatricule() {
		return matricule;
	}
	public void setmMtricule(String matricule) {
		this.matricule = matricule;
	}
	public String getMarque() {
		return marque;
	}
	public void setMarque(String marque) {
		this.marque = marque;
	}
	public String getCouleur() {
		return couleur;
	}
	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}
    public double getPrix() {
        return prix;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }
    public Double getKilometrage() {
		return kilometrage;
	}
	public void setKilometrage(Double kilometrage) {
		this.kilometrage = kilometrage;
    }
    public Double getVitesse() {
		return vitesse;
	}
	public void setVitesse(Double vitesse) {
		this.vitesse = vitesse;
    }

    @Override
    public String toString() {
        return "Voiture{" +
                "marque = " + this.marque +
                ", matricule=" + this.matricule+
                ", couleur='" + this.couleur+
                ", prix=" + this.prix +
                ", kilometrage=" + this.kilometrage +
                ", vitesse=" + this.vitesse +
                '}';
    }
}
