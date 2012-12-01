/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.grid;

import java.awt.Color;
import java.util.ArrayList;
import student.parse.Program;
import student.remote.server.RemoteSpecies;

/**
 *
 * @author Eileen Liu <el544@cornell.edu>
 */
public final class Species implements RemoteSpecies {

    private int[] attributes;
    private Program program;
    private Color color; //each unique species corresponds to a unique color

    public Species(int[] att, Program p) {
        this.attributes = att;
        program = p;
        //Guarantees same species (of same appearance) have the same color
        //We square it first b/c of implementation of .hashCode() [not good for bit fields]
        long h = Math.abs((long) this.hashCode()), r = 7, k = 1;
        while (h > 1) {
            if((h&1L) == 1) {
                h--;
                k *= r;
            }
            h >>= 1;
            r *= r;
            r &= 0xfffffff;
        }
        r *= k;
        int red = (int) (r & 0xff);
        int grn = (int) ((r >>= 8) & 0xff);
        int blu = (int) ((r >> 8) & 0xff);
        color = new Color(red, grn, blu);
    }

    @Override
    public int[] getSpeciesAttributes() {
        return attributes;
    }

    @Override
    public ArrayList<RemoteSpecies> getLineage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Program getSpeciesProgram() {
        return program;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public int hashCode() {
        int res = 1;
        res += attributes[0];
        res <<= 8;            //memory size
        res += attributes[1];
        res <<= 8;            //offense
        res += attributes[2];
        res <<= 8;            //defense
        res = res + (program.hashCode() & 0xff); //program
        return res;
    }

    @Override
    public String toString() {
        return "species " + hashCode();
    }
}
