
package com.mycompany.hotelms;

public abstract class Room {
    int days;
    int people;
    int pType;
    abstract double Price();
    abstract String Receipt();
    
    static double MwSt = 1.19;
    static double[] pTypes = new double[]{1, 1.2, 1.5};
    static int[] pRoom = new int[]{100, 140, 180, 300};
    static int pBalcony = 45;
    static int pFood = 20;
    static int pSolo = 50;
    static int pWC = 40;
    static int pPet = 30;
    static int pService = 35;

    static int[][] T1_RoomBooked = new int[3][2];
    static int[][] T1_RoomCount = new int[3][2];
    static int[][] T2_RoomBooked = new int[3][2];
    static int[][] T2_RoomCount = new int[3][2];
    static int[][] T3_RoomBooked = new int[3][2];
    static int[][] T3_RoomCount =  new int[3][2];
    static int[] T4_RoomBooked = new int[3];
    static int[] T4_RoomCount =  new int[3];
    
    public static void INIT() {
        TX_BookTarget = T1_RoomBooked;
        TX_CountTarget = T1_RoomCount;
        int numOfRoomsWithThisConfiguration = 10;
        for (int pType = 0; pType < 3; pType++){
            for (int b = 0; b < 2; b++) { // balcony or wc
                T1_RoomCount[pType][b] = 1;
                T1_RoomBooked[pType][b] = 1;
                T2_RoomCount[pType][b] = numOfRoomsWithThisConfiguration;
                T2_RoomBooked[pType][b] = 0;
                T3_RoomCount[pType][b] = numOfRoomsWithThisConfiguration;
                T3_RoomBooked[pType][b] = 0;
            }
            T4_RoomCount[pType] = numOfRoomsWithThisConfiguration;
            T4_RoomBooked[pType] = 0;
        }
    }
    private static int[][] TX_BookTarget = T1_RoomBooked; // TODO --------------------------------------------------------
    private static int[][] TX_CountTarget = T1_RoomCount; // TODO --------------------------------------------------------
    public static boolean IsT1Available() {
        int[][] targetBook = TX_BookTarget;
        int[][] targetCount = TX_CountTarget;
        boolean emptyFound = false;
        for (int pType = 0; pType < 3 && !emptyFound; pType++)
            for (int b = 0; b < 2 && !emptyFound; b++)
                if (targetBook[pType][b] < targetCount[pType][b])
                    emptyFound = true;
        TX_BookTarget = T1_RoomBooked;
        TX_CountTarget = T1_RoomCount;
        return emptyFound;
    }
    public static boolean IsT2Available() {
        TX_BookTarget = T2_RoomBooked;
        TX_CountTarget = T2_RoomCount;
        return IsT1Available();
    }
    public static boolean IsT3Available() {
        TX_BookTarget = T3_RoomBooked;
        TX_CountTarget = T3_RoomCount;
        return IsT1Available();
    }
    public static boolean IsT4Available() {
        boolean emptyFound = false;
        for (int pType = 0; pType < 3 && !emptyFound; pType++)
            if (T4_RoomBooked[pType] < T4_RoomCount[pType])
                emptyFound = true;
        return emptyFound;
    }
    // returns isSuccess
    public static boolean TryBookT1(int pType, boolean withBalcony) { // withbalcony <=> withWC
        int[][] targetBook = TX_BookTarget;
        int[][] targetCount = TX_CountTarget;
        boolean isLimit = targetBook[--pType][withBalcony ? 0 : 1] == targetCount[pType][withBalcony ? 0 : 1];
        if (!isLimit)
            targetBook[pType][withBalcony ? 0 : 1]++;
        TX_BookTarget = T1_RoomBooked;
        TX_CountTarget = T1_RoomCount;
        return !isLimit;
    }
    public static boolean TryBookT2(int pType, boolean withBalcony) {
        TX_BookTarget = T2_RoomBooked;
        TX_CountTarget = T2_RoomCount;
        return TryBookT1(pType,withBalcony);
    }
    public static boolean TryBookT3(int pType, boolean withWC) {
        TX_BookTarget = T3_RoomBooked;
        TX_CountTarget = T3_RoomCount;
        return TryBookT1(pType,withWC);
    }
    public static boolean TryBookT4(int pType) {
        boolean isLimit = T4_RoomBooked[--pType] == T4_RoomCount[pType];
        if (!isLimit)
            T4_RoomBooked[pType]++;
        return !isLimit;
    }
}
