package com.idib.TE;

import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {

    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    private static ExecutorService pools = Executors.newFixedThreadPool(10);
    private static List<Future<Boolean>> results = new ArrayList<>();

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        try {
            DetectorFactory.loadProfile("src/dic/gramms");
        } catch (LangDetectException e) {
            e.printStackTrace();
        }

        File f = new File("src/tests");

        File[] list = f.listFiles();
        System.out.println("=================star=================");
        for (File file : list) {
            check(file.getPath());

        }

        for (Future<Boolean> result : results) {
            result.get();
        }

        for (Future<Boolean> result : results) {

            System.out.println();
        }

        System.out.println("=================results=================");


        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).get())
            {
                System.out.println("" + list[i] + "#Result: language");
            }else{
                System.out.println("" + list[i] + "#Result: encryption");
            }
        }

    }


    private static void check(String path){
        try {

            System.out.println(path+"#start");
            results.add(pools.submit(new Tester(path,pool)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
