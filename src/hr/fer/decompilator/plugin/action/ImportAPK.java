package hr.fer.decompilator.plugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import hr.fer.decompilator.plugin.wizard.WizardHelper;

import javax.swing.*;
import java.io.File;

public class ImportAPK extends AnAction {

    public ImportAPK() {
        super("Import _APK");
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        int option = JOptionPane.showConfirmDialog(null, "This action will wipe your current project (source code and other files), would you like to proceed?", "Warning", JOptionPane.YES_NO_OPTION);
        if(option == JOptionPane.YES_OPTION)  {
            Project project = event.getData(PlatformDataKeys.PROJECT);
            String projectDir = project.getBasePath();

            WizardHelper wizard = new WizardHelper();

            wizard.doCleanup(projectDir);

            wizard.run();

            //File apkFile = wizard.getApkFile();
            //String jadxArgs = wizard.getJadxArgs();
            //String procyonArgs = wizard.getProcyonArgs();
            //String fernFlowerArgs = wizard.getFernFlowerArgs();
        }
    }
}
