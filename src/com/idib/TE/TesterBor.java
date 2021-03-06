package com.idib.TE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by idib on 15.02.17.
 */
public class TesterBor implements Callable<Boolean> {
    String text;
    private static Bor root;
    private final static Object syn = new Object();
    int N;
    double eps = 0.8;

    public TesterBor(String str) {
        N = str.length();
        text = str;
    }

    public static void main(String[] args) {
        System.out.println(Charset.defaultCharset());
        Scanner in = new Scanner(System.in);

//        String s = in.nextLine();
        TesterBor tb = new TesterBor("ВОСПОЛЬЗУЙТЕСЬ_ПОДСКАЗКОЙ");

//        String[] split = s.split("[^A-Za-zА-Яа-я]+");
//
//        for (String s1 : split) {
//            System.out.println(s1);
//        }

        try {
            Boolean t = tb.call();

            System.out.println(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean call() throws Exception {
        synchronized (syn) {
            if (root == null)
                Bor.init(root = new Bor());
        }
        int good = 0;
        int bad = 0;
        String[] splits = text.split("[^A-Za-zА-Яа-я]+");
        for (int i = 0; i < splits.length; i++) {
            if (root.test(splits[i]))
                good++;
            else
                bad++;
        }
//
//        System.out.println("good " + good);
//        System.out.println("bad " + bad);
//        System.out.println(good * 1. / (good + bad));
//
//        if ((good + bad) * eps < good)
//            System.out.println(true);
//        else
//            System.out.println(false);
//        if ((good + bad) * eps >= good)
//            System.out.println(good*1. / (good+bad));

        if ((good + bad) * eps < good)
            return true;
        return false;
    }

}
