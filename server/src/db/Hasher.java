package db;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Hasher {
    public static String getSalt() {
        byte[] array = new byte[8];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }
    public static byte[] getHash(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            String pepper="%g*&/s3M";
            byte[] hash =  md.digest((pepper + password + salt).getBytes(StandardCharsets.UTF_8));
            return hash;
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
            return null;
        }
    }
    public static boolean checkPassword(String password, String salt, byte[] dbHashedPassword){
        byte[] hashedPassword = getHash(password, salt);
        if (hashedPassword.length != dbHashedPassword.length) {
            return false;
        }
        for (int i = 0; i < hashedPassword.length; i++) {
            if (hashedPassword[i] != dbHashedPassword[i]) {
                return false;
            }
        }
        return true;
    }
}
