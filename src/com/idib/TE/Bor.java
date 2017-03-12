package com.idib.TE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by idib on 05.03.17.
 */
public class Bor {
    Node root;

    public Bor() {
        root = new Node();
    }

    void put(String input) {
        put(input, 0);
    }

    void put(String input, long count) {
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
            if (i == n - 1) {
                cur.counts = count;
                cur.terminated = true;
            }
        }
    }

    boolean test(String input) {
        Node cur = get(input);
        if (cur != null && cur.terminated)
            return true;
        return false;
    }

    private Node get(String input) {
        char c;
        Node cur = root;
        for (int i = 0; i < input.length(); i++) {
            c = Character.toLowerCase(input.charAt(i));
            if (cur.next.containsKey(c)) {
                cur = cur.next.get(c);
            } else
                return null;
        }
        return cur;
    }

    void addCounts(String input) {
        Node cur = get(input);
        if (cur == null) {
            put(input, 0);
            cur = get(input);
        }
        cur.counts++;
    }


    public static void init(Bor root) {
        File dirDic = new File("src/dic");
        File[] DicList = dirDic.listFiles();
        for (File file : DicList) {
            if (file.isFile()) {
                try {
                    addFile(root,file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void addFile(Bor root ,File file) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        String str;
        while ((str = in.readLine()) != null) {
            root.put(str.trim().toLowerCase());
        }
    }

    class Node {
        boolean terminated = false;
        long counts = 0;
        Map<Character, Node> next = new HashMap<>();
    }
}
