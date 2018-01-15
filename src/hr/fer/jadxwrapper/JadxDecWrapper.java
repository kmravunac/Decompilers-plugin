package hr.fer.jadxwrapper;

import jadx.api.JadxArgs;
import jadx.api.JadxDecompiler;
import jadx.core.utils.exceptions.JadxException;

import java.io.File;

public class JadxDecWrapper {
    private File apkFile;
    private File outDir;
    private JadxArgs arguments;
    private JadxDecompiler decompiler;

    public JadxDecWrapper(File apkFile, File outDir, JadxArgs args) {
        this.arguments = args;
        this.apkFile = apkFile;
        this.outDir = outDir;
        this.decompiler = new JadxDecompiler(args);
    }

    public JadxDecWrapper(File apkFile, File outDir) {
        this.apkFile = apkFile;
        this.outDir = outDir;
        this.decompiler = new JadxDecompiler();
    }

    public void setApkFile(File apkFile) {
        this.apkFile = apkFile;
    }

    public File getApkFile() {
        return apkFile;
    }

    public File getOutDir() {
        return outDir;
    }

    public void setOutDir(File outDir) {
        this.outDir = outDir;
    }

    public JadxArgs getArguments() {
        return arguments;
    }

    public void setArguments(JadxArgs args) {
        this.arguments = args;
    }

    public void decompile() {
        try {
            decompiler.loadFile(apkFile);
        } catch (JadxException e) {
            System.out.println("Something went wrong, jadx exception: ");
            e.printStackTrace();
        }

        decompiler.setOutputDir(outDir);

        decompiler.save();
    }
}

