package hr.fer.decompiler.plugin.action;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import hr.fer.decompiler.util.utility.Utils;


public class ViewJadxSource extends AnAction {

    public ViewJadxSource() {
        super("View Jadx source");
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();

        VirtualFile selectedFile = event.getData(DataKeys.VIRTUAL_FILE);

        if(selectedFile != null) {
            String fileSuffix = Utils.preparePath(selectedFile.getCanonicalPath(), project.getBasePath());
            String filePath = project.getBasePath() + "/" + Utils.jadxOutput + "/" + fileSuffix;

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
            boolean enable = !filePath.contains(Utils.jadxOutput) && filePath.contains(".java") &&
                    (filePath.contains(Utils.fernflowerOutput) || filePath.contains(Utils.procyonOutput) || filePath.contains(Utils.smaliDir) || filePath.contains(Utils.smaliCodeLocation));

            if (!enable)
                presentation.setEnabled(false);
        } else {
            presentation.setEnabled(false);
        }
    }
}
