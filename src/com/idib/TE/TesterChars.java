package com.idib.TE;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 * Created by idib on 14.02.17.
 */
public class TesterChars implements Callable<Boolean> {
    private final static Object syn = new Object();
    private HashMap<Character, node> root;
    private double eps = 2e-2;
    private long uno;
    private long duo;
    private long tri;
    private long n;
    private BufferedReader in;
    private String testStr;
    private String FilePath = "src/dic/gramms/rus";


    public TesterChars(String testStr) throws FileNotFoundException {
        this.testStr = testStr.toLowerCase();
        init();
    }

    @Override
    public Boolean call() throws Exception {
        try {
            int n = testStr.length();
            char last, cur, next;
            node nd;
            for (int i = 0; i < n; i++) {
                if (i > 1)
                    last = testStr.charAt(i - 1);
                else
                    last = ' ';
                if (i < n - 1)
                    next = testStr.charAt(i + 1);
                else
                    next = ' ';
                cur = testStr.charAt(i);
                nd = root.get(cur);
                nd.cur++;
                nd.next.get(next).cur++;
                nd.next.get(next).curlast.put(last, nd.next.get(last).curlast.getOrDefault(last, 0L));
            }

            return false;
        }
        catch (Exception e)
        {
            return false;
        }
    }


    private void init() throws FileNotFoundException {
        in = new BufferedReader(new FileReader(FilePath));
        root = new HashMap<>();
        try {
            String str;
            uno = Long.parseLong(in.readLine());
            duo = Long.parseLong(in.readLine());
            tri = Long.parseLong(in.readLine());
            n = uno + duo + tri;
            while((str = in.readLine()) != null){
                try {
                    String[] temp = str.split(":");
                    String chars = temp[0];
                    long count = Long.parseLong(temp[1]);
                    addSequence(root, chars, count);
                }catch (Exception e){
                    System.err.println(str);
                }
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
        long count = 0;
        long cur = 0;

        public node() {
            next = new HashMap<>();
        }
    }


    private class sub {
        long count = 0;
        long cur = 0;
        HashMap<Character, Long> last;
        HashMap<Object, Long> curlast;

        public sub() {
            last = new HashMap<>();
            curlast = new HashMap<>();
        }
    }
}
