package org.example.tris;

import java.util.ArrayList;
import java.util.Random;

public class MinMaxAlgorithm {
    public MinMaxAlgorithm(){}

    public int genereteMove(ArrayList<Integer> mosseLibere){
        int size=mosseLibere.size();
        if(size>0) {
            Random rand = new Random();
            int intRandom = rand.nextInt(size);
            return mosseLibere.get(intRandom);
        }else{
            return -1;
        }
    }
}
