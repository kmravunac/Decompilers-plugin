package hr.fer.decompiler.plugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import hr.fer.decompiler.plugin.wizard.WizardHelper;
import hr.fer.decompiler.util.utility.Utils;
import hr.fer.decompiler.util.utility.ZipUtility;
import hr.fer.decompiler.util.wrapper.Dex2JarWrapper;
import hr.fer.decompiler.util.wrapper.FernFlowerWrapper;
import hr.fer.decompiler.util.wrapper.JadxWrapper;
import hr.fer.decompiler.util.wrapper.ProcyonWrapper;

import javax.swing.*;
import java.io.File;

public class ImportAPK extends AnAction {

    public ImportAPK() {
        super("Import _APK");
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        int option = JOptionPane.showConfirmDialog(null, "This action will wipe your current project (source code and other files), would you like to proceed?", "Warning", JOptionPane.YES_NO_OPTION);
        if(option == JOptionPane.YES_OPTION) {
            Project project = event.getData(PlatformDataKeys.PROJECT);
            String projectDir = project.getBasePath();

            WizardHelper wizard = new WizardHelper();

            wizard.doCleanup(projectDir);
            wizard.prepareDirectories(projectDir);

            wizard.run();

            File apkFile = wizard.getApkFile();

            String jadxArgs = null;
            String procyonArgs = null;
            String fernFlowerArgs = null;

            wizard.fetchArguments(jadxArgs, procyonArgs, fernFlowerArgs);

            ZipUtility.unzip(apkFile.getAbsolutePath(), projectDir + Utils.tmpDir);

            String dexFile = projectDir + Utils.tmpDir + Utils.dexFile;
            String outJarFile = projectDir + Utils.tmpDir + Utils.jarFile;
            Dex2JarWrapper d2j = new Dex2JarWrapper(dexFile, outJarFile);
            d2j.dex2jar();

            String jadxFailed = null;
            String procyonFailed = null;
            String fernFlowerFailed = null;

            if (wizard.jadxSelected()) {
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

            if(wizard.procyonSelected()) {
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

            if(wizard.fernFlowerSelected()) {
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

            String message = "";
            if(jadxFailed != null)
                message += jadxFailed + "\n";
            if(procyonFailed != null)
                message += procyonFailed;
            if(fernFlowerFailed != null)
                message += fernFlowerFailed;

            if(!message.isEmpty())
                JOptionPane.showMessageDialog(null, "There were some errors during decompilation:\n\n" + message, "Error", JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, "Decompilation was succesfull", "Success", JOptionPane.INFORMATION_MESSAGE);

            project.getBaseDir().refresh(false, true);
        }
    }
}
