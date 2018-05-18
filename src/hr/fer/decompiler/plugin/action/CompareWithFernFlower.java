package hr.fer.decompiler.plugin.action;

import com.intellij.diff.DiffManager;
import com.intellij.diff.DiffRequestFactoryImpl;
import com.intellij.diff.requests.DiffRequest;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import hr.fer.decompiler.util.utility.Utils;

public class CompareWithFernFlower extends AnAction {
    public CompareWithFernFlower() {
        super("FernFlower source");
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        VirtualFile firstFile = event.getData(DataKeys.VIRTUAL_FILE);

        String fileSuffix = Utils.preparePath(firstFile.getCanonicalPath(), project.getBasePath());
        String filePath = project.getBasePath() + "/" + Utils.fernflowerOutput + "/" + fileSuffix;

        VirtualFile secondFile = LocalFileSystem.getInstance().findFileByPath(filePath);

        DiffRequestFactoryImpl factory = new DiffRequestFactoryImpl();

        DiffRequest diffRequest = factory.createFromFiles(project, firstFile, secondFile);

        DiffManager.getInstance().showDiff(project, diffRequest);
    }

    @Override
    public void update(AnActionEvent event) {
        VirtualFile selectedFile = event.getData(DataKeys.VIRTUAL_FILE);
        Presentation presentation = event.getPresentation();

        if(selectedFile != null) {
            String filePath = selectedFile.getCanonicalPath();
            boolean enable = !filePath.contains(Utils.fernflowerOutput) && filePath.contains(".java") &&
                    (filePath.contains(Utils.jadxOutput) || filePath.contains(Utils.procyonOutput));

            if (!enable)
                presentation.setEnabled(false);
        } else {
            presentation.setEnabled(false);
        }
    }
}
