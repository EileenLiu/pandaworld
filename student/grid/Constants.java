/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.grid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

/**
 *
 * @author haro
 */
public final class Constants {
    private Constants(){}
    public static volatile int BASE_DAMAGE              = 100000,  //wrong!
                               MOVE_COST                = 3,
                               ATTACK_COST              = 5,
                               GROW_COST                = 1,
                               ABILITY_COST             = 25,
                               ENERGY_PER_PLANT         = 100000, //also wrong!
                               FOOD_PER_SIZE            = 200,
                               PLANTS_CREATED_PER_TURN  = 2,
                               INITIAL_ENERGY           = 250,
                               BUD_COST                 = 9;
    public static volatile double DAMAGE_INC = 0.2,
                                  PLANT_GROW_PROB = .005;
    
    private static final Pattern constant = Pattern.compile("\\{[^\\}]*\\}");
    public static void loadFromFile(File f) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        String inlin = ""; int line = 0;
        while((inlin = in.readLine()) != null) {
            String lin = constant.matcher(inlin).replaceAll("");
            String spl[] = lin.split(" ");
            if(spl.length != 2) 
                throw new IOException("Invalid line format on line "+line);
            String var = spl[0];
            String sval = spl[1];
            Number val;
            try {
                val = new Integer(sval);
            } catch (NumberFormatException nfe) {
                try {
                    val = new Double(sval);
                } catch (NumberFormatException nfe2) {
                    throw new IOException("Invalid number on line "+line);
                }
            }
            try {
                Constants.class.getDeclaredField(var).set(null, val);
            } catch (NoSuchFieldException nsfe) {
                //throw new IOException("Not a recognized constant: " +var);
                System.err.println("Not a recognized constant: "+var);
            } catch (ReflectiveOperationException re) {
                throw new IOException("Couldn't set value of field: "+var);
            }
        }
    }
}
