
package com.mycompany.hotelms;

public class T4Room extends Room {
    boolean service;
    boolean pet;
    public T4Room(int pType, boolean service, boolean pet, int days, int people) {
        this.pType = pType;
        this.service = service;
        this.pet = pet;
        this.days = days;
        this.people = people;
    }
    @Override
    public double Price() {
        double sum = pRoom[3] * pTypes[pType - 1];
        if (service)
            sum += pService;
        if (pet)
            sum += pPet; 
        return sum * MwSt * days;
    }
    @Override
    public String Receipt(){
        String s = "Preisberechnung für Ihr Appartement für "+people+" Personen\n";
        double sum = pRoom[1] * pTypes[pType - 1];
        s +=       "Grundbetrag: "+(int)(sum*MwSt+0.5)+"€\n";
        if (service){
            sum += pService;
            s +=   "Service:     "+(int)(pService*MwSt+0.5)+"€\n";
        }
        else
            s +=   "Service:     --\n";
        if (pet){
            sum += pPet;
            s +=   "Haustier:    "+(int)(pPet*MwSt*+0.5)+"€\n";
        }
        else
            s +=   "Haustier:    --\n";
        s +=       "     | Tage "+days+" für "+(int)(sum*MwSt+0.5)+"€/Tag\n";
        s +=       "     | Gesamt  "+(int)(sum * MwSt * days+0.5)+"€\n";
        return s;
    }
}