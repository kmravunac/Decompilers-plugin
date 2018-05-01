package hr.fer.decompiler.util.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Utils {
    public static final String mainBranchPath = File.separator + "app/src/main/java";
    public static final String decompiledRoot = File.separator + "decompiled";
    public static final String jadxOutput = File.separator + "decompiled/jadx-output";
    public static final String procyonOutput = File.separator + "decompiled/procyon-output";
    public static final String fernflowerOutput = File.separator + "decompiled/fernflower-output";
    public static final String tmpDir = File.separator + "tmp";
    public static final String javaDir = File.separator + "app/src/main/java";
    public static final String resDir = File.separator + "app/src/main/res";
    public static final String manifestDir = File.separator + "app/src/main";
    public static final String dexFile = File.separator + "classes.dex";
    public static final String jarFile = File.separator + "out.jar";

    public static File fetchManifest(File startDir) {
        File manifest = null;
        File[] files = startDir.listFiles();

        for(File f : files) {
            if(f.getName().equals("AndroidManifest.xml")) {
                manifest = f;
            }
        }

        return manifest;
    }

    public static File fetchApk(String path) {
        File startDir = new File(path);
        File[] files = startDir.listFiles();

        for(File file : files) {
            if(com.google.common.io.Files.getFileExtension(file.getAbsolutePath()).equals("apk"))
                return file;
        }

        return null;
    }

    public static void cleanupDirectory(String projectPath) throws IOException {
        final Path folder = Paths.get(projectPath);
        File file = new File(projectPath);

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
                if(!(dir.toFile().equals(file)))
                    Files.delete(dir);

                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void prepareDirectories(String projectPath) {
        String tmpDir = projectPath + Utils.tmpDir;
        String decompiledDir = projectPath + Utils.decompiledRoot;
        String jadxOutput = projectPath + Utils.jadxOutput;
        String procyonOutput = projectPath + Utils.procyonOutput;
        String fernFlowerOutput = projectPath + Utils.fernflowerOutput;

        new File(tmpDir).mkdirs();
        new File(decompiledDir).mkdirs();
        new File(jadxOutput).mkdirs();
        new File(procyonOutput).mkdirs();
        new File(fernFlowerOutput).mkdirs();
    }
}
