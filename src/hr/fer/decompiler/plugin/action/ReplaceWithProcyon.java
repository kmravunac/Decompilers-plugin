package hr.fer.decompiler.plugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import hr.fer.decompiler.util.utility.Utils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ReplaceWithProcyon extends AnAction {
    public ReplaceWithProcyon() {
        super("Procyon source");
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();

        VirtualFile selectedFile = event.getData(DataKeys.VIRTUAL_FILE);

        if(selectedFile != null) {
            ProgressManager.getInstance().run(new Task.Backgroundable(project, "Copying files") {
                public void run(ProgressIndicator indicator) {
                    String projectPath = project.getBasePath();
                    String originalFilePath = selectedFile.getCanonicalPath();

                    String filePath = Utils.preparePath(selectedFile.getCanonicalPath(), projectPath);
                    String[] splitPath = filePath.split("/");
                    String file = splitPath[splitPath.length - 1];

                    String directorySuffix = Utils.determineDirectorySuffix(originalFilePath);
                    String backupPath = projectPath + directorySuffix;

                    indicator.setFraction(0.5);
                    indicator.setText("Copying files...");

                    if (!selectedFile.isDirectory()) {
                        if (!Utils.hasBackup(file, backupPath)) {
                            Utils.backupFile(file, backupPath, originalFilePath);
                        }

                        String fileToBeCopied = projectPath + Utils.procyonOutput + "/" + filePath;

                        try {
                            Files.copy(Paths.get(fileToBeCopied), Paths.get(originalFilePath), StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null, "Something went wrong when copying files: " + e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
                        }

                        project.getBaseDir().refresh(false, true);

                        VirtualFile newFile = LocalFileSystem.getInstance().findFileByPath(originalFilePath);
                        FileEditorManager.getInstance(project).openFile(newFile, true);
                    } else {
                        if(file.isEmpty()) {
                            file = Utils.determineDirectoryPath(originalFilePath);
                        }

                        String backupDirPath = backupPath + "/" + file;

                        if (!Utils.hasBackup(file, backupPath)) {
                            new File(backupDirPath).mkdirs();
                            Utils.copyDirectory(originalFilePath, backupDirPath);
                        }

                        String dirToBeCopied = projectPath + Utils.procyonOutput + "/" + filePath;

                        Utils.copyDirectory(dirToBeCopied, originalFilePath);
                    }
                }
            });
        }
    }

    @Override
    public void update(AnActionEvent event) {
        Presentation presentation = event.getPresentation();
        VirtualFile selectedFile = event.getData(DataKeys.VIRTUAL_FILE);

        String filePath = selectedFile.getCanonicalPath();

        boolean enable = (filePath.contains(".java") || selectedFile.isDirectory()) &&
                (filePath.contains(Utils.fernflowerOutput) || filePath.contains(Utils.jadxOutput) || filePath.contains(Utils.smaliCodeLocation));

        if(selectedFile == null || !enable)
            presentation.setEnabled(false);
    }
}
