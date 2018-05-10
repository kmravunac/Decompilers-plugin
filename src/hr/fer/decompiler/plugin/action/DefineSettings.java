package hr.fer.decompiler.plugin.action;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import hr.fer.decompiler.plugin.settings.DecompilerSettings;
import hr.fer.decompiler.plugin.settings.SettingsForm;
import hr.fer.decompiler.util.utility.Utils;

import javax.swing.*;
import java.io.File;


public class DefineSettings extends AnAction {
    public static DecompilerSettings settings;

    public DefineSettings() {
        super("Define _Settings");
        settings = new DecompilerSettings(true, true, true, null, null, null, true);
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);

        SettingsForm settingsForm = new SettingsForm("Define decompilers and arguments", project);
        settingsForm.show();

        String jadxArgs = null;
        String procyonArgs = null;
        String fernFlowerArgs = null;

        try {
            jadxArgs = settingsForm.getJadxArgs();
        } catch(Exception e) {};

        try {
            procyonArgs = settingsForm.getProcyonArgs();
        } catch(Exception e) {};

        try {
            fernFlowerArgs = settingsForm.getFernFlowerArgs();
        } catch(Exception e) {};

        settings = new DecompilerSettings(settingsForm.jadxSelected(), settingsForm.procyonSelected(), settingsForm.fernFlowerSelected(), jadxArgs, procyonArgs, fernFlowerArgs, false);
    }

    @Override
    public void update(AnActionEvent event) {
        Presentation presentation = event.getPresentation();
        Project project = event.getData(PlatformDataKeys.PROJECT);

        File apk = Utils.fetchApk(project.getBaseDir().getCanonicalPath());

        if(apk == null)
            presentation.setEnabled(false);
        else
            presentation.setEnabled(true);
    }
}
