package dao;

import org.apache.log4j.Logger;

public class Encode {

    /**
     converts String with "ISO-8859-1" to String with
     "UTF-8" encoding
     @param str - String which will be converted
     */
    public static String toUTF8(String str) {
        String out = null;
        try {
            out = new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            Logger.getLogger(Encode.class).error(e.getMessage());
            return null;
        }
        return out;
    }
}