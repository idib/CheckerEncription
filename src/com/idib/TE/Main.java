package com.idib.TE;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, ExecutionException, InterruptedException {
        Scanner in = new Scanner(new File("src/test1"));
//        Scanner in = new Scanner(new File("src/test2"));

        ExecutorService pool = Executors.newFixedThreadPool(4);

        List<Future> futures = new ArrayList<>();

        TryGoogle tg = new TryGoogle();

        String t="";

        while (in.hasNextLine()) {
            String text = in.nextLine();
            t+=text;
//            futures.add(pool.submit(new TesterChars(text)));
//            //tg(text);
        }

        futures.add(pool.submit(new TesterChars(t)));

        Future <Integer> tgres = (pool.submit(tg));

        List<Boolean> f = new ArrayList<>();

        for (Future future : futures) {
            System.out.println("результ " + future.get());
            f.add((Boolean) future.get());
        }

        int good = 0;

        for (Boolean b : f) {
            good += b ? 1 : 0;
        }

        if ((good + tgres.get()) > f.size() * 0.8)
        {
            System.out.println("Язык");
        }else {
            System.out.println("шифр");
        }



         in = new Scanner(new File("src/test2"));

        pool = Executors.newFixedThreadPool(4);

        futures = new ArrayList<>();

        tg = new TryGoogle();

        t="";

        while (in.hasNextLine()) {
            String text = in.nextLine();
            t+=text;
//            futures.add(pool.submit(new TesterChars(text)));
//            //tg(text);
        }

        futures.add(pool.submit(new TesterChars(t)));

         tgres = (pool.submit(tg));

         f = new ArrayList<>();

        for (Future future : futures) {
            System.out.println("результ " + future.get());
            f.add((Boolean) future.get());
        }

         good = 0;

        for (Boolean b : f) {
            good += b ? 1 : 0;
        }

        if ((good + tgres.get()) > f.size() * 0.8)
        {
            System.out.println("Язык");
        }else {
            System.out.println("шифр");
        }

    }
}
