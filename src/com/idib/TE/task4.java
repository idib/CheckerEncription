package com.idib.TE;

import java.io.IOException;
import java.util.*;

/**
 * Created by idib on 18.03.17.
 */
public class task4 {
    static List<HashMap<Integer, Integer>> frec = new ArrayList<>();
    static List<Integer> len = new ArrayList<>();
    static List<Double> Ic = new ArrayList<>();
    static List<List<Integer>> shift = new ArrayList<>();
    static String input;
    static int keyLen;
    static Scanner in = new Scanner(System.in);
    static HashMap<Character, Integer> Alp = new HashMap<Character, Integer>() {{
        put('А', 0);
        put('Б', 1);
        put('В', 2);
        put('Г', 3);
        put('Д', 4);
        put('Е', 5);
        put('Ж', 6);
        put('З', 7);
        put('И', 8);
        put('Й', 9);
        put('К', 10);
        put('Л', 11);
        put('М', 12);
        put('Н', 13);
        put('О', 14);
        put('П', 15);
        put('Р', 16);
        put('С', 17);
        put('Т', 18);
        put('У', 19);
        put('Ф', 20);
        put('Х', 21);
        put('Ц', 22);
        put('Ч', 23);
        put('Ш', 24);
        put('Щ', 25);
        put('Ъ', 26);
        put('Ы', 27);
        put('Ь', 28);
        put('Э', 29);
        put('Ю', 30);
        put('Я', 31);
        put('_', 32);
    }};
    static HashMap<Integer, Character> Alps = new HashMap<Integer, Character>() {{
        put(0, 'А');
        put(1, 'Б');
        put(2, 'В');
        put(3, 'Г');
        put(4, 'Д');
        put(5, 'Е');
        put(6, 'Ж');
        put(7, 'З');
        put(8, 'И');
        put(9, 'Й');
        put(10, 'К');
        put(11, 'Л');
        put(12, 'М');
        put(13, 'Н');
        put(14, 'О');
        put(15, 'П');
        put(16, 'Р');
        put(17, 'С');
        put(18, 'Т');
        put(19, 'У');
        put(20, 'Ф');
        put(21, 'Х');
        put(22, 'Ц');
        put(23, 'Ч');
        put(24, 'Ш');
        put(25, 'Щ');
        put(26, 'Ъ');
        put(27, 'Ы');
        put(28, 'Ь');
        put(29, 'Э');
        put(30, 'Ю');
        put(31, 'Я');
        put(32, '_');
    }};
    static int sizeAlp = Alp.size();

    public static void main(String[] args) throws IOException {
        input = Tester.read("src/tests/task4");
//        keyLen = in.nextInt();
        keyLen = 4;

        for (int i = 0; i < keyLen; i++) {
            frec.add(new HashMap<>());
            len.add(0);
            shift.add(new ArrayList<>());
        }

        for (int i = 0; i < input.length(); i++) {
            frec.get(i % keyLen).put(Alp.get(input.charAt(i)), frec.get(i % keyLen).getOrDefault(Alp.get(input.charAt(i)), 0) + 1);
            len.set(i % keyLen, len.get(i % keyLen) + 1);
        }

        for (int i = 0; i < keyLen; i++) {
            int sum = 0;
            for (Map.Entry<Integer, Integer> ent : frec.get(i).entrySet()) {
                sum += ent.getValue() * (ent.getValue() - 1);
            }
            Ic.add(1. * sum / (len.get(i) * (len.get(i) - 1)));
        }

        for (int i = 1; i < keyLen; i++) {
            for (int j = 0; j < sizeAlp; j++) {
                double r = Mi(i, j);
                if (0.05 < r && r < 0.07) {
                    shift.get(i).add(j);
                }
            }
        }

        for (int i = 0; i < sizeAlp; i++) {
            shift.get(0).add(i);
        }

        System.out.println(shift);

        next(new int[keyLen], 0);
    }

    static void next(int[] mat, int depth) {
        if (depth == keyLen) {
            print(mat);
            System.out.println();
        } else {
            for (int i = 0; i < shift.get(depth).size(); i++) {
                if (depth == 0) {
                    mat[depth] = (char) (int) (shift.get(depth).get(i));
                } else {
                    mat[depth] = (char) (int) (mat[0] + shift.get(depth).get(i));
                }
                next(mat, depth + 1);
            }
        }
    }

    static void print(int[] mat) {
        for (int i = 0; i < keyLen; i++)
            System.out.print(Alps.get(mat[i]%sizeAlp));
        System.out.println();
        for (int i = 0; i < input.length(); i++) {
            System.out.print(Alps.get((input.charAt(i) - mat[i % keyLen] + sizeAlp) % sizeAlp));
        }
    }

    static double Mi(int j, int shift) {
        int sum = 0;
        for (Map.Entry<Integer, Integer> ent : frec.get(j).entrySet()) {
            sum += ent.getValue() * frec.get(0).getOrDefault((ent.getKey() - shift + sizeAlp) % sizeAlp, 0);
        }
        return 1. * sum / (len.get(j) * len.get(0));
    }
}
