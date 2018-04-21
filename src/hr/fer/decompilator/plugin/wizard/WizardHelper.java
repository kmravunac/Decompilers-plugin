package hr.fer.decompilator.plugin.wizard;

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
        return ds.fernFlowerSelected();
    }
}
