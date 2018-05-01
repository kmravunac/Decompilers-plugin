package hr.fer.decompiler.plugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import hr.fer.decompiler.util.utility.Utils;
import hr.fer.decompiler.util.utility.ZipUtility;
import hr.fer.decompiler.util.wrapper.Dex2JarWrapper;
import hr.fer.decompiler.util.wrapper.FernFlowerWrapper;
import hr.fer.decompiler.util.wrapper.JadxWrapper;
import hr.fer.decompiler.util.wrapper.ProcyonWrapper;

import javax.swing.*;
import java.io.File;

public class DecompileAPK extends AnAction {
    public DecompileAPK() {
        super("Decompile _APK");
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);

        if(DefineSettings.settings == null || !DefineSettings.settings.isAnySelected()) {
            JOptionPane.showMessageDialog(null, "Settings are not initialized, please initialize decompilers settings by using Define decompiler settings option.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            ProgressManager.getInstance().run(new Task.Backgroundable(project, "Decompilation"){
                public void run(ProgressIndicator indicator) {
                    indicator.setText("Decompilation in progres: preparing files...");
                    indicator.setFraction(0.01);

                    String projectDir = project.getBasePath();

                    Utils.prepareDirectories(projectDir);

                    File apkFile = Utils.fetchApk(projectDir);

                    String jadxArgs = DefineSettings.settings.getJadxArgs();
                    String procyonArgs = DefineSettings.settings.getProcyonArgs();
                    String fernFlowerArgs = DefineSettings.settings.getFernFlowerArgs();

                    ZipUtility.unzip(apkFile.getAbsolutePath(), projectDir + Utils.tmpDir);

                    String dexFile = projectDir + Utils.tmpDir + Utils.dexFile;
                    String outJarFile = projectDir + Utils.tmpDir + Utils.jarFile;
                    Dex2JarWrapper d2j = new Dex2JarWrapper(dexFile, outJarFile);
                    d2j.dex2jar();

                    String jadxFailed = null;
                    String procyonFailed = null;
                    String fernFlowerFailed = null;

                    indicator.setFraction(0.25);

                    if (DefineSettings.settings.isJadxSelected()) {
                        indicator.setText("Decompilation in progres: running Jadx...");
                        JadxWrapper jadx;

                        File jadxout = new File(projectDir + Utils.jadxOutput);

                        if (jadxArgs == null)
                            jadx = new JadxWrapper(apkFile, jadxout);
                        else
                            jadx = new JadxWrapper(apkFile, jadxout, jadxArgs);

                        try {
                            jadx.decompile();
                        } catch (Exception e) {
                            jadxFailed = e.toString();
                        }
                    }

                    indicator.setFraction(0.5);

                    if(DefineSettings.settings.isProcyonSelected()) {
                        indicator.setText("Decompilation in progres: running Procyon...");
                        String procyonOut = new String(projectDir + Utils.procyonOutput);

                        ProcyonWrapper procyon = new ProcyonWrapper(procyonOut, outJarFile);
                        try {
                            if(procyonArgs == null)
                                procyon.decompile();
                            else {
                                String args[] = procyonArgs.trim().split("\\s+");
                                procyon.decompile(args);
                            }
                        } catch(Exception e) {
                            procyonFailed = e.toString();
                        }
                    }

                    indicator.setFraction(0.75);

                    if(DefineSettings.settings.isFernFlowerSelected()) {
                        indicator.setText("Decompilation in progres: running FernFlower...");
                        String fernFlowerOut = new String(projectDir + Utils.fernflowerOutput);

                        FernFlowerWrapper fernFlower = new FernFlowerWrapper(fernFlowerOut, outJarFile);
                        try {
                            if(fernFlowerArgs == null)
                                fernFlower.decompile();
                            else {
                                String[] args = fernFlowerArgs.trim().split("\\s+");
                                fernFlower.decompile(args);
                            }
                        } catch (Exception e) {
                            fernFlowerFailed = e.toString();
                        }
                    }

                    indicator.setText("Decompilation in progress, refreshing project directory...");
                    indicator.setFraction(0.99);

                    String message = "";
                    if(jadxFailed != null)
                        message += jadxFailed + "\n";
                    if(procyonFailed != null)
                        message += procyonFailed + "\n";
                    if(fernFlowerFailed != null)
                        message += fernFlowerFailed + "\n";

                    if(!message.isEmpty())
                        JOptionPane.showMessageDialog(null, "There were some errors during decompilation:\n\n" + message, "Informational message", JOptionPane.ERROR_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "Decompilation was succesful.", "Informational message", JOptionPane.INFORMATION_MESSAGE);

                    project.getBaseDir().refresh(false, true);
                }
            });
        }
    }

    @Override
    public void update(AnActionEvent event) {
        Presentation presentation = event.getPresentation();
        Project project = event.getData(PlatformDataKeys.PROJECT);

        File apk = Utils.fetchApk(project.getBaseDir().getCanonicalPath());

        if(apk == null)
            presentation.setEnabled(false);
    }
}
