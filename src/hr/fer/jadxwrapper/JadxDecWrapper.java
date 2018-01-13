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

    public JadxDecWrapper(File apkFile, File outDir, String args) {
        // TODO set all arguments using method calls
        //this.arguments = new JadxArgs();
        //this.arguments.set...

        this.apkFile = apkFile;
        this.outDir = outDir;
        // TODO call decompiler with the specified arguments
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

    public void decompile() {
        decompiler.setOutputDir(outDir);

        try {
            decompiler.loadFile(apkFile);
        } catch (JadxException e) {
            System.out.println("Something went wrong, jadx exception: ");
            e.printStackTrace();
        }

        decompiler.save();
    }
}

