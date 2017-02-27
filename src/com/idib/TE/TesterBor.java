package com.idib.TE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;

/**
 * Created by idib on 15.02.17.
 */
public class TesterBor implements Callable<Boolean> {
    String text;
    int index;
    private final static Object syn = new Object();
    static Node root;
    BufferedReader in;
    int N;
    double eps = 0.3;

    public TesterBor(String str) {
        N = str.length();
        text = str;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        for (int j = 0; j < s.length(); j++) {
            System.out.println(s.charAt(j) + " " + Character.isWhitespace(s.charAt(j)));
        }
    }

    @Override
    public Boolean call() throws Exception {
        synchronized (syn) {
            if (root == null)
                init();
        }
        int good = 0;
        int bad = 0;
        for (index = 0; index < N; index++) {
            if (Character.isAlphabetic(text.charAt(index))) {
                if (check())
                    good++;
                else
                {
                    while (index < N  && Character.isLetterOrDigit(text.charAt(index))) index++;
                    bad++;
                }
            }
        }
//        System.out.println(good * 1. / (good + bad));
        if ((good + bad) * eps < good)
            return true;
        return false;
    }

    private void init() {
        root = new Node();
        File dirDic = new File("src/dic");
        File[] DicList = dirDic.listFiles();
        for (File file : DicList) {
            if (file.isFile()) {
                try {
                    addFile(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addFile(File file) throws IOException {
        in = new BufferedReader(new FileReader(file));
        String str;
        while ((str = in.readLine()) != null) {
            put(str.trim().toLowerCase());
        }
    }

    private void put(String input) {
        int n = input.length();
        char c;
        Node cur = root;
        for (int i = 0; i < n; i++) {
            c = input.charAt(i);
            if (!cur.next.containsKey(c)) {
                Node t = new Node();
                cur.next.put(c, t);
            }
            cur = cur.next.get(c);
            if (i == n - 1)
                cur.terminated = true;
        }
    }

    private boolean check() {
        char c;
        Node cur = root;
        for (; index < N; index++) {
            try {
                c = Character.toLowerCase(text.charAt(index));
                if (specialSymbol(c) && cur.terminated) {
//                    System.out.println();
                    return true;
                }
//                System.out.print(c);
                if (cur.next.containsKey(c)) {
                    cur = cur.next.get(c);
                } else
                    break;
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println(index);
                System.out.println(N);
            }
        }
        if (cur.terminated) {
//                    System.out.println();
            return true;
        }
//        System.out.println();
        return false;
    }

    private boolean specialSymbol(char c) {
        if (!Character.isLetterOrDigit(c) && c != '\'')
            return true;
        return false;
    }


    class Node {
        boolean terminated = false;
        Map<Character, Node> next = new HashMap<>();
    }
}
