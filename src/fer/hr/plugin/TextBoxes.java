package fer.hr.plugin;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.module.EmptyModuleType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ui.configuration.ModulesConfigurator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import hr.fer.jadxwrapper.JadxDecWrapper;

import java.io.File;

public class TextBoxes extends AnAction {
    public TextBoxes() {
        super("Text _Boxes");
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
        String apkFile = Messages.showInputDialog(project, "Enter the apk file location", "Input apk file location", Messages.getQuestionIcon());
        String outputDirName = project.getBasePath() + "/" + "plugin-jadx-output";
        //JadxDecWrapper wrapper = new JadxDecWrapper(apkFile, outputDirName, null);

        Module[] modules = ModuleManager.getInstance(project).getModules();
        Module outDir = null;

        for(Module module: modules) {
            if(module.getName().equalsIgnoreCase(outputDirName)) {
                outDir = module;
            }
        }

        if(outDir == null) {
            outDir = ModuleManager.getInstance(project).newModule(outputDirName, EmptyModuleType.EMPTY_MODULE);
        }
    }
}
