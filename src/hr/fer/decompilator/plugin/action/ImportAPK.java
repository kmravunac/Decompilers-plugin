package hr.fer.decompilator.plugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import hr.fer.decompilator.plugin.wizard.WizardHelper;

import javax.swing.*;
import java.io.File;

public class ImportAPK extends AnAction {

    public ImportAPK() {
        super("Import _APK");
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        int option = JOptionPane.showConfirmDialog(null, "This action will wipe your current project (source code and other files), would you like to proceed?", "Warning", JOptionPane.YES_NO_OPTION);
        if(option == JOptionPane.YES_OPTION) {
            WizardHelper wizard = new WizardHelper();
            wizard.run();

            File apkFile = wizard.getApkFile();
            String jadxArgs = wizard.getJadxArgs();
            String procyonArgs = wizard.getProcyonArgs();
            String fernFlowerArgs = wizard.getFernFlowerArgs();
        }
    }
}
