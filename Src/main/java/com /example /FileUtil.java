package com.example;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtil {

    public static List<Path> listFiles(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (Stream<Path> stream = Files.walk(path)) {
                return stream
                        .filter(Files::isRegularFile)
                        .collect(Collectors.toList());
            }
        } else {
            List<Path> singleFile = new ArrayList<>();
            singleFile.add(path);
            return singleFile;
        }
    }

    public static void createDirectories(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
        }
    }
}
