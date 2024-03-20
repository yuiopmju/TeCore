package dev.sfcore.server;

import dev.sfcore.server.commands.Command;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class JavaPlugin {
    private boolean isDisabled = false;
    private String name;
    private String description;
    private String version;
    private File file;
    private File dir;
    private String[] dependencies;
    public abstract void onEnable();
    public abstract void onDisable();
    public abstract void onLoad();
    public void disable(){
        isDisabled = true;
        onDisable();
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getDirectory() {
        return dir;
    }

    public void setDirectory(File dir) {
        this.dir = dir;
    }

    public String[] getDependencies() {
        return dependencies;
    }

    public void setDependencies(String[] dependencies) {
        this.dependencies = dependencies;
    }

    public Command getCommand(String cmd){
        return new Command(cmd);
    }
}
