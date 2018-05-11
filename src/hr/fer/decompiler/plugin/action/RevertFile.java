package hr.fer.decompiler.plugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.fileEditor.FileEditorManager;
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

public class RevertFile extends AnAction {
    public RevertFile() {
        super("Revert original file");
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        VirtualFile selectedFile = event.getData(DataKeys.VIRTUAL_FILE);
        Project project = event.getProject();

        String selectedFilePath = selectedFile.getCanonicalPath();
        String backupSuffix = Utils.determineBackupSuffix(selectedFilePath);
        String backupDirectory = Utils.determineDirectorySuffix(selectedFilePath);

        String filePath = Utils.preparePath(selectedFilePath, project.getBasePath());
        String[] splitPath = filePath.split("/");
        String file = splitPath[splitPath.length - 1];

        String backupFile = project.getBasePath() + backupDirectory + "/" + file + backupSuffix;

        try {
            Files.copy(Paths.get(backupFile), Paths.get(selectedFilePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Something went wrong when copying files: " + e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }

        File fileToDelete = new File(backupFile);
        fileToDelete.delete();

        project.getBaseDir().refresh(false, true);

        VirtualFile newFile = LocalFileSystem.getInstance().findFileByPath(selectedFilePath);
        FileEditorManager.getInstance(project).openFile(newFile, true);
    }

    @Override
    public void update(AnActionEvent event) {
        Presentation presentation = event.getPresentation();
        VirtualFile selectedFile = event.getData(DataKeys.VIRTUAL_FILE);
        Project project = event.getProject();

        String selectedFilePath = selectedFile.getCanonicalPath();
        String backupSuffix = Utils.determineBackupSuffix(selectedFilePath);
        String backupDirectory = Utils.determineDirectorySuffix(selectedFilePath);

        String filePath = Utils.preparePath(selectedFilePath, project.getBasePath());
        String[] splitPath = filePath.split("/");
        String file = splitPath[splitPath.length - 1];

        String backupFile = project.getBasePath() + backupDirectory + "/" + file + backupSuffix;

        boolean enable = selectedFilePath.contains(".java") &&
                (selectedFilePath.contains(Utils.jadxOutput) || selectedFilePath.contains(Utils.fernflowerOutput) ||
                        selectedFilePath.contains(Utils.procyonOutput) || selectedFilePath.contains(Utils.smaliCodeLocation));

        if(!Files.exists(Paths.get(backupFile)) || !enable) {
            presentation.setEnabledAndVisible(false);
        }
    }
}
