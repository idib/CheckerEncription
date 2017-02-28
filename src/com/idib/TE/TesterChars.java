package com.idib.TE;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;


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
    private static Detector detector;
    private HashMap<Character, node> root;
    private double eps = 2e-2;
    private long uno;
    private long duo;
    private long tri;
    private BufferedReader in;
    private String testStr;
    private String FilePath;


    public TesterChars(String testStr) throws LangDetectException {
        this.testStr = testStr.toLowerCase();
        detector = DetectorFactory.create();
        detector.append(testStr);
    }

    @Override
    public Boolean call() throws Exception {
        int n = testStr.length();

        String lang = detector.detect();
        System.out.println(lang);
        System.out.println(detector.getProbabilities());
        return false;
    }


    private void init() throws FileNotFoundException {
        in = new BufferedReader(new FileReader(FilePath));
        root = new HashMap<>();
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
                addSequence(root, chars, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void addSequence(HashMap<Character, node> cur, String str, long count) {
        int n = str.length();
        char a, b, c;
        a = str.charAt(0);
        if (n == 1) {
            if (!cur.containsKey(a))
                cur.put(a, new node());
            cur.get(a).count = count;
        }

        if (n == 2) {
            b = str.charAt(1);
            if (!cur.containsKey(a))
                cur.put(a, new node());
            if (!cur.get(a).next.containsKey(b))
                cur.get(a).next.put(b, new sub());
            cur.get(a).next.get(b).count = count;
        }

        if (n == 3) {
            b = str.charAt(1);
            c = str.charAt(2);
            if (!cur.containsKey(a))
                cur.put(a, new node());
            if (!cur.get(a).next.containsKey(b))
                cur.get(a).next.put(b, new sub());
            cur.get(a).next.get(b).last.put(c, count);
        }
    }

    private class node {
        HashMap<Character, sub> next;
        long count;
        long cur;

        public node() {
            next = new HashMap<>();
        }
    }


    private class sub {
        node link;
        long count;
        HashMap<Character, Long> last;

        public sub() {
            last = new HashMap<>();
        }
    }
}
