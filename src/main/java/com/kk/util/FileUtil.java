package com.kk.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileUtil {
    public static long countLines(String file) {
        Stream<String> lines = null;
        try {
            lines = Files.lines(Paths.get(file));
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return lines.count();
    }

    public static BufferedReader bufferedReader(String file) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    }

    public static void closeWriter(PrintWriter writer) {
        if (writer != null) {
            writer.flush();
            writer.close();
        }
    }

    public static void closeReader(Reader reader) {
        try {
            if (reader != null) reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
