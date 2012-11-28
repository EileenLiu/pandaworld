package student.remote;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 * @author haro
 */
public final class PRNG {

    public static SecureRandom getSHA1PRNG() {
        try {
            return SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("Your java sucks.");
            System.exit(103);
        }
        return null; //no, we won't!
    }
}
