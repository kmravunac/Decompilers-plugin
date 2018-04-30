package hr.fer.decompiler.plugin.wizard;

import com.intellij.ui.wizard.WizardDialog;
import com.intellij.ui.wizard.WizardModel;

public class DecompilerWizardDialog extends WizardDialog {
    public DecompilerWizardDialog(boolean canBeParent, WizardModel model) {
        super(canBeParent, model);
    }
}
