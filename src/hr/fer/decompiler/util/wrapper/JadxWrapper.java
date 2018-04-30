package hr.fer.decompiler.util.wrapper;

import jadx.api.JadxArgs;
import jadx.api.JadxDecompiler;
import jadx.core.utils.exceptions.JadxException;

import java.io.File;

public class JadxWrapper {
    private File apkFile;
    private File outDir;
    private JadxArgs arguments;
    private JadxDecompiler decompiler;

    public JadxWrapper(File apkFile, File outDir, String args) {
        this.arguments = parseArguments(args);
        this.apkFile = apkFile;
        this.outDir = outDir;
        this.decompiler = new JadxDecompiler(arguments);
    }

    public JadxWrapper(File apkFile, File outDir) {
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

    private JadxArgs parseArguments(String arguments) {
        JadxArgs jadxargs = new JadxArgs();
        String args[] = arguments.trim().split("\\s+");

        for(int i = 0; i < args.length; i++) {
            if(args[i].equals("-r") || args[i].equals("--no-res"))
                jadxargs.setSkipSources(true);

            if(args[i].equals("-j") || args[i].equals("--threads-count"))
                jadxargs.setThreadsCount(Integer.parseInt(args[i+1]));

            if(args[i].equals("-s") || args[i].equals("--no-src"))
                jadxargs.setSkipSources(true);

            if(args[i].equals("-e") || args[i].equals("--export-gradle"))
                jadxargs.setExportAsGradleProject(true);

            if(args[i].equals("--show-bad-code"))
                jadxargs.setShowInconsistentCode(true);

            if(args[i].equals("--no-replace-consts"))
                jadxargs.setReplaceConsts(true);

            if(args[i].equals("--escape-unicode"))
                jadxargs.setEscapeUnicode(true);

            if(args[i].equals("--deobf"))
                jadxargs.setDeobfuscationOn(true);

            if(args[i].equals("--deobf-min"))
                jadxargs.setDeobfuscationMaxLength(Integer.parseInt(args[i+1]));

            if(args[i].equals("--deobf-max"))
                jadxargs.setDeobfuscationMaxLength(Integer.parseInt(args[i+1]));

            if(args[i].equals("--deobf-rewrite-cfg"))
                jadxargs.setDeobfuscationForceSave(true);

            if(args[i].equals("--deobf-use-sourcename"))
                jadxargs.setUseSourceNameAsClassAlias(true);

            if(args[i].equals("--cfg"))
                jadxargs.setCfgOutput(true);

            if(args[i].equals("--raw-cfg"))
                jadxargs.setRawCFGOutput(true);

            if(args[i].equals("-f"))
                jadxargs.setFallbackMode(true);

            if(args[i].equals("-v"))
                jadxargs.setVerbose(true);
        }

        return jadxargs;
    }
}

