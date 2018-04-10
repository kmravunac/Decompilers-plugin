package hr.fer.util.wrapper;

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
    }
}

