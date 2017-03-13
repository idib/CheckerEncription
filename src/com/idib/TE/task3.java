package com.idib.TE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by idib on 12.03.17.
 */
public class task3 {
    static int countChars = 7;
    static double maxDist = 1e-2;
    static int maxCounts = 3;
    static Scanner in = new Scanner(System.in);
    static String Dic = "src/dic/All_Forms2.txt";
    static String TXT;
    static HashMap<Character, Double> alp = new HashMap<Character, Double>() {{
        put('а', 0.07998);
        put('б', 0.01592);
        put('в', 0.04533);
        put('г', 0.01687);
        put('д', 0.02977);
        put('е', 0.08483);
        put('ж', 0.0094);
        put('з', 0.01641);
        put('и', 0.07367);
        put('й', 0.01208);
        put('к', 0.03486);
        put('л', 0.04343);
        put('м', 0.03203);
        put('н', 0.067);
        put('о', 0.10983);
        put('п', 0.02804);
        put('р', 0.04746);
        put('с', 0.05473);
        put('т', 0.06318);
        put('у', 0.02615);
        put('ф', 0.00267);
        put('х', 0.00966);
        put('ц', 0.00486);
        put('ч', 0.0145);
        put('ш', 0.00718);
        put('щ', 0.00361);
        put('ъ', 0.00037);
        put('ы', 0.01898);
        put('ь', 0.01735);
        put('э', 0.00331);
        put('ю', 0.00639);
        put('я', 0.02001);
        put('ё', 0.00013);
    }};
    static List<HashMap<Integer, Character>> vars;
    static int LimitForFindWords = 20;
    static List<String> dics;

    public static void main(String[] args) throws IOException {
        TXT = Tester.read("src/tests/task3");
//        TXT = Tester.read("src/tests/task3_4");
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


        HashMap<Integer, Double> frequency = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : cI.entrySet()) {
            frequency.put(entry.getKey(), (double) entry.getValue() / counts);
        }

        HashMap<Integer, Set<Character>> t = Juxtapose(alp, frequency);
        System.out.println(frequency.size());
        System.out.println(t.size());
        System.out.println(t);

        BigInteger coutsss = BigInteger.ONE;
        for (Map.Entry<Integer, Set<Character>> entry : t.entrySet()) {
            coutsss = coutsss.multiply(BigInteger.valueOf(entry.getValue().size()));
        }
        System.out.println(coutsss);

        List<Map.Entry<Integer, Integer>> c = new ArrayList<>();
        c.addAll(cI.entrySet());
        c.sort((o1, o2) -> Integer.compare(o2.getValue(), o1.getValue()));
        List<Map.Entry<Integer, Integer>> r = c.subList(0, countChars);
        System.out.println(r);


        printWordsNoUnicalCars(intWords);


//        HashMap<Integer, Character> cur = new HashMap<>();
//        while (in.hasNextInt()) {
//            int i = in.nextInt();
//            char j = in.next().charAt(0);
//            cur.put(i, j);
//            System.out.println(getText(TXT, cur));
//        }
    }

    static void printWordsNoUnicalCars(int[][] intWords) {

        Arrays.sort(intWords, (o1, o2) -> {
            Set a = Arrays.stream(o1).boxed().collect(Collectors.toSet());
            Set b = Arrays.stream(o2).boxed().collect(Collectors.toSet());
            if (a.size() != b.size())
                return Integer.compare(o2.length - b.size(), o1.length - a.size());
            else
                return Integer.compare(o2.length, o1.length);
        });

        List<String> finds = find(intWords[0], new HashMap<>());
        List<HashMap<Integer, Character>> lis = getTrans(finds, intWords[0]);
        Set<HashMap<Integer, Character>> res = new HashSet<>();
        System.out.println(finds);
        for (HashMap<Integer, Character> li : lis) {
            res.addAll(nextVar(li, intWords));
        }
        for (HashMap<Integer, Character> re : res) {
            System.out.println(re);
            System.out.println(getText(TXT, re));
        }
    }


    static Set<HashMap<Integer, Character>> nextVar(HashMap<Integer, Character> trans, int[][] intWords) {
        List<nodesort> words = new ArrayList<>();
        for (int[] w : intWords) {
            int counts = 0;
            for (int i = 0; i < w.length; i++) {
                if (trans.containsKey(w[i]))
                    counts++;
            }
            if (counts != w.length)
                words.add(new nodesort(w, w.length - counts));
        }

        Set<HashMap<Integer, Character>> res = new HashSet<>();

        System.out.println(words.size());

        if (words.size() == 0) {
            res.add(trans);
            return res;
        }

        words.sort((o1, o2) -> {
            if (o2.w.length == o1.w.length)
                return Integer.compare(o1.c, o2.c);
            else
                return Integer.compare(o2.w.length, o1.w.length);
        });
        for (nodesort word : words) {
            List<String> temp = find(word.w, trans);
            if (temp != null && temp.size() != 0) {
                System.out.println(temp);
                List<HashMap<Integer, Character>> li = getTrans(trans, temp, word.w);
                for (HashMap<Integer, Character> ent : li) {
                    Set<HashMap<Integer, Character>> r = nextVar(ent, intWords);
                    if (r.size() == 1)
                        return r;
                    else
                        res.addAll(r);
                }
            }
        }
        return res;
    }

    static List<HashMap<Integer, Character>> getTrans(List<String> finds, int[] intWords) {
        Map<Integer, Character> tr = new HashMap<>(), cl;
        List res = new ArrayList();
        if (finds.size() > 0) {
            for (int i = 0; i < finds.get(0).length(); i++) {
                boolean fl = true;
                for (int j = 1; fl && j < finds.size(); j++)
                    if (finds.get(0).charAt(i) != finds.get(j).charAt(i))
                        fl = false;
                if (fl)
                    tr.put(intWords[i], finds.get(0).charAt(i));
            }

            for (int i = 0; i < finds.size(); i++) {
                cl = new HashMap<>(tr);
                boolean fl = false;
                for (int j = 0; j < finds.get(i).length(); j++) {
                    if (!tr.containsKey(intWords[j])) {
                        cl.put(intWords[j], finds.get(i).charAt(j));
                        fl = true;
                    }
                }
                if (fl)
                    res.add(cl);
            }
        }
        if (res.size() == 0 && tr.size() != 0)
            res.add(tr);
        return res;
    }

    static List<HashMap<Integer, Character>> getTrans(HashMap<Integer, Character> tr, List<String> finds, int[] intWords) {
        Map<Integer, Character> cl;
        List res = new ArrayList();
        if (finds.size() > 0) {
            for (int i = 0; i < finds.get(0).length(); i++) {
                if (!tr.containsKey(finds.get(0).charAt(i))) {
                    boolean fl = true;
                    for (int j = 1; fl && j < finds.size(); j++)
                        if (finds.get(0).charAt(i) != finds.get(j).charAt(i))
                            fl = false;
                    if (fl) {
                        if (tr.containsValue(finds.get(0).charAt(i)) &&
                                tr.containsKey(intWords[i]) &&
                                tr.get(intWords[i]) == finds.get(0).charAt(i)) {
                            tr.put(intWords[i], finds.get(0).charAt(i));
                        } else {
                            return res;
                        }
                    }
                }
            }

            for (int i = 0; i < finds.size(); i++) {
                cl = new HashMap<>(tr);
                boolean fl = false;
                for (int j = 0; j < finds.get(i).length(); j++) {
                    if (!cl.containsKey(intWords[j])) {
                        if (!cl.containsValue(finds.get(i).charAt(j))) {
                            cl.put(intWords[j], finds.get(i).charAt(j));
                        } else
                            return res;
                        fl = true;
                    }
                }
                if (fl)
                    res.add(cl);
            }
        }
        if (res.size() == 0 && tr.size() != 0)
            res.add(tr);
        return res;
    }

    static List<String> find(int[] word, HashMap<Integer, Character> trans) {
        List<String> GoodWords = new ArrayList();

        String findstr = "";
        Set<Integer> unic = new HashSet<>();
        for (int i : word) {
            findstr += trans.getOrDefault(i, '.');
            unic.add(i);
        }
        List<Pattern> patterns = new ArrayList<>();
        patterns.add(Pattern.compile(findstr));
        for (int i = 0; i < word.length; i++)
            if (!trans.containsKey(word[i]))
                for (int j = i + 1; j < word.length; j++)
                    if (word[i] == word[j])
                        patterns.add(Pattern.compile("^.{" + i + "}(([а-я]).{" + (j - i - 1) + "}\\2)"));


        try {
            if (dics == null) {
                init();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String str : dics) {
            if (str.length() == word.length) {
                Set<Character> u = str.chars().mapToObj(e -> (char) e).collect(Collectors.toSet());
                boolean fl = true;
                if (u.size() != unic.size())
                    fl = false;
                int i = 0;
                while (i < patterns.size() && fl) {
                    fl = patterns.get(i).matcher(str).find();
                    i++;
                }
                if (fl)
                    GoodWords.add(str);

//                if (GoodWords.size() > LimitForFindWords) {
//                    return null;
//                }
            }
        }


        return GoodWords;
    }

    static void init() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(Dic));
        String str;
        dics = new ArrayList<>();
        while ((str = in.readLine()) != null) {
            dics.add(str.trim().toLowerCase());
        }
    }

    static String getText(String sorce, HashMap<Integer, Character> translit) {
        for (Map.Entry<Integer, Character> tr : translit.entrySet()) {
            sorce = sorce.replaceAll(tr.getKey().toString() + "\\s?", tr.getValue().toString());
        }
        return sorce;
    }

    static HashMap<Integer, Set<Character>> Juxtapose(HashMap<Character, Double> alphabet, HashMap<Integer, Double> curFrec) {
        HashMap<Integer, Set<Character>> res = new HashMap<>();
        for (Map.Entry<Integer, Double> ent : curFrec.entrySet()) {
            for (Map.Entry<Character, Double> alp : alphabet.entrySet()) {
                if (Math.abs(ent.getValue() - alp.getValue()) < maxDist) {
                    if (!res.containsKey(ent.getKey()))
                        res.put(ent.getKey(), new HashSet<>());
                    res.get(ent.getKey()).add(alp.getKey());
                }
            }
        }
        return res;
    }

    static class nodesort {
        int[] w;
        int c;

        public nodesort(int[] w, int c) {
            this.w = w;
            this.c = c;
        }
    }
}
