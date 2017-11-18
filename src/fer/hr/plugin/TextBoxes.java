package fer.hr.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.actionSystem.PlatformDataKeys;

public class TextBoxes extends AnAction{
    public TextBoxes() {
        super("Text _Boxes");
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
        String txt = Messages.showInputDialog(project, "Enter some text", "Input some text", Messages.getQuestionIcon());
        Messages.showMessageDialog(project, "You have entered: " + txt + "\n", "Information", Messages.getInformationIcon());
    }
}
