package com.idib.TE;

import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.Callable;

/**
 * Created by idib on 15.02.17.
 */
public class MatchesWord implements Callable<Integer> {
    private final static Object syn = new Object();
    private static Bor root;
    String text;
    int N;
    double eps = 0.8;

    public MatchesWord(String str) {
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
    public Integer call() throws Exception {
        synchronized (syn) {
            if (root == null)
                Bor.init(root = new Bor());
        }
        int good = 0;

        String[] splits = text.split("[^A-Za-zА-Яа-я]+");
        for (int i = 0; i < splits.length; i++) {
            if (root.test(splits[i]))
                good++;
        }

        return good;
    }

}