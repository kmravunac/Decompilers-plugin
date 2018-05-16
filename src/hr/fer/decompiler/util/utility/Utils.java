package hr.fer.decompiler.util.utility;

import org.apache.commons.io.FileUtils;

import javax.swing.*;
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
    public static final String backupDir = "/tmp/backup";
    public static final String jadxBackup = "/tmp/backup/jadx";
    public static final String procyonBackup = "/tmp/backup/procyon";
    public static final String fernFlowerBackup = "/tmp/backup/fernflower";
    public static final String smaliBackup = "/tmp/backup/smali";
    public static final String logDir = "/logs";
    public static final String jadxLog = "/logs/jadx-log.txt";
    public static final String procyonLog = "/logs/procyon-log.txt";
    public static final String fernflowerLog = "/logs/fernflower-log.txt";

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
        String backupDir = projectPath + Utils.backupDir;
        String jadxBackup = projectPath + Utils.jadxBackup;
        String procyonBackup = projectPath + Utils.procyonBackup;
        String fernFlowerBackup = projectPath + Utils.fernFlowerBackup;
        String smaliBackup = projectPath + Utils.smaliBackup;
        String logDirectory = projectPath + Utils.logDir;

        new File(tmpDir).mkdirs();
        new File(decompiledDir).mkdirs();
        new File(jadxOutput).mkdirs();
        new File(procyonOutput).mkdirs();
        new File(fernFlowerOutput).mkdirs();
        new File(smaliDir).mkdirs();
        new File(backupDir).mkdirs();
        new File(jadxBackup).mkdirs();
        new File(procyonBackup).mkdirs();
        new File(fernFlowerBackup).mkdirs();
        new File(smaliBackup).mkdirs();
        new File(logDirectory).mkdirs();
    }

    public static void copyDirectory(String src, String dest) {
        File srcFile = new File(src);
        File destFile = new File(dest);

        try {
            FileUtils.copyDirectory(srcFile, destFile);
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null, "Something went wrong when copying files: " + e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
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

    public static String determineDirectoryPath(String filePath) {
        String[] splitPath = filePath.split("/");
        return splitPath[splitPath.length - 1];
    }

    public static boolean hasBackup(String fileName, String backupPath) {
        String filePath = backupPath + "/" + fileName;

        Path path = Paths.get(filePath);

        return Files.exists(path);
    }

    public static void backupFile(String fileName, String backupPath, String originalPath) {
        String filePath = backupPath + "/" + fileName;

        try {
            Files.copy(Paths.get(originalPath), Paths.get(filePath));
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String determineDirectorySuffix(String filePath) {
        if(filePath.contains("jadx-output")) {
            return jadxBackup;
        } else if(filePath.contains("fernflower-output")) {
            return fernFlowerBackup;
        } else if(filePath.contains("procyon-output")) {
            return procyonBackup;
        }else if(filePath.contains("smali/out")) {
            return smaliBackup;
        } else {
            return "";
        }
    }
}
