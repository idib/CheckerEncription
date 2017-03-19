package com.idib.TE;

import org.bouncycastle.util.encoders.Hex;

/**
 * Created by idib on 19.03.17.
 */
public class lab3 {

    public static void main(String[] args) {


        GOST encriptor = new GOST();
        String originalString = "String to encode";
        String Key = "37373lfmslidnfgsanboigfa psjf0923hj0r9 uaO(*hsf890qg8h3fo8gha9o08e0ghf[98AGSEH[90f8g8";
        String encoded = encriptor.Encode(originalString, Key.getBytes());
        String decoded = encriptor.Decode(encoded, Key.getBytes(), Hex.decode(originalString).length);
    }

}
