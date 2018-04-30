package hr.fer.decompiler.plugin.wizard;

import com.google.common.io.Files;
import com.intellij.ui.wizard.WizardNavigationState;
import com.intellij.ui.wizard.WizardStep;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileSelectionStep extends WizardStep {
    private JPanel panel;
    private JFileChooser fc;
    private File selectedFile;
    private WizardNavigationState state;

    public FileSelectionStep(String title) {
        super(title);
        panel = new JPanel();
        fc = new JFileChooser();
        fc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableNext();

                selectedFile = fc.getSelectedFile();
            }
        });
        panel.add(fc);
    }

    @Override
    public JComponent prepare(WizardNavigationState wizardNavigationState) {
        wizardNavigationState.NEXT.setEnabled(false);

        return panel;
    }

    public File getFile() {
        return selectedFile;
    }

    public void setState(WizardNavigationState state) {
        this.state = state;
    }

    private void enableNext() {
        state.NEXT.setEnabled(false);
        if(this.fc.getSelectedFile() != null)
            if(Files.getFileExtension(fc.getSelectedFile().getAbsolutePath()).equals("apk")) {
                state.NEXT.setEnabled(true);
            }
    }
}
