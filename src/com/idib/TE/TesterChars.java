package com.idib.TE;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by idib on 14.02.17.
 */
public class TesterChars implements Callable<Boolean> {
    String testStr;
    HashMap<Character, Double> CharsRus;
    HashMap<Character, Double> CharsEng;
    double maxK = 0;
    double eps = 9e-3;


    public TesterChars(String testStr) {
        this.testStr = testStr.toLowerCase();

        CharsRus = new HashMap();
        CharsRus.put('о', 0.10983);
        CharsRus.put('е', 0.08483);
        CharsRus.put('а', 0.07998);
        CharsRus.put('и', 0.07367);
        CharsRus.put('н', 0.067);
        CharsRus.put('т', 0.06318);
        CharsRus.put('с', 0.05473);
        CharsRus.put('р', 0.04746);
        CharsRus.put('в', 0.04533);
        CharsRus.put('л', 0.04343);
        CharsRus.put('к', 0.03486);
        CharsRus.put('м', 0.03203);
        CharsRus.put('д', 0.02977);
        CharsRus.put('п', 0.02804);
        CharsRus.put('у', 0.02615);
        CharsRus.put('я', 0.02001);
        CharsRus.put('ы', 0.01898);
        CharsRus.put('ь', 0.01735);
        CharsRus.put('г', 0.01687);
        CharsRus.put('з', 0.01641);
        CharsRus.put('б', 0.01592);
        CharsRus.put('ч', 0.0145);
        CharsRus.put('й', 0.01208);
        CharsRus.put('х', 0.00966);
        CharsRus.put('ж', 0.0094);
        CharsRus.put('ш', 0.00718);
        CharsRus.put('ю', 0.00639);
        CharsRus.put('ц', 0.00486);
        CharsRus.put('щ', 0.00361);
        CharsRus.put('э', 0.00331);
        CharsRus.put('ф', 0.00267);
        CharsRus.put('ъ', 0.00037);
        CharsRus.put('ё', 0.00013);


        CharsEng = new HashMap<>();
        CharsEng.put('a', 8.167);
        CharsEng.put('b', 1.492);
        CharsEng.put('c', 2.782);
        CharsEng.put('d', 4.253);
        CharsEng.put('e', 12.702);
        CharsEng.put('f', 2.228);
        CharsEng.put('g', 2.015);
        CharsEng.put('h', 6.094);
        CharsEng.put('i', 6.966);
        CharsEng.put('j', 0.153);
        CharsEng.put('k', 0.772);
        CharsEng.put('l', 4.025);
        CharsEng.put('m', 2.406);
        CharsEng.put('n', 6.749);
        CharsEng.put('o', 7.507);
        CharsEng.put('p', 1.929);
        CharsEng.put('q', 0.095);
        CharsEng.put('r', 5.987);
        CharsEng.put('s', 6.327);
        CharsEng.put('t', 9.056);
        CharsEng.put('u', 2.758);
        CharsEng.put('v', 0.978);
        CharsEng.put('w', 2.360);
        CharsEng.put('x', 0.150);
        CharsEng.put('y', 1.974);
        CharsEng.put('z', 0.074);
    }

    @Override
    public Boolean call() throws Exception {
        int n = testStr.length();

        long countRus = 0, countEng = 0;

        HashMap<Character, Integer> cRus = new HashMap<>();
        HashMap<Character, Integer> cEng = new HashMap<>();

        for (int i = 0; i < n; i++) {
            char c = testStr.charAt(i);
            if (c >= 'а' && c <= 'я')
            {
                cRus.put(c, cRus.getOrDefault(c, 0) + 1);
                countRus++;
            }
            if (c >= 'a' && c <= 'z')
            {
                cEng.put(c, cEng.getOrDefault(c, 0) + 1);
                countEng++;
            }
        }


        double avg = 0;
        double avgK = 0;

        if (countRus > countEng)
        {
            for (Map.Entry<Character,Integer> entry : cRus.entrySet()){
                avg += Math.abs(CharsRus.get(entry.getKey()) - (double) entry.getValue() / n);
            }
            avgK =  (double)avg / cRus.size();
        }
        else {
            for (Map.Entry<Character,Integer> entry : cEng.entrySet()){
                avg += Math.abs(CharsEng.get(entry.getKey()) - (double) entry.getValue() / n);
            }
            avgK =  (double)avg / cEng.size();
        }


        System.out.println(avgK);

        if (avgK > eps)
            return false;
        return true;
    }
}
