package hr.fer.decompiler.plugin.settings;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsForm extends DialogWrapper {
    private JPanel decompilersPanel;
    private JCheckBox jadxCheckBox;
    private JTextField jadxArgumentsField;
    private JCheckBox procyonCheckBox;
    private JTextField procyonArgumentsField;
    private JCheckBox fernFlowerCheckBox;
    private JTextField fernFlowerArgumentsField;

    public SettingsForm(String title, Project project) {
        super(project);
        init();
        setTitle(title);
        decompilersPanel.setSize(new Dimension(400, 400));
        decompilersPanel.setMaximumSize(new Dimension(400, 400));
        decompilersPanel.setMinimumSize(new Dimension(400, 400));
        setSize(400, 400);

        jadxArgumentsField.setToolTipText("-j/--threads-count -r/--no-res -s/--no-src -e/--export-gradle --show-bad-code --no-imports" +
                " --no-replace-consts --escape-unicode --deobf --deobf-min --deobf-max --deobf-rewrite-cfg --deobf-use-sourcename --cfg" +
                " --raw-cfg -f/--fallback");

        procyonArgumentsField.setToolTipText("-mv/--merge-variables -ei/--explicit-imports -eta/--explicit-type-arguments -ec/--retain-explicit-casts" +
                " -fsb/--flatten-switch-blocks -ss/show-synthetic -b/--bytecode-ast -r/--raw-bytecode -u/--unoptimized -ent/--exclude-nested" +
                " -ps/--retain-pointless-switches --unicode");

        fernFlowerArgumentsField.setToolTipText("-rbr=0/1 -rsy=0/1 -din=0/1 -dc4=0/1 -das=0/1 -hes=0/1 -hdc=0/1 -dgs=0/1 -ner=0/1 ...");

        jadxCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jadxArgumentsField.setEnabled(jadxCheckBox.isSelected());
            }
        });

        procyonCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procyonArgumentsField.setEnabled(procyonCheckBox.isSelected());
            }
        });

        fernFlowerCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fernFlowerArgumentsField.setEnabled(fernFlowerCheckBox.isSelected());
            }
        });

        jadxCheckBox.setSelected(true);
        procyonCheckBox.setSelected(true);
        fernFlowerCheckBox.setSelected(true);
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

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return decompilersPanel;
    }

}
