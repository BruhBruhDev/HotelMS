
package com.mycompany.hotelms;

public class T3Room extends Room {
    boolean wc;
    boolean food;
    public T3Room(int pType, boolean food, int days, int people, boolean wc) {
        this.pType = pType;
        this.food = food;
        this.days = days;
        this.people = people;
        this.wc = wc;
    }
    @Override
    public double Price() {
        double sum = pRoom[2] * pTypes[pType - 1];
        if (food)
            sum += pFood * people;
        if (wc)
            sum += pWC;
        return sum * MwSt * days;
    }
    @Override
    public String Receipt(){
        String s = "Preisberechnung für Ihr Triplezimmer für "+people+" Personen\n";
        double sum = pRoom[1] * pTypes[pType - 1];
        s +=       "Grundbetrag: "+(int)(sum*MwSt+0.5)+"€\n";
        if (wc){
            sum += pWC;
            s +=   "Badezimmer:  "+(int)(pWC*MwSt+0.5)+"€\n";
        }
        else
            s +=   "Badezimmer:  --\n";
        if (food){
            sum += pFood*people;
            s +=   "Frühstück:   "+(int)(pFood*MwSt*+0.5)+"€ pro Person\n";
        }
        else
            s +=   "Frühstück:   --\n";
        s +=       "     | Tage "+days+" für "+(int)(sum*MwSt+0.5)+"€/Tag\n";
        s +=       "     | Gesamt  "+(int)(sum * MwSt * days+0.5)+"€\n";
        return s;
    }
}
