package com.idib.TE;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by idib on 14.02.17.
 */
public class tryGoogle implements Callable<Integer> {
    private double eps = 1e3;

    private static Pattern patternDomainName;
    private Matcher matcher;
    private static final String DOMAIN_NAME_PATTERN
            = "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";

    private Queue<String> tested;

    static {
        patternDomainName = Pattern.compile(DOMAIN_NAME_PATTERN);
    }

    public tryGoogle() {
        tested = new LinkedList<>();
    }

    public void add(String a){
        if (a.length() > 13)
            tested.add(a.substring(0,13));
        else
            tested.add(a);
    }

    public static void main(String[] args) {

        tryGoogle obj = new tryGoogle();
        long r = obj.test("mario");
    }


    public String getDomainName(String url){

        String domainName = "";
        matcher = patternDomainName.matcher(url);
        if (matcher.find()) {
            domainName = matcher.group(0).toLowerCase().trim();
        }
        return domainName;

    }

    private long test(String query){
        Set<String> result = new HashSet<String>();
        String request = "https://www.google.com/search?q=" + query + "&num=20";
        System.out.println("Sending request..." + request);

        try {

            // need http protocol, set this as a Google bot agent :)
            Document doc = (Document) Jsoup.connect(request)
                    .userAgent("Mozilla/6.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
                    .timeout(5000).get();

            // get all links

            Elements links = doc.select("#resultStats");


            String countStr =  links.toString();

            countStr  = countStr.replace("&nbsp;","").replace("примерно","").replace("Результатов:","").replace("<div class=\"sd\" id=\"resultStats\">","").replace("</div>","");

            System.out.println(Long.parseLong(countStr.trim()));
            return Long.parseLong(countStr.trim());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }




    @Override
    public Integer call() throws Exception {
        int good = 0;
        int bad = 0;
        while (!tested.isEmpty()){
            if (test(tested.poll()) > eps)
                good++;
            Thread.sleep(1000);
        }
        return good;
    }
}
