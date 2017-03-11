package com.idib.TE;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class task2 {

    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    private static ExecutorService pools = Executors.newFixedThreadPool(10);
    private static List<Future<Boolean>> results = new ArrayList<>();
    private static char[][] arr;

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        String texts = read("src/tests/test0");
        String text1s = read("src/tests/test00");
        arr = gen(texts);
        next(arr,new char[5][5], new boolean[5], 0);

        arr = gen(text1s);
        nextv(new char[5][5], new boolean[5], 0);
    }


    private static char[][] gen(String s) {
        char[][] r = new char[5][5];
        for (int i = 0; i < s.length() && i < 25; i++) {
            r[i / 5][i % 5] = s.charAt(i);
//            r[i % 5][i / 5] = s.charAt(i);
        }
        return r;
    }


    private static String toS(char[][] mat) {
        String s = "";
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                s += mat[i][j];
            }
        }
//        for (int j = 0; j < 5; j++) {
//            for (int i = 0; i < 5; i++) {
//                s += mat[i][j];
//            }
//        }
        return s;
    }

    private static void print(char[][] mat) {
        String s = "";
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                s += mat[i][j];
            }
            s += '\n';
        }
        System.out.println(s);
    }

    public static char[][] swapCol(char[][] mat, int a, int b) {
        for (int i = 0; i < 5; i++) {
            char t = mat[i][a];
            mat[i][a] = mat[i][b];
            mat[i][b] = t;
        }
        return mat;
    }

    private static void next(char[][] inar, char[][] mat, boolean[] use, int depth) throws FileNotFoundException {
        if (depth == 5) {
            Future<Boolean> f = pool.submit(new TesterBor(toS(mat)));

            try {
                Boolean rus = f.get();
//                System.out.println(toS(mat));
                if (rus) {
                    print(mat);
                    System.out.println("TEXT");
                    System.out.println();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            for (int i = 0; i < mat.length; i++) {
                if (!use[i]) {
                    use[i] = true;
                    clone(inar, mat, i, depth);
                    next(inar, mat, use, depth + 1);
                    use[i] = false;
                }
            }
        }
    }


    private static void nextv(char[][] mat, boolean[] use, int depth) throws FileNotFoundException {
        if (depth == 5) {
            next(mat, new char[5][5], new boolean[5], 0);
        } else {
            for (int i = 0; i < mat.length; i++) {
                if (!use[i]) {
                    use[i] = true;
                    cloneV(arr, mat, i, depth);
                    nextv(mat, use, depth + 1);
                    use[i] = false;
                }
            }
        }
    }

    private static void clone(char[][] a, char[][] b, int t, int depth) {
        for (int i = 0; i < a.length; i++) {
            b[i][depth] = a[i][t];
        }
    }

    private static void cloneV(char[][] a, char[][] b, int t, int depth) {
        for (int i = 0; i < a.length; i++) {
            b[depth][i] = a[t][i];
        }
    }

    private static void check(String path) throws FileNotFoundException {
        results.add(pools.submit(new Tester(pool, path)));
    }

    private static String read(String path) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(path));
        StringBuilder text = new StringBuilder();
        String str;
        while ((str = in.readLine()) != null) {
            text.append(str);
        }
        return text.toString();
    }
}
