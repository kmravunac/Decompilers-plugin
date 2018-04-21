package hr.fer.decompilator.util.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Utils {
    public static final String mainBranchPath = File.separator + "app/src/main/java";
    public static final String decompiledRoot = File.separator + "decompiled";
    public static final String jadxOutput = File.separator + "jadx-output";
    public static final String procyonOutput = File.separator + "procyon-output";
    public static final String fernflowerOutput = File.separator + "fernflower-output";
    public static final String tmpDir = File.separator + "tmp";
    public static final String javaDir = File.separator + "app/src/main/java";
    public static final String resDir = File.separator + "app/src/main/res";
    public static final String manifestDir = File.separator + "app/src/main";

    public static File fetchManifest(File startDir) {
        File manifest = null;

        for(File f : startDir.listFiles()) {
            if(f.getName().equals("AndroidManifest.xml")) {
                manifest = f;
            }
        }

        return manifest;
    }

    public static void cleanupDirectory(String projectPath) throws IOException {
        final Path folder = Paths.get(projectPath);
        Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc != null) {
                    throw exc;
                }
                if(!dir.toAbsolutePath().equals(projectPath))
                    Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
