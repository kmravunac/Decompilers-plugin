package hr.fer.decompilator.plugin.wizard;

import com.intellij.ui.wizard.WizardNavigationState;
import com.intellij.ui.wizard.WizardStep;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectDecompilersStep extends WizardStep {
    private JPanel decompilersPanel;
    private JCheckBox jadxCheckBox;
    private JTextField jadxArgumentsField;
    private JCheckBox procyonCheckBox;
    private JTextField procyonArgumentsField;
    private JCheckBox fernFlowerCheckBox;
    private JTextField fernFlowerArgumentsField;

    private WizardNavigationState state;

    public SelectDecompilersStep(String title) {
        super(title);

        jadxArgumentsField.setEnabled(false);
        procyonArgumentsField.setEnabled(false);
        fernFlowerArgumentsField.setEnabled(false);

        jadxCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableFinish();
                jadxArgumentsField.setEnabled(jadxCheckBox.isSelected());
            }
        });

        procyonCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableFinish();
                procyonArgumentsField.setEnabled(procyonCheckBox.isSelected());
            }
        });

        fernFlowerCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableFinish();
                fernFlowerArgumentsField.setEnabled(fernFlowerCheckBox.isSelected());
            }
        });
    }

    @Override
    public JComponent prepare(WizardNavigationState wizardNavigationState) {
        enableFinish();

        return decompilersPanel;
    }

    public String getJadxArgs() {
        return jadxArgumentsField.getText();
    }

    public String getProcyonArgs() {
        return procyonArgumentsField.getText();
    }

    public String getFernFlowerArgs() {
        return fernFlowerArgumentsField.getText();
    }

    public boolean jadxSelected() {
        return jadxCheckBox.isSelected();
    }

    public boolean procyonSelected() {
        return procyonCheckBox.isSelected();
    }

    public boolean fernFlowerSelected() {
        return fernFlowerCheckBox.isSelected();
    }

    public void setState(WizardNavigationState state) {
        this.state = state;
    }

    private void enableFinish() {
        boolean enable = jadxCheckBox.isSelected() || procyonCheckBox.isSelected() || fernFlowerCheckBox.isSelected();

        state.FINISH.setEnabled(enable);
    }
}
