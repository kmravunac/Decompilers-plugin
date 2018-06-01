package hr.fer.decompiler.util.wrapper;

public class ProcyonWrapper {
    private String outDir;
    private String jarFile;

    public ProcyonWrapper(String outDirPath, String jarFilePath) {
        this.outDir = outDirPath;
        this.jarFile = jarFilePath;
    }

    public void decompile(String... arguments) {
        String[] args = null;

        String[] parsedArgs = parseArguments(arguments);

        args = new String[4 + parsedArgs.length];
        args[0] = "-jar ";
        args[1] = jarFile;
        args[2] = "-o ";
        args[3] = outDir;

        for(int i = 0; i < parsedArgs.length; i++) {
            args[4 + i] = parsedArgs[i];
        }
        com.strobel.decompiler.DecompilerDriver.main(args);
    }

    private String[] parseArguments(String[] args) {
        String argumentString = "";

        for(String arg : args) {
            if(arg.equals("-mv") || arg.equals("--merge-variables"))
                argumentString += "-mv ";
            if(arg.equals("-ei") || arg.equals("--explicit-imports"))
                argumentString += "-ei ";
            if(arg.equals("-eta") || arg.equals("--explicit-type-arguments"))
                argumentString += "-eta ";
            if(arg.equals("-ec") || arg.equals("--retain-explicit-casts"))
                argumentString += "-ec ";
            if(arg.equals("-fsb") || arg.equals("--flatten-switch-blocks"))
                argumentString += "-fsb ";
            if(arg.equals("-ss") || arg.equals("--show-synthetic"))
                argumentString += "-ss ";
            if(arg.equals("-b") || arg.equals("--bytecode-ast"))
                argumentString += "-b ";
            if(arg.equals("-r") || arg.equals("--raw-bytecode"))
                argumentString += "-r ";
            if(arg.equals("-u") || arg.equals("--unoptimized"))
                argumentString += "-u ";
            if(arg.equals("-ent") || arg.equals("--exclude-nested"))
                argumentString += "-ent ";
            if(arg.equals("-ps") || arg.equals("--retain-pointless-switches"))
                argumentString += "-ps ";
            if(arg.equals("--unicode"))
                argumentString += "--unicode ";
        }

        argumentString = argumentString.trim();
        String[] retval = argumentString.split("\\s+");

        return retval;
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

