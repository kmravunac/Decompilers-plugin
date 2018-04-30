package hr.fer.decompiler.plugin.settings;

public class DecompilerSettings {
    private boolean jadxSelected;
    private boolean procyonSelected;
    private boolean fernFlowerSelected;

    private String jadxArgs;
    private String procyonArgs;
    private String fernFlowerArgs;

    public DecompilerSettings(boolean jadxSelected, boolean procyonSelected, boolean fernFlowerSelected, String jadxArgs, String procyonArgs, String fernFlowerArgs) {
        this.jadxSelected = jadxSelected;
        this.procyonSelected = procyonSelected;
        this.fernFlowerSelected = fernFlowerSelected;
        this.jadxArgs = jadxArgs;
        this.procyonArgs = procyonArgs;
        this.fernFlowerArgs = fernFlowerArgs;
    }

    public boolean isJadxSelected() {
        return jadxSelected;
    }

    public void setJadxSelected(boolean jadxSelected) {
        this.jadxSelected = jadxSelected;
    }

    public boolean isProcyonSelected() {
        return procyonSelected;
    }

    public void setProcyonSelected(boolean procyonSelected) {
        this.procyonSelected = procyonSelected;
    }

    public boolean isFernFlowerSelected() {
        return fernFlowerSelected;
    }

    public void setFernFlowerSelected(boolean fernFlowerSelected) {
        this.fernFlowerSelected = fernFlowerSelected;
    }

    public String getJadxArgs() {
        return jadxArgs;
    }

    public void setJadxArgs(String jadxArgs) {
        this.jadxArgs = jadxArgs;
    }

    public String getProcyonArgs() {
        return procyonArgs;
    }

    public void setProcyonArgs(String procyonArgs) {
        this.procyonArgs = procyonArgs;
    }

    public String getFernFlowerArgs() {
        return fernFlowerArgs;
    }

    public void setFernFlowerArgs(String fernFlowerArgs) {
        this.fernFlowerArgs = fernFlowerArgs;
    }
}
