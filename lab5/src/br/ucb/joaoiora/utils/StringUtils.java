package br.ucb.joaoiora.utils;

/**
 * Created by joaocarlos on 08/05/17.
 */
public class StringUtils {

    public static boolean isEmpty(final CharSequence string) {
        return string == null || string.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence string) {
        return !isEmpty(string);
    }

}
