package com.idib.TE;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by idib on 12.03.17.
 */
public class task3 {
    static int countChars = 7;
    static Scanner in = new Scanner(System.in);
    static String TXT;
    List<Character> topChars = Arrays.asList('о', 'е', 'а', 'и', 'н', 'т', 'с', 'р', 'в', 'л', 'к', 'м', 'д', 'п', 'у', 'я', 'ы', 'ь', 'г', 'з', 'б', 'ч', 'й', 'х', 'ж', 'ш', 'ю', 'ц', 'щ', 'э', 'ф', 'ъ', 'ё');
    List<HashMap<Integer, Character>> vars;

    public static void main(String[] args) throws IOException {
        TXT = Tester.read("src/tests/task3");
        String[] words = TXT.split("([^\\d] )+");
        int[][] intWords = new int[words.length][];
        for (int i = 0; i < words.length; i++) {
            try {
                intWords[i] = Arrays.stream(words[i].split("(?<=\\d) (?=\\d)")).mapToInt(s -> Integer.parseInt(s.trim())).toArray();
            } catch (Exception e) {
                System.out.println(Arrays.toString(words[i].split("(?<=\\d) (?=\\d)")));
            }
        }

        HashMap<Integer, Integer> cI = new HashMap<>();
        long counts = 0;
        for (int[] word : intWords) {
            for (int i : word) {
                cI.put(i, cI.getOrDefault(i, 0) + 1);
                counts++;
            }
        }

        Arrays.sort(intWords, (o1, o2) -> {
            Set a = Arrays.stream(o1).boxed().collect(Collectors.toSet());
            Set b = Arrays.stream(o2).boxed().collect(Collectors.toSet());
            if (a.size() != b.size())
                return Integer.compare(o2.length - b.size(), o1.length - a.size());
            else
                return Integer.compare(o2.length, o1.length);
        });

        for (int[] i : intWords) {
            System.out.println(Arrays.toString(i));
            Set a = Arrays.stream(i).boxed().collect(Collectors.toSet());
            System.out.println(a.size());
            System.out.println(i.length);
        }

        List<Map.Entry<Integer, Integer>> c = new ArrayList<>();
        c.addAll(cI.entrySet());
        c.sort((o1, o2) -> Integer.compare(o2.getValue(), o1.getValue()));


//        System.out.println(c);
        List<Map.Entry<Integer, Integer>> r = c.subList(0, countChars);


        HashMap<Integer, Character> cur = new HashMap<>();
        while (in.hasNextInt()) {
            int i = in.nextInt();
            char j = in.next().charAt(0);
            cur.put(i, j);
            System.out.println(getText(TXT, cur));
        }
    }

    static void next(List<Map.Entry<Integer, Integer>> var, boolean[] use, int depth) {
        if (depth == countChars) {

        } else {
            for (int i = 0; i < var.size(); i++) {

            }
        }
    }


    static String getText(String sorce, HashMap<Integer, Character> translit) {
        for (Map.Entry<Integer, Character> tr : translit.entrySet()) {
            sorce = sorce.replaceAll(tr.getKey().toString() + "\\s?", tr.getValue().toString());
        }
        return sorce;
    }
}
