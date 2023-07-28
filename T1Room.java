
package com.mycompany.hotelms;

public class T1Room extends Room {
    boolean food;
    boolean balcony;
    public T1Room(int pType, boolean balcony, boolean food, int days) {
        this.pType = pType;
        this.balcony = balcony;
        this.food = food;
        this.days = days;
        // people is irrelevant; has no logic attached to it
    }
    @Override
    public double Price() {
        double sum = pRoom[0] * pTypes[pType - 1];
        if (balcony)
            sum += pBalcony;
        if (food)
            sum += pFood;
        return sum * MwSt * days;
    }
    @Override
    public String Receipt(){
        String s = "Preisberechnung für Ihr Einzelzimmer\n";
        double sum = pRoom[0] * pTypes[pType - 1];
        s +=       "Grundbetrag: "+(int)(sum*MwSt+0.5)+"€\n";
        if (balcony){
            sum += pBalcony;
            s +=   "Balkon:      "+(int)(pBalcony*MwSt+0.5)+"€\n";
        }
        else
            s +=   "Balkon:      --\n";
        if (food){
            sum += pFood;
            s +=   "Frühstück:   "+(int)(pFood*MwSt*+0.5)+"€\n";
        }
        else
            s +=   "Frühstück:   --\n";
        s +=       "     | Tage "+days+" für "+(int)(sum*MwSt+0.5)+"€/Tag\n";
        s +=       "     | Gesamt  "+(int)(sum * MwSt * days+0.5)+"€\n";
        return s;
    }
}