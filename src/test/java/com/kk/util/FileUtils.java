package com.kk.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileUtils {
    public static void cleanData() throws IOException {
        Stream<Path> files = Files.list(Paths.get("data/"));

        files.forEach(path -> {
            String fileName = path.getFileName().toString();

            if (!fileName.equals("original.dat")) {
                try {
                    Files.deleteIfExists(Paths.get("data/" + fileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
