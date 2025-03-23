package com.andreidodu.sbpv.util;

import java.io.InputStream;
import java.util.Scanner;

public class CommandUtil {
    public static final String END_OF_LINE = "\\n";

    public static String executeCommand(String[] cmd) throws java.io.IOException, InterruptedException {
        Process proc = Runtime.getRuntime().exec(cmd);
        proc.waitFor();

        InputStream is = proc.getInputStream();
        Scanner s = new Scanner(is).useDelimiter(END_OF_LINE);

        InputStream es = proc.getErrorStream();
        Scanner e = new Scanner(es).useDelimiter(END_OF_LINE);

        if (!s.hasNext()) {
            s = e;
        }

        if (s.hasNext()) {
            return s.next();
        }

        return "";
    }

}
