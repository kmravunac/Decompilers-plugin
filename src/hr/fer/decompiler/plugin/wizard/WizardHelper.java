package hr.fer.decompiler.plugin.wizard;

import hr.fer.decompiler.util.utility.Utils;

import java.io.File;

public class WizardHelper {
    private DecompilerWizardModel model;
    private FileSelectionStep fs;
    private SelectDecompilersStep ds;

    public WizardHelper() {
        model = new DecompilerWizardModel("Decompilator");
        fs = new FileSelectionStep("Select APK file");
        ds = new SelectDecompilersStep("Choose decompilers");

        model.add(fs);
        model.add(ds);

        fs.setState(model.getCurrentNavigationState());
        ds.setState(model.getCurrentNavigationState());
    }

    public boolean run() {
        try {
            DecompilerWizardDialog dialog = new DecompilerWizardDialog(false, model);
            dialog.show();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return model.isWizardGoalAchieved();
    }

    public File getApkFile() {
        return fs.getFile();
    }

    public String getJadxArgs() {
        return ds.getJadxArgs();
    }

    public String getProcyonArgs() {
        return ds.getProcyonArgs();
    }

    public String getFernFlowerArgs() {
        return ds.getFernFlowerArgs();
    }

    public boolean jadxSelected() {
        return ds.jadxSelected();
    }

    public boolean fernFlowerSelected() {
        return ds.fernFlowerSelected();
    }

    public boolean procyonSelected() {
        return ds.procyonSelected();
    }

    public void doCleanup(String projectPath) {
        String javaDir = projectPath  + Utils.javaDir;
        String resDir = projectPath  + Utils.resDir;
        String manifest = projectPath  + Utils.manifestDir + File.separator + "AndroidManifest.xml";

        try {
            Utils.cleanupDirectory(javaDir);
            Utils.cleanupDirectory(resDir);
            File man = new File(manifest);
            man.delete();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void prepareDirectories(String projectPath) {
        String tmpDir = projectPath + Utils.tmpDir;
        String decompiledDir = projectPath + Utils.decompiledRoot;
        String jadxOutput = projectPath + Utils.jadxOutput;
        String procyonOutput = projectPath + Utils.procyonOutput;
        String fernFlowerOutput = projectPath + Utils.fernflowerOutput;

        new File(tmpDir).mkdirs();
        new File(decompiledDir).mkdirs();
        new File(jadxOutput).mkdirs();
        new File(procyonOutput).mkdirs();
        new File(fernFlowerOutput).mkdirs();
    }

    public void fetchArguments(String jadxArgs, String procyonArgs, String fernFlowerArgs) {
        try {
            jadxArgs = ds.getJadxArgs();
        } catch (Exception e) {}
        try {
            procyonArgs = ds.getProcyonArgs();
        } catch (Exception e) {}

        try {
            fernFlowerArgs = ds.getFernFlowerArgs();
        } catch (Exception e) {}
    }
}
