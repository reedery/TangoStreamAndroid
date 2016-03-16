package edu.bc.reedelunt;

import java.util.Iterator;
import java.util.Random;

public class NumService {

    private static Random randy = new Random();

    private static Iterator<Integer> xyzIter = randy.ints(-3,3).iterator();
    private static Iterator<Integer> rotationIter = randy.ints(-10,10).iterator();


    public static String getNums(){
        return (xyzIter.next().toString() + "\t" + xyzIter.next().toString() + "\t" + xyzIter.next().toString() + "\t" + rotationIter.next().toString() + "\t" + rotationIter.next().toString() + "\t" + rotationIter.next().toString());
    }
}