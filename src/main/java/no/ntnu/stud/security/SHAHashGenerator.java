package no.ntnu.stud.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Provides the ability to either generate a new password with salt,
 * or just hash an existing password with an existing salt.
 *
 * @author Adrian Hundseth
 */
public class SHAHashGenerator {

    /**
     * Uses <code>getSHA512SecureHash</code> and <code>getSalt</code> to
     * generate new hashed password and a new salt.
     *
     * @param password
     * @return A <code>String[]</code> with the hashed password and salt.
     */
    public static String[] getSecurePassword(String password) {
        String salt = getSalt();
        String securePassword = getSHA512SecureHash(password, salt);
        String[] returnStrings = {securePassword, salt};
        return returnStrings;
    }

    /**
     * Uses the SHA algorithm to generate a 512-bit hash from the supplied password and salt.
     *
     * @param password
     * @param salt
     * @return A <code>String</code> containing 512-bit SHA hash
     */
    public static String getSHA512SecureHash(String password, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    /**
     * Generates a random 16-byte <code>String</code> used to salt the hash with the help
     * of Java's {@link java.security.SecureRandom} class.
     *
     * @see java.security.SecureRandom
     * @return Random 16-byte <code>String</code>
     */
    private static String getSalt() {
        byte[] salt = new byte[16];
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new String(salt);
    }
}