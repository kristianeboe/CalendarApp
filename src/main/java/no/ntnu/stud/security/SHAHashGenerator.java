package no.ntnu.stud.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

/**
 * Provides the ability to either generate a new password with salt,
 * or just hash an existing password with an existing salt.
 *
 * @author Adrian Hundseth
 */
public class SHAHashGenerator {

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    /**
     * Uses the SHA algorithm to generate a 512-bit hash from the supplied password and salt.
     *
     * @param password
     * @param salt
     * @return A <code>String</code> containing 512-bit SHA hash
     */
    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    /**
     * Generates a random 16-byte <code>String</code> used to salt the hash with the help
     * of Java's {@link java.security.SecureRandom} class.
     *
     * @see java.security.SecureRandom
     * @return Random 16-byte <code>String</code>
     */
    public static byte[] getSalt() {
        byte[] salt = new byte[16];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(salt);
        return salt;
    }

    public static boolean isValid(char[] password, byte[] salt, byte[] hash) {
        byte[] pwdHash = hash(password, salt);
        Arrays.fill(password, Character.MIN_VALUE);

        if (pwdHash.length != hash.length) return false;

        for (int i = 0; i < pwdHash.length; i++) {
            if (pwdHash[i] != hash[i]) return false;
        }

        return true;
    }

    public static String generateRandomPassword(int length) {
        StringBuilder sb = new StringBuilder(length);
        SecureRandom sr = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int c = sr.nextInt(62);
            if (c <= 9 ) {
                sb.append(String.valueOf(c));
            } else if (c < 36) {
                sb.append((char) ('a' + c - 10));
            } else {
                sb.append((char) ('A' + c - 36));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(isValid("12345".toCharArray(), "[B@6a5fc7f7".getBytes().toString().getBytes(), hash("12345".toCharArray(), "[B@6a5fc7f7".getBytes())));
    }
}