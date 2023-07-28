
package com.mycompany.hotelms;

public class T2Room extends Room {
    boolean food;
    boolean balcony;
    public T2Room(int pType, boolean balcony, boolean food, int days, int people) {
        this.pType = pType;
        this.balcony = balcony;
        this.food = food;
        this.days = days;
        this.people = people;
    }
    @Override
    public double Price() {
        double sum = pRoom[1] * pTypes[pType - 1];
        if (balcony)
            sum += pBalcony;
        if (food)
            sum += pFood * people;
        if (people == 1)
            sum += pSolo;
        return sum * MwSt * days;
    }
    @Override
    public String Receipt(){
        String s = "Preisberechnung für Ihr Doppelzimmer für "+people+" Person/en\n";
        double sum = pRoom[1] * pTypes[pType - 1];
        
        s +=       "Grundbetrag: "+(int)(sum*MwSt+0.5)+"€\n";
        if (balcony){
            sum += pBalcony;
            s +=   "Balkon:      "+(int)(pBalcony*MwSt+0.5)+"€\n";
        }
        else
            s +=   "Balkon:      --\n";
        if (food){
            sum += pFood*people;
            s +=   "Frühstück:   "+(int)(pFood*MwSt*+0.5)+"€"+(people==1?"":" pro Person")+"\n";
        }
        else
            s +=   "Frühstück:   --\n";
        if (people == 1){
            sum += pSolo;
            s +=   "Einzelbelegung: "+(int)(pSolo*MwSt+0.5)+"€\n";
        }
        s +=       "     | Tage "+days+" für "+(int)(sum*MwSt+0.5)+"€/Tag\n";
        s +=       "     | Gesamt  "+(int)(sum * MwSt * days+0.5)+"€\n";
        return s;
    }
}