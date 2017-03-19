package com.idib.TE;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by idib on 15.02.17.
 */
public class Tester implements Callable<Boolean> {
//    private static boolean flPrint = true;
        private static boolean flPrint = false;
    private TesterChars TC;
    private TesterBor TB;
    private String text;
    private String path;
    private ExecutorService pool;

    public Tester(ExecutorService pool, String texts) throws FileNotFoundException {
        path = "";
        this.pool = pool;


        text = texts;

        TC = new TesterChars(text);
        TB = new TesterBor(text);
        //TG = new TryGoogle();
    }

    public Tester(String path, ExecutorService pool) throws IOException {
        this.path = path;
        this.pool = pool;

        text = read(path);

        TC = new TesterChars(text);
        TB = new TesterBor(text);
        //TG = new TryGoogle();
    }

    public static String read(String path) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(path));
        StringBuilder text = new StringBuilder();
        String str;
        while ((str = in.readLine()) != null) {
            text.append(str);
        }
        return text.toString();
    }

    @Override
    public Boolean call() throws Exception {
        Future<Boolean> futureTC = pool.submit(TC);
        Future<Boolean> futureTB = pool.submit(TB);

        List<Boolean> f = new ArrayList<>();


        try {

            System.out.println(path + "#Result TC " + futureTC.get());
            f.add(futureTC.get());
            System.out.println(path + "#Result TB " + futureTB.get());
            f.add(futureTB.get());

            if (text.length() > 50 && f.get(0) && f.get(1))
                return true;
            else if (f.get(1))
                return true;
            else
                return false;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }
}
