package com.idib.TE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by idib on 15.02.17.
 */
public class Bor {
    static Scanner ins;
    Node root;
    BufferedReader in;

    public Bor(String pathToFile) throws IOException {
        root = new Node();
        in = new BufferedReader(new FileReader(pathToFile));
        String str;
        while ((str = in.readLine()) != null && str.length() != 0) {
            put(str.trim());
        }
    }

    public static void main(String[] args) throws IOException {
        Bor r = new Bor("src/dic/2of12.txt");
        ins = new Scanner(System.in);
        System.out.println(r.check(ins.next()));
    }

    public void put(String input) {
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

    private boolean check(String next) {
        int n = next.length();
        char c;
        Node cur = root;
        for (int i = 0; i < n; i++) {
            c = next.charAt(i);
            if (cur.next.containsKey(c)) {
                cur = cur.next.get(c);
            } else
                break;
            if (i == n - 1 && cur.terminated)
                return true;
        }

        return false;
    }

    class Node {
        boolean terminated = false;
        Map<Character, Node> next = new HashMap<>();
    }
}
