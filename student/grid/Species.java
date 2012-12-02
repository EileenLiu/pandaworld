/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.grid;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
    private HashSet<Integer> lineage = new HashSet();
    private static HashMap<Integer, Species> instances = new HashMap();
    public static Species getInstance(int[] att, Program p, LinkedList<Integer> l)
    {
        Species s = instances.get(hash(att,p));
        System.out.println("s= "+s);
        if(s == null)
        {
            s = new Species(att, p, l);
            instances.put(s.hashCode(), s);
        }
        return s;
    }
    public static Species get(int speciesID)
    {
        return instances.get(speciesID);
    }
    private Species(int[] att, Program p, LinkedList<Integer> l) {
        this.attributes = att;
        program = p;
        //Guarantees same species (of same appearance) have the same color
        //We square it first b/c of implementation of .hashCode() [not good for bit fields]
        long h = Math.abs((long) this.hashCode()), r = 7, k = 1;
        while (h > 1) {
            if ((h & 1L) == 1) {
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
        if(l!=null){
        for (Integer i : l) {
            lineage.add(i);
        }
        }
    }

    @Override
    public int[] getSpeciesAttributes() {
        return attributes;
    }

    @Override
    public HashSet<Integer> getLineage() {
        return lineage;
    }

    @Override
    public Program getSpeciesProgram() {
        return program;
    }

    public Color getColor() {
        return color;
    }
    private static int hash(int[] att, Program prog){
        int res = 1;
        res += att[0];
        res <<= 8;            //memory size
        res += att[1];
        res <<= 8;            //offense
        res += att[2];
        res <<= 8;            //defense
        res = res + (prog.hashCode() & 0xff); //program
        return res;
    }
    @Override
    public int hashCode() {
        return hash(attributes, program);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public String toString() {
        return "species " + hashCode();
    }
}
