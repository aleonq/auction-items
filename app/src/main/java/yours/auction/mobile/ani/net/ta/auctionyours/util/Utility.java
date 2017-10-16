package yours.auction.mobile.ani.net.ta.auctionyours.util;

import java.util.Random;

/**
 * Created by taru on 5/15/2017.
 */

public class Utility {
    public static String getAuthotity(String prefix, String suffix) {
        return prefix + "." + suffix;
    }

    public static int generateRandomNumber(int start, int end) {
        Random random = new Random();
        return random.nextInt(end - start) + start;
    }

    public static boolean equalsIgnoreCase(String s1, String s2) {
        if (s1 == s2) {
            return true;
        }
        if ((s1 == null && s2 != null) || (s2 == null && s1 != null)) {
            return false;
        }
        if (s1.trim().toLowerCase().equals(s2.trim().toLowerCase())) {
            return true;
        }
        return false;
    }
}
