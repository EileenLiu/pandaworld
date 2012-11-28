package student.remote;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author haro
 */
public final class SHA256 {
    private static final MessageDigest sha256;
    static {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException nsae) {
            System.err.println("No SHA-256!");
            System.exit(25);
        }
        sha256 = md; //dumb, but otherwise java complains.
    }
    public static byte []hash(byte[]...byteses) {
        synchronized (sha256) {
            for(byte[] bs : byteses) 
                sha256.update(bs);
            return sha256.digest();
        }
    }
}
