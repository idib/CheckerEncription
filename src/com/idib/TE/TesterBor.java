package com.idib.TE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
    private final static Object syn = new Object();
    static Node root;
    BufferedReader in;
    int N;
    double eps = 0.5;

    public TesterBor(String str) {
        N = str.length();
        text = str;
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

//        String s = in.nextLine();
        TesterBor tb = new TesterBor("Исходя из результатов эксперимента, можно сделать заключение,\n" +
                "что объект имеет мягкую однородную структуру, свободно пропускает\n" +
                "свет и может изменять ряд своих параметров при воздействии на него\n" +
                "разности потенциалов в диапазоне от 5 до 33 000 В. Исследования также\n" +
                "показали, что объект необратимо изменяет свою молекулярную структуру\n" +
                "под воздействием температуры свыше 300 К. При механическом воздействии\n" +
                "на объект с силой до 1000 Н видимых изменений в структуре не наблюдается.");

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
                init();
        }
        int good = 0;
        int bad = 0;
        String[] splits = text.split("/[^A-Za-z0-9А-Яа-я]+/g");
        for (int i = 0; i < splits.length; i++) {
            if (test(splits[i]))
                good++;
            else
                bad++;
        }

//        System.out.println("good " + good);
//        System.out.println("bad" + bad);
//        System.out.println(good * 1. / (good + bad));

        if ((good + bad) * eps < good)
            System.out.println(true);
        else
            System.out.println(false);
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

    private boolean test(String r) {
        char c;
        Node cur = root;
        for (int i = 0; i < r.length(); i++) {
            c = Character.toLowerCase(text.charAt(i));
            if (cur.next.containsKey(c)) {
                cur = cur.next.get(c);
            } else
                break;
            if (i == r.length() - 1 && cur.terminated)
                return true;
        }
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
