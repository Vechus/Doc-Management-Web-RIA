package it.polimi.tiw.util;

public class Hashing {
    public static boolean isValidMD5(String s) {
        return s.matches("^[a-fA-F0-9]{32}$");
    }
}
