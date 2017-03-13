package com.idib.TE;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by idib on 06.03.17.
 */
public class temp {
    public static void main(String[] args) throws IOException {
//        System.out.println(task3.find(new int[]{90, 26, 26, 63, 68, 24, 63, 90, 63, 68, 32, 11, 30, 67, 24}, new HashMap<>()));

        Set<HashMap<Integer,Integer>> mem  = new HashSet<>();
        mem.add(new HashMap<Integer,Integer>(){{put(1,2);put(2,4);}});
        mem.add(new HashMap<Integer,Integer>(){{put(3,2);put(2,4);}});
        mem.add(new HashMap<Integer,Integer>(){{put(2,4);put(1,2);}});

        System.out.println(mem);
    }
}
