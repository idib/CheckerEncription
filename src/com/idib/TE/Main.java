package com.idib.TE;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {

    private static ExecutorService pool = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        File f = new File("src/tests");
        File[] list = f.listFiles();
        for (File file : list) {
            check(file.getPath());
        }
    }


    private static void check(String path){
        Tester t = null;
        try {
            t = new Tester(path,pool);
            Thread th = new Thread(t);
            th.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
