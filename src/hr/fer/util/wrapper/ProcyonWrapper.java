package hr.fer.util.wrapper;

public class ProcyonWrapper {
    private String outDir;
    private String jarFile;

    public ProcyonWrapper(String outDirPath, String jarFilePath) {
        this.outDir = outDirPath;
        this.jarFile = jarFilePath;
    }

    public void decompile(String... arguments) {
        String[] args = null;
        arguments = new String[4 + arguments.length];
        arguments[0] = "-jar ";
        arguments[1] = jarFile;
        arguments[2] = "-o ";
        arguments[4] = outDir;

        for(int i = 0; i < arguments.length; i++) {
            arguments[4 + i] = arguments[i];
        }
        com.strobel.decompiler.DecompilerDriver.main(args);
    }
}

