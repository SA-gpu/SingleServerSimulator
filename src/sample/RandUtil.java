package sample;

import java.util.Random;

public class RandUtil {

    private static Random exponentialRandom = new Random();
//    static Random ur= new Random();

    static double exponential(double lambda){
        return  (-lambda)*Math.log(1- exponentialRandom.nextDouble());
    }

//    static double uniform(int min, int max){
//        int x= ur.nextInt((max - min) + 1) + min;
//        return x/60;
//    }
}
