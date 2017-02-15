package com.idib.TE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by idib on 15.02.17.
 */
public class Tester implements Runnable {
    private TesterChars TC;
    private TesterBor TB;
    private TryGoogle TG;
    private String text;
    private String path;
    private ExecutorService pool;

    private static String read (String path) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(path));
        StringBuilder text = new StringBuilder();
        String str;
        while ((str = in.readLine()) != null) {
            text.append(str);
        }
        return text.toString();
    }

    public Tester(String path, ExecutorService pool) throws IOException {
        this.path = path;
        this.pool = pool;
        text = read(path);
        TC = new TesterChars(text);
        TB = new TesterBor(text);
        //TG = new TryGoogle();
    }


    @Override
    public void run() {
        Future<Boolean> futureTC = pool.submit(TC);
        Future<Boolean> futureTB = pool.submit(TB);

        List<Boolean> f = new ArrayList<>();


        try {
            System.out.println(path + "#результ TC " + futureTC.get());
            f.add(futureTC.get());


            System.out.println(path +"#результ TB " + futureTB.get());
            f.add(futureTB.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

}
