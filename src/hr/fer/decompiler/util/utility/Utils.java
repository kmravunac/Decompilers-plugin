package hr.fer.decompiler.util.utility;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Utils {
    public static final String mainBranchPath = "/app/src/main/java";
    public static final String decompiledRoot = "/decompiled";
    public static final String jadxOutput = "/decompiled/jadx-output";
    public static final String procyonOutput = "/decompiled/procyon-output";
    public static final String fernflowerOutput = "/decompiled/fernflower-output";
    public static final String tmpDir = "/tmp";
    public static final String javaDir = "/app/src/main/java";
    public static final String resDir = "/app/src/main/res";
    public static final String manifestDir = "/app/src/main";
    public static final String dexFile = "/classes.dex";
    public static final String jarFile = "/out.jar";
    public static final String smaliDir = "/decompiled/smali";
    public static final String smaliCodeLocation = "/smali/out";

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
        String smaliDir = projectPath + Utils.smaliDir;

        new File(tmpDir).mkdirs();
        new File(decompiledDir).mkdirs();
        new File(jadxOutput).mkdirs();
        new File(procyonOutput).mkdirs();
        new File(fernFlowerOutput).mkdirs();
        new File(smaliDir).mkdirs();
    }

    public static void copyDirectory(String src, String dest) {
        File srcFile = new File(src);
        File destFile = new File(dest);

        try {
            FileUtils.copyDirectory(srcFile, destFile);
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String preparePath(String filePath, String projectPath) {
        String result = filePath.replace(projectPath, "");
        result = result.replace(jadxOutput, "");
        result = result.replace(fernflowerOutput, "");
        result = result.replace(procyonOutput, "");
        result = result.replace(smaliDir, "");
        result = result.replace(smaliCodeLocation, "");
        result = result.replace(".smali", ".java");

        return result;
    }
}
