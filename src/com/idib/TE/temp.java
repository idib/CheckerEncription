package com.idib.TE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by idib on 06.03.17.
 */
public class temp {
    public static void main(String[] args) throws IOException {
        Scanner in  = new Scanner(new File("src/dic/All_Forms2.txt"));
        FileWriter out;
        File file = new File("src/dic/All_Forms4.txt");
        out = new FileWriter(file);
//        while (in.hasNextLine())
//        {
//            String l = in.nextLine();
//            if (l.length() == 16)
//                out.write(l + '\n');
//        }

        for (char i = 'а'; i <= 'я'; i++) {
            out.write(i +".{4}" + i + '|');
        }


        out.close();
    }
}
