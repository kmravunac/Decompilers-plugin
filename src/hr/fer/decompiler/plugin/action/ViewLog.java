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

public class ViewLog extends AnAction {
    public ViewLog() {
        super("View log");
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        VirtualFile selectedFile = event.getData(DataKeys.VIRTUAL_FILE);
        String selectedFilePath = selectedFile.getCanonicalPath();

        String suffix = "";

        if(selectedFilePath.contains(Utils.jadxOutput)) {
            suffix = Utils.jadxLog;
        } else if(selectedFilePath.contains(Utils.fernflowerOutput)) {
            suffix = Utils.fernflowerLog;
        } else if(selectedFilePath.contains(Utils.procyonOutput)) {
            suffix = Utils.procyonLog;
        }

        if(!suffix.isEmpty()) {
            String filePath = project.getBasePath() + suffix;
            VirtualFile file = LocalFileSystem.getInstance().findFileByPath(filePath);

            if(file != null)
                FileEditorManager.getInstance(project).openFile(file, true);
        }
    }

    @Override
    public void update(AnActionEvent event) {
        VirtualFile selectedFile = event.getData(DataKeys.VIRTUAL_FILE);
        Presentation presentation = event.getPresentation();

        if(selectedFile != null) {
            String filePath = selectedFile.getCanonicalPath();
            boolean enable = filePath.contains(Utils.fernflowerOutput) || filePath.contains(Utils.procyonOutput) || filePath.contains(Utils.jadxOutput);

            if (!enable)
                presentation.setEnabled(false);
        } else {
            presentation.setEnabled(false);
        }
    }
}
