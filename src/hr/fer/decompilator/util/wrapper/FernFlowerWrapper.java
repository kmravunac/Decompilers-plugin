package hr.fer.decompilator.util.wrapper;

import hr.fer.decompilator.util.utility.ZipUtility;

import java.io.File;

public class FernFlowerWrapper {
    private String outDir;
    private String jarFile;

    public FernFlowerWrapper(String outDir, String jarFile) {
        this.outDir = outDir;
        this.jarFile = jarFile;
    }

    public void decompile(String... arguments) {
        String[] args = new String[2 + arguments.length];

        for(int i = 0; i < arguments.length; i++) {
            args[i] = arguments[i];
        }

        args[arguments.length] = jarFile;
        args[arguments.length + 1] = outDir;

        org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler.main(args);

        File jar = new File(jarFile);
        String jarName = jar.getName();

        String toUnzip = new String(outDir + File.separator + jarName);
        ZipUtility.unzip(toUnzip, outDir);

        File toDelete = new File(toUnzip);
        toDelete.delete();
    }

    public String getOutDir() {
        return this.outDir;
    }

    public void setOutDir(String outDir) {
        this.outDir = outDir;
    }

    public String getJarFile() {
        return this.jarFile;
    }

    public void setJarFile(String jarFile) {
        this.jarFile = jarFile;
    }
}

