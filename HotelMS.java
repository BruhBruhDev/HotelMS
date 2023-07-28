
package com.mycompany.hotelms;
import java.util.Scanner;




public class HotelMS {

    static class Text {
        static final String yes = "ja";
        static final String no = "nein";
    }
    static class C {
        private static Scanner input = new Scanner(System.in);
        private static int iList = 1;
        public static void Out(String mess){
            System.out.println(mess);
        }
        public static void OutListEntry(String mess){
            System.out.println(iList+++". "+mess); // haha der code ist so cursed
        }
        public static void OutListEntry(String mess, boolean i0){
            if (i0) iList = 1;
            OutListEntry(mess);
        }
        public static int UserInputInt(int min, int max) {
            return UserInputInt(min,max,"");
        }
        public static int UserInputInt(int min, int max, String mess) {
            Out(mess);
            int num = 0;
            while (true) {
                try {
                    num = Integer.parseInt(input.nextLine());
                    if (num < min || num > max)
                        throw new Exception();
                    break;
                } catch (Exception e) {
                    Out("! Bitte einen Wert zwischen "+min+" und "+max+" eingeben");
                }
            }
            return num;
        }
        public static boolean UserInputBool(String mess, String yes, String no){
            Out(mess);
            String s;
            while (true) {
                s = input.nextLine(); 
                if (s.equals(yes) || s.equals(no)) break;
                Out("! Bitte "+yes+" oder "+no+" eingeben");
            }
            return s.equals(yes);
        }
        public static String UserInputString(String mess){
            Out(mess);
            return input.nextLine();
        }
    }
    private static Room[] rooms = new Room[3];
    private static Room cachedRoomRef = null;
    public static void main(String[] args) {
        Room.INIT();
        int bookingCount = 0;
        while (bookingCount < rooms.length) {
            boolean succ = BookRoom(); // succ is not fully implemented and has no affect
            if (succ){
                rooms[bookingCount++] = cachedRoomRef;
                C.Out("Wollen Sie ein weiteres Zimmer buchen?");
            }
            else
                C.Out("Wollen Sie nochmal ein Zimmer buchen?");
            if(!C.UserInputBool("", Text.yes, Text.no))
                break;
        }
        C.Out("Bitte geben Sie zum Abschluss der Buchung Ihre persönlichen Daten ein:");
        
        Client client = new Client();
        client.name = C.UserInputString("Nachnamen:");
        client.forname = C.UserInputString("Vornamen:");
        client.postalcode = C.UserInputInt(1000, 99999, "PLZ:");
        client.street = C.UserInputString("Straße:");
        client.streetnr = C.UserInputInt(1, 99999, "Hausnr:");
        client.premium = C.UserInputBool("Sind Sie Premiumkunde?", Text.yes, Text.no);
        C.Out("--------------------------------------------------");
        C.Out("Hier ist die Zusammenfassung für Ihre Buchung:   (alle Angaben inkl. MwSt)");
        double sum = 0;
        for (int i = 0; i < bookingCount; i++){
            C.Out(rooms[i].Receipt());
            sum += rooms[i].Price();
        }
        C.Out("--------------------------------------------------");
        C.Out("Summe für alle Buchungen:     "+(int)(sum+0.5)+"€");
        if (client.premium){
            C.Out("Preisnachlass durch Ihre Premiummitgliedschaft -5%");
            C.Out("                              "+(int)(sum*0.95+0.5)+"€");
        }
        C.Out("--------------------------------------------------");
        
        (new Scanner(System.in)).nextLine();
    }
    public static boolean BookRoom() {
        C.Out("Wie viele Personen sollen im Zimmer übernachten (max 6)");
        int personCount = C.UserInputInt(1, 6);
        int type;
        int pType;
        boolean bBoWC;
        while (true) {
            boolean b1T1 = personCount < 2;
            boolean b1T2 = personCount < 3;
            boolean b1T3 = personCount == 3;
            boolean b1T4 = personCount < 7;
            boolean b2T1 = Room.IsT1Available();
            boolean b2T2 = Room.IsT2Available();
            boolean b2T3 = Room.IsT3Available();
            boolean b2T4 = Room.IsT4Available();
            String bookedout = " (ausgebucht)";
            String notapply = " (nicht zutreffened)";
            C.OutListEntry("Einzelzimmer"+(b1T1?(b2T1?"":bookedout): notapply), true);
            C.OutListEntry("Doppelzimmer"+(b1T2?(b2T2?"":bookedout): notapply));
            C.OutListEntry("Triple-Zimmer"+(b1T3?(b2T3?"":bookedout): notapply));
            C.OutListEntry("Appartement"+(b1T4?(b2T4?"":bookedout): notapply));
            C.Out("Welchen Zimmertyp möchten sie buchen:");
            while (true) {
                type = C.UserInputInt(1, 4);
                if (type == 1 && b1T1
                    || type == 2 && b1T2
                    || type == 3 && b1T3
                    || type == 4 && b1T4) break;
                C.Out("! Dieser Zimmertyp ist leider nicht verfügbar, bitte wählen Sie eine andere Option");
            }
            C.Out("Welche Ausstattung bevorzugen Sie?");
            C.OutListEntry("Standard", true);
            C.OutListEntry("Business");
            C.OutListEntry("Premium");
            pType = C.UserInputInt(1, 3);
            bBoWC = false;
            if (type == 1 || type == 2)
                bBoWC = C.UserInputBool("Wünschen Sie ein Zimmer mit Balkon?",Text.yes,Text.no);
            else if (type == 3)
                bBoWC = C.UserInputBool("Wollen Sie ein zweites Badezimmer?",Text.yes,Text.no);
            if (type == 1 && Room.TryBookT1(pType, bBoWC)
                    || type == 2 && Room.TryBookT2(pType, bBoWC)
                    || type == 3 && Room.TryBookT3(pType, bBoWC)
                    || type == 4 && Room.TryBookT4(pType)) {
                break;
            }
            C.Out("Die gewünschte Konfiguration ist nicht verfügbar. Bitte wählen Sie eine andere");
        }
        boolean food = false;
        if (type == 1 || type == 2 || type == 3)
            food = C.UserInputBool("Wünschen Sie Frühstück?", Text.yes, Text.no);
        boolean pet = false;
        boolean service = false;
        if (type == 4){
            pet = C.UserInputBool("Möchten Sie Ihr Haustier mitbringen?", Text.yes, Text.no);
            service = C.UserInputBool("Wünschen Sie einen Zimmerservice?",Text.yes,Text.no);
        }
        int days = 0;
        C.Out("Wie viele Tage möchten Sie bei uns bleiben?");
        if (type == 1 || type == 2 || type == 3)
            days = C.UserInputInt(1, 28);
        else if (type == 4)
            days = C.UserInputInt(3, 28);
        C.Out("(Alle Angaben sind inklusive MWST)\n-- -- -- -- -- -- -- -- -- -- -- -- -- -- --");
        Room room = null;
        if (type == 1)
            room = new T1Room(pType, bBoWC, food, days);
        else if (type == 2)
            room = new T2Room(pType, bBoWC, food, days, personCount);
        else if (type == 3)
            room = new T3Room(pType, food, days, personCount, bBoWC);
        else if (type == 4)
            room = new T4Room(pType, service, pet, days, personCount);
        C.Out(room.Receipt());
        C.Out("-- -- -- -- -- --");
        cachedRoomRef = room;
        return true;
    }
}
