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

    public JadxDecWrapper(String apkFilePath, String outDirPath, String args) {
        // TODO set all arguments using method calls
        //this.arguments = new JadxArgs();
        //this.arguments.set...

        this.apkFile = new File(apkFilePath);
        this.outDir = new File(outDirPath);
        // TODO call decompiler with the specified arguments
        this.decompiler = new JadxDecompiler();
    }

    public void setApkFile(String apkFilePath) {
        apkFile = new File(apkFilePath);
    }

    public String getApkFilePath() {
        return apkFile.getAbsolutePath();
    }

    public String getOutDirPath() {
        return outDir.getAbsolutePath();
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

