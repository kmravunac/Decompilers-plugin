package hr.fer.decompilator.util.wrapper;

public class ProcyonWrapper {
    private String outDir;
    private String jarFile;

    public ProcyonWrapper(String outDirPath, String jarFilePath) {
        this.outDir = outDirPath;
        this.jarFile = jarFilePath;
    }

    public void decompile(String... arguments) {
        String[] args = null;
        args = new String[4 + arguments.length];
        args[0] = "-jar ";
        args[1] = jarFile;
        args[2] = "-o ";
        args[4] = outDir;

        for(int i = 0; i < arguments.length; i++) {
            args[4 + i] = arguments[i];
        }
        com.strobel.decompiler.DecompilerDriver.main(args);
    }

    public String getOutDir() {
        return this.outDir;
    }

    public void setOutDir(String outDirPath) {
        this.outDir = outDirPath;
    }

    public String getJarFile() {
        return this.jarFile;
    }

    public void setJarFile(String jarFilePath) {
        this.jarFile = jarFilePath;
    }
}

