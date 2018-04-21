package hr.fer.decompilator.plugin.wizard;

import com.intellij.ui.wizard.WizardDialog;
import com.intellij.ui.wizard.WizardModel;

public class DecompilatorWizardDialog extends WizardDialog {
    public DecompilatorWizardDialog(boolean canBeParent, WizardModel model) {
        super(canBeParent, model);
    }
}
