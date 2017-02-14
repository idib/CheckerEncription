package com.idib.TE;

import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 * Created by idib on 14.02.17.
 */
public class TesterChars implements Callable<Boolean> {
    String testStr;
    HashMap<Character, Double> of;
    double maxK = 0;
    double eps = 1e-5;


    public TesterChars(String testStr) {
        this.testStr = testStr.toLowerCase();

        of = new HashMap();
        of.put('о', 0.10983);
        of.put('е', 0.08483);
        of.put('а', 0.07998);
        of.put('и', 0.07367);
        of.put('н', 0.067);
        of.put('т', 0.06318);
        of.put('с', 0.05473);
        of.put('р', 0.04746);
        of.put('в', 0.04533);
        of.put('л', 0.04343);
        of.put('к', 0.03486);
        of.put('м', 0.03203);
        of.put('д', 0.02977);
        of.put('п', 0.02804);
        of.put('у', 0.02615);
        of.put('я', 0.02001);
        of.put('ы', 0.01898);
        of.put('ь', 0.01735);
        of.put('г', 0.01687);
        of.put('з', 0.01641);
        of.put('б', 0.01592);
        of.put('ч', 0.0145);
        of.put('й', 0.01208);
        of.put('х', 0.00966);
        of.put('ж', 0.0094);
        of.put('ш', 0.00718);
        of.put('ю', 0.00639);
        of.put('ц', 0.00486);
        of.put('щ', 0.00361);
        of.put('э', 0.00331);
        of.put('ф', 0.00267);
        of.put('ъ', 0.00037);
        of.put('ё', 0.00013);

    }

    private void max(double k) {
        if (maxK < k)
            maxK = k;
    }

    @Override
    public Boolean call() throws Exception {
        int n = testStr.length();

        HashMap<Character, Integer> f = new HashMap<>();

        for (int i = 0; i < n; i++) {
            char c = testStr.charAt(i);
            if (c >= 'а' && c <= 'я')
                f.put(testStr.charAt(i), f.getOrDefault(testStr.charAt(i), 0) + 1);
        }


        f.forEach((character, integer) -> {
            double k = of.get(character) - (double) integer / testStr.length();
            max(k);
        });


        if (maxK > eps)
            return false;
        return true ;
    }
}
