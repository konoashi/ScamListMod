package fr.konoashi.ScamerList.utils;

public class RandomUsage {

    public static long RandomLong(){
        java.util.Random random = new java.util.Random();
        long number = random.nextLong();
        return +number;
    }
}
