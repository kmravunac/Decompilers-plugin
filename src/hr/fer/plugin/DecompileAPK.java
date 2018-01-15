package hr.fer.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import hr.fer.jadxwrapper.JadxDecWrapper;

import java.io.File;

public class DecompileAPK extends AnAction {
    public DecompileAPK() {
        super("Decompile _APK");
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);

        VirtualFile apk = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor(), project, project.getBaseDir());
        File apkFile = new File(apk.getCanonicalPath());

        String outputDirName = project.getBasePath() + "/" + "plugin-jadx-output";
        File outputDir = new File(outputDirName);

        JadxDecWrapper wrapper = new JadxDecWrapper(apkFile, outputDir);

        if(!outputDir.exists()) {
            outputDir.mkdir();
        }

        wrapper.decompile();
    }
}
