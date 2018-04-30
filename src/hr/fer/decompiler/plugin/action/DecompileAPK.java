package hr.fer.decompiler.plugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import hr.fer.decompiler.util.wrapper.JadxWrapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DecompileAPK extends AnAction {
    public DecompileAPK() {
        super("Decompile _APK");
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);

        VirtualFile apk = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor(), project, project.getBaseDir());
        File apkFile = new File(apk.getCanonicalPath());

        String outputDirName = project.getBasePath() + "/" + "app/src/main/java";
        File manifestDir = new File(project.getBasePath() + "/" + "app/src/main/AndroidManifest.xml");

        File outputDir = new File(outputDirName);

        JadxWrapper wrapper = new JadxWrapper(apkFile, outputDir);

        if(!outputDir.exists()) {
            outputDir.mkdir();
        }

        wrapper.decompile();

        File manifest = null;
        File[] files = outputDir.listFiles();

        for(File f : files) {
            if(f.getName().equals("AndroidManifest.xml"))
                manifest = f;
        }

        if(manifest != null) {
            try {
                Files.move(Paths.get(manifest.getAbsolutePath()), Paths.get(manifestDir.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
