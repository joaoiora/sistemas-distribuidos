package br.ucb.joaoiora.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by joaocarlos on 09/05/17.
 */
public class ConsoleUtils {

    public static String readString() throws IOException {
        return new BufferedReader(new InputStreamReader(System.in)).readLine();
    }

}
