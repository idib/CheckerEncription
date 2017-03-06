package com.idib.TE;

import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.Callable;

/**
 * Created by idib on 14.02.17.
 */
public class TesterChars implements Callable<Boolean> {
    private Bor root;
    private Bor nroot;
    private double[] eps = {
            1e-2,
            1e-2,
            1e-2
    };
    private long[] counts;
    private long[] ncounts;
    private BufferedReader in;
    private String testStr;
    private String FilePath = "src/dic/gramms/rus";


    public TesterChars(String testStr) {
        this.testStr = testStr.toLowerCase();
        counts = new long[3];
        ncounts = new long[3];
    }

    @Override
    public Boolean call() throws Exception {
        init();
        nroot = new Bor();
        char c1, c2 = ' ', c3 = ' ';
        String[] splits = testStr.split("[^A-Za-zА-Яа-я]+");
        for (String s : splits) {
            for (int i = 0; i < s.length(); i++) {
                c1 = c2;
                c2 = c3;
                c3 = s.charAt(i);
                nroot.addCounts("" + c3);
                ncounts[0]++;
                if (i > 0) {
                    nroot.addCounts("" + c2 + c3);
                    ncounts[1]++;
                }
                if (i > 1) {
                    nroot.addCounts("" + c1 + c2 + c3);
                    ncounts[2]++;
                }
            }
        }

        double dif[] = AvgDifferent(nroot.root, root.root);
        boolean fl = true;
//        System.out.println();
//        System.out.println();
//        System.out.println(Arrays.toString(dif));
        for (int i = 0; fl && i < eps.length; i++) {
            if (dif[i] > eps[i])
                fl = false;
        }
        return fl;
    }


    private double[] AvgDifferent(Bor.Node a, Bor.Node b) {
        Stack<Bor.Node> sA = new Stack<>();
        Stack<Bor.Node> sB = new Stack<>();
        Stack<Integer> slvl = new Stack<>();

        sA.add(a);
        sB.add(b);
        slvl.add(-1);
        int lvl;


        double[] dif = new double[3];
        long[] n = new long[3];

        while (!sA.empty()) {
            a = sA.pop();
            b = sB.pop();
            lvl = slvl.pop();

            if (lvl != -1) {
                dif[lvl] += (double) a.counts / ncounts[lvl] - (double) b.counts / counts[lvl];
                n[lvl]++;
            }

            for (Map.Entry<Character, Bor.Node> entA : a.next.entrySet()) {
                char c = entA.getKey();
                Bor.Node subA = entA.getValue();
                if (b.next.containsKey(c)) {
                    Bor.Node subB = b.next.get(c);

                    sA.add(subA);
                    sB.add(subB);
                    if (c == ' ')
                        slvl.add(lvl);
                    else
                        slvl.add(lvl + 1);
                }
            }
        }

        for (int i = 0; i < dif.length; i++) {
            dif[i] = dif[i] / n[i];
        }

        return dif;
    }


    private void init() throws FileNotFoundException {
        in = new BufferedReader(new FileReader(FilePath));
        root = new Bor();
        try {
            String str;
            counts[0] = Long.parseLong(in.readLine());
            counts[1] = Long.parseLong(in.readLine());
            counts[2] = Long.parseLong(in.readLine());
            while ((str = in.readLine()) != null) {
                String[] temp = str.split(":");
                String chars = temp[0];
                long count = Long.parseLong(temp[1]);
                root.put(chars, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try{
            File f = new File("src/tests");

            File[] list = f.listFiles();

            for (File file : list) {
                System.out.println(file.getPath() + "#" +  new TesterChars(Tester.read(file.getPath())).call());
            }
        }catch (Exception e){

        }
    }
}
