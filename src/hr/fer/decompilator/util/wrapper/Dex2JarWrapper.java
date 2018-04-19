package hr.fer.decompilator.util.wrapper;

public class Dex2JarWrapper {
    private String dexFile;
    private String outFile;

    public Dex2JarWrapper(String dexFile, String outFile) {
        this.dexFile = dexFile;
        this.outFile = outFile;
    }

    public void dex2jar(String... arguments) {
        String[] args = null;
        args = new String[3 + arguments.length];
        args[0] = dexFile;
        args[1] = "-o";
        args[2] = outFile;

        for(int i = 0; i < arguments.length; i++) {
            args[3 + i] = arguments[i];
        }

        com.googlecode.dex2jar.tools.Dex2jarCmd.main(args);
    }

    public String getDexFile() {
        return this.dexFile;
    }

    public void setDexFile(String dexFile) {
        this.dexFile = dexFile;
    }

    public String getOutFile() {
        return this.outFile;
    }

    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }
}
