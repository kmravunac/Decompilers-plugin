package hr.fer.decompiler.plugin.action;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import hr.fer.decompiler.util.utility.Utils;
import hr.fer.decompiler.util.utility.ZipUtility;
import hr.fer.decompiler.util.wrapper.Dex2JarWrapper;
import hr.fer.decompiler.util.wrapper.FernFlowerWrapper;
import hr.fer.decompiler.util.wrapper.JadxWrapper;
import hr.fer.decompiler.util.wrapper.ProcyonWrapper;

import javax.swing.*;
import java.io.*;

public class DecompileAPK extends AnAction {
    public DecompileAPK() {
        super("Decompile _APK");
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);

        boolean isDefault = DefineSettings.settings.getIsDefault();
        int settingsStatus = JOptionPane.YES_OPTION;
        int decompiledStatus = JOptionPane.YES_OPTION;

        String decompiledDirPath = project.getBasePath() + Utils.decompiledRoot;
        String backupDir = project.getBasePath() + Utils.backupDir;
        File decompiledDir = new File(decompiledDirPath);

        if(isDefault) {
            settingsStatus = JOptionPane.showConfirmDialog(null, "Using default decompilers settings (all decompilers selected, no arguments), are you sure you want to proceed?", "Warning", JOptionPane.YES_NO_OPTION);
        }

        boolean cleanup = decompiledDir.exists() && (decompiledDir.list().length > 0);

        if(settingsStatus == JOptionPane.YES_OPTION) {
            if (cleanup) {
                decompiledStatus = JOptionPane.showConfirmDialog(null, "It appears that there are some existing decompiled sources, would you like to delete them and proceed with decompilation?", "Warning", JOptionPane.YES_NO_OPTION);
            }
        }

        if(settingsStatus == JOptionPane.YES_OPTION && decompiledStatus == JOptionPane.YES_OPTION) {
            ProgressManager.getInstance().run(new Task.Backgroundable(project, "Decompilation") {
                public void run(ProgressIndicator indicator) {
                    boolean error = false;
                    String message = "";

                    indicator.setFraction(0.01);

                    if(cleanup) {
                        indicator.setText("Removing old decompiled sources...");
                        indicator.setFraction(0.02);

                        try {
                            Utils.cleanupDirectory(decompiledDirPath);
                            Utils.cleanupDirectory(backupDir);
                        } catch (IOException e) {
                            error = true;
                            message += e.toString() + "\n";
                        }
                    }

                    if(!message.isEmpty())
                        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.INFORMATION_MESSAGE);

                    if(!error) {

                        indicator.setText("Decompilation in progress: preparing files...");
                        indicator.setFraction(0.03);

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

                        PrintStream console = System.out;

                        indicator.setFraction(0.25);

                        if (DefineSettings.settings.isJadxSelected()) {
                            indicator.setText("Decompilation in progress: running Jadx...");
                            JadxWrapper jadx;

                            File jadxLog = new File(projectDir + Utils.jadxLog);
                            try {
                                jadxLog.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            PrintStream ps = null;
                            try {
                                ps = new PrintStream(new FileOutputStream(jadxLog));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            System.setOut(ps);

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

                            System.setOut(console);
                        }

                        indicator.setFraction(0.5);

                        if (DefineSettings.settings.isProcyonSelected()) {
                            indicator.setText("Decompilation in progress: running Procyon...");
                            String procyonOut = new String(projectDir + Utils.procyonOutput);

                            File procyonLog = new File(projectDir + Utils.procyonLog);
                            try {
                                procyonLog.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            PrintStream ps = null;
                            try {
                                ps = new PrintStream(new FileOutputStream(procyonLog));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            System.setOut(ps);

                            ProcyonWrapper procyon = new ProcyonWrapper(procyonOut, outJarFile);
                            try {
                                if (procyonArgs == null)
                                    procyon.decompile();
                                else {
                                    String args[] = procyonArgs.trim().split("\\s+");
                                    procyon.decompile(args);
                                }
                            } catch (Exception e) {
                                procyonFailed = e.toString();
                            }

                            System.setOut(console);
                        }

                        indicator.setFraction(0.75);

                        if (DefineSettings.settings.isFernFlowerSelected()) {
                            indicator.setText("Decompilation in progress: running FernFlower...");
                            String fernFlowerOut = new String(projectDir + Utils.fernflowerOutput);

                            File fernflowerLog = new File(projectDir + Utils.fernflowerLog);
                            try {
                                fernflowerLog.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            PrintStream ps = null;
                            try {
                                ps = new PrintStream(new FileOutputStream(fernflowerLog));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            System.setOut(ps);

                            FernFlowerWrapper fernFlower = new FernFlowerWrapper(fernFlowerOut, outJarFile);
                            try {
                                if (fernFlowerArgs == null)
                                    fernFlower.decompile();
                                else {
                                    String[] args = fernFlowerArgs.trim().split("\\s+");
                                    fernFlower.decompile(args);
                                }
                            } catch (Exception e) {
                                fernFlowerFailed = e.toString();
                            }

                            System.setOut(console);
                        }

                        indicator.setFraction(0.9);
                        indicator.setText("Backuping smali output...");

                        Utils.copyDirectory(projectDir + Utils.smaliCodeLocation, projectDir + Utils.smaliDir);

                        indicator.setText("Refreshing project directory...");
                        indicator.setFraction(0.99);

                        if (jadxFailed != null)
                            message += jadxFailed + "\n";
                        if (procyonFailed != null)
                            message += procyonFailed + "\n";
                        if (fernFlowerFailed != null)
                            message += fernFlowerFailed + "\n";

                        if (!message.isEmpty())
                            JOptionPane.showMessageDialog(null, "There were some errors during decompilation:\n\n" + message, "Informational message", JOptionPane.ERROR_MESSAGE);
                        else
                            JOptionPane.showMessageDialog(null, "Decompilation was succesful.", "Decompiler", JOptionPane.INFORMATION_MESSAGE);

                        project.getBaseDir().refresh(false, true);
                    }
                }
            });
        }
    }

    @Override
    public void update(AnActionEvent event) {
        Presentation presentation = event.getPresentation();
        Project project = event.getData(PlatformDataKeys.PROJECT);
        VirtualFile selectedFile = event.getData(DataKeys.VIRTUAL_FILE);

        if(selectedFile != null) {
            File apk = Utils.fetchApk(project.getBasePath());

            if (apk == null)
                presentation.setEnabled(false);
            else if (!selectedFile.getName().contains(".apk")) {
                presentation.setEnabledAndVisible(false);
            } else {
                presentation.setEnabled(true);
            }
        } else {
            presentation.setEnabled(false);
        }
    }
}
