package hr.fer.decompiler.plugin.action;

import com.intellij.diff.DiffManager;
import com.intellij.diff.DiffRequestFactoryImpl;
import com.intellij.diff.contents.DiffContent;
import com.intellij.diff.contents.FileContentImpl;
import com.intellij.diff.requests.DiffRequest;
import com.intellij.diff.requests.SimpleDiffRequest;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import hr.fer.decompiler.util.utility.Utils;

public class CompareWithJadx extends AnAction {
    public CompareWithJadx() {
        super("Jadx source");
    }
    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        VirtualFile firstFile = event.getData(DataKeys.VIRTUAL_FILE);

        String fileSuffix = Utils.preparePath(firstFile.getCanonicalPath(), project.getBasePath());
        String filePath = project.getBasePath() + "/" + Utils.jadxOutput + "/" + fileSuffix;

        VirtualFile secondFile = LocalFileSystem.getInstance().findFileByPath(filePath);

        DiffRequestFactoryImpl factory = new DiffRequestFactoryImpl();

        DiffRequest diffRequest = factory.createFromFiles(project, firstFile, secondFile);

        DiffManager.getInstance().showDiff(project, diffRequest);
    }

    @Override
    public void update(AnActionEvent event) {
        VirtualFile selectedFile = event.getData(DataKeys.VIRTUAL_FILE);
        Presentation presentation = event.getPresentation();

        String filePath = selectedFile.getCanonicalPath();
        boolean enable = !filePath.contains(Utils.jadxOutput) && filePath.contains(".java") &&
                (filePath.contains(Utils.fernflowerOutput) || filePath.contains(Utils.procyonOutput));

        if(selectedFile == null || !enable)
            presentation.setEnabled(false);
    }
}
