package hr.fer.decompilator.plugin.wizard;

import hr.fer.decompilator.util.utility.Utils;

import javax.swing.*;
import java.io.File;

public class WizardHelper {
    private DecompilatorWizardModel model;
    private FileSelectionStep fs;
    private SelectDecompilersStep ds;

    public WizardHelper() {
        model = new DecompilatorWizardModel("Decompilator");
        fs = new FileSelectionStep("Select APK file");
        ds = new SelectDecompilersStep("Choose decompilers");

        model.add(fs);
        model.add(ds);

        fs.setState(model.getCurrentNavigationState());
        ds.setState(model.getCurrentNavigationState());
    }

    public boolean run() {
        try {
            DecompilatorWizardDialog dialog = new DecompilatorWizardDialog(false, model);
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
        String javadir = projectPath + File.separator + Utils.javaDir;
        String resDir = projectPath + File.separator + Utils.resDir;
        String manifest = projectPath + File.separator + Utils.manifestDir + File.separator + "AndroidManifest.xml";

        try {
            Utils.cleanupDirectory(javadir);
            Utils.cleanupDirectory(resDir);
            File man = new File(manifest);
            man.delete();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
