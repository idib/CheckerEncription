package com.idib.TE;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by idib on 14.02.17.
 */
public class TesterChars implements Callable<Boolean> {
    private final static Object syn = new Object();

    String testStr;
    String FilePath;
    BufferedReader in;
    HashMap<Character,node> root;
    double maxK = 0;
    double eps = 2e-2;
    long uno;
    long duo;
    long tri;


    public TesterChars(String testStr) {
        this.testStr = testStr.toLowerCase();
        synchronized (syn) {
            if (root != null)
                try {
                    init();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public Boolean call() throws Exception {
        int n = testStr.length();

        long countRus = 0, countEng = 0;

        HashMap<Character, Integer> cRus = new HashMap<>();
        HashMap<Character, Integer> cEng = new HashMap<>();

        for (int i = 0; i < n; i++) {
            char c = testStr.charAt(i);
            if (c >= 'а' && c <= 'я') {
                cRus.put(c, cRus.getOrDefault(c, 0) + 1);
                countRus++;
            }
            if (c >= 'a' && c <= 'z') {
                cEng.put(c, cEng.getOrDefault(c, 0) + 1);
                countEng++;
            }
        }


        double avg = 0;
        double avgK = 0;

        if (countRus > countEng) {
            for (Map.Entry<Character, Integer> entry : cRus.entrySet()) {
                avg += Math.abs(CharsRus.get(entry.getKey()) - (double) entry.getValue() / n);
            }
            avgK = (double) avg / cRus.size();
        } else {
            for (Map.Entry<Character, Integer> entry : cEng.entrySet()) {
                avg += Math.abs(CharsEng.get(entry.getKey()) - (double) entry.getValue() / n);
            }
            avgK = (double) avg / cEng.size();
        }

        if (avgK > eps)
            return false;
        return true;
    }


    private void init() throws FileNotFoundException {
        in = new BufferedReader(new FileReader(FilePath));
        root = new node();
        try {
            String str = in.readLine();

            uno = Long.parseLong(in.readLine());
            duo = Long.parseLong(in.readLine());
            tri = Long.parseLong(in.readLine());
            long n = uno + duo + tri;
            for (int i = 0; i < n; i++) {
                str = in.readLine();
                String[] temp = str.split(":");
                String chars = temp[0];
                long count = Long.parseLong(temp[1]);
                addSequence(chars, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void addSequence(String str, long count) {
        int n = str.length();
        char a, b, c;
        a = str.charAt(0);
        if (n == 1) {
            if (!root.containsKey(a))
                root.put(a, new node());
            root.get(a).count = count;
        }

        if (n == 2) {
            b = str.charAt(1);
            if (!root.containsKey(a))
                root.put(a, new node());
            if (!root.get(a).next.containsKey(b))
                root.get(a).next.put(b,new sub());
            root.get(a).next.get(b).count = count;
        }

        if (n == 3) {
            b = str.charAt(1);
            c = str.charAt(2);
            if (!root.next.containsKey(c))
                root.next.put(c, new sub());
            if (!root.next.get(c).last.containsKey())
                root.next.put(c, new sub());
            root.next.get(c).count = count;
        }
    }

    class node {
        public node() {
            next = new HashMap<>();
        }

        HashMap<Character, sub> next;
        long count;
    }


    class sub {
        public sub() {
            last = new HashMap<>();
        }

        node link;
        long count;
        HashMap<Character, Long> last;
    }
}
