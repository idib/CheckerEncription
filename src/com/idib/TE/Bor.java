package com.idib.TE;

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

    class Node {
        boolean terminated = false;
        long counts = 0;
        Map<Character, Node> next = new HashMap<>();
    }
}
