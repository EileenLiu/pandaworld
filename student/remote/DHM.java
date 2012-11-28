package student.remote;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 *
 * @author haro
 */
public class DHM {
    private static final int LENGTH = 256;
    private static final SecureRandom rand = new SecureRandom();
    private static final BigInteger base = new BigInteger("115456866657752040987804240743603470478753404693929652771367729188022067165263"),
                                    mod  = BigInteger.valueOf(4423);
    
    private BigInteger a, ga, gab = null;
    
    public DHM () {
        byte bytes[] = new byte[LENGTH];
        rand.nextBytes(bytes);
        a = new BigInteger(bytes);
        ga = base.modPow(a, mod);
    }
    
    public BigInteger ga() {
        return ga;
    }
    
    public BigInteger gab() {
        return gab;
    }
    
    public void gb(BigInteger gb) {
        if(gab == null) {
            gab = gb.modPow(a, mod);
        }
    }
}
