package hr.fer.decompiler.plugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import hr.fer.decompiler.plugin.settings.DecompilerSettings;
import hr.fer.decompiler.plugin.settings.SettingsForm;


public class DefineSettings extends AnAction {
    public DefineSettings() {
        super("Configure _Settings");
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

        DecompilerSettings settings = new DecompilerSettings(settingsForm.jadxSelected(), settingsForm.procyonSelected(), settingsForm.fernFlowerSelected(), jadxArgs, procyonArgs, fernFlowerArgs);

        System.out.println(settings.isJadxSelected() + " " + settings.isProcyonSelected() + " " + settings.isFernFlowerSelected());
        System.out.println("jadx: " + settings.getJadxArgs());
        System.out.println("procyon: " + settings.getProcyonArgs());
        System.out.println("fernflower: " + settings.getFernFlowerArgs());
    }
}
