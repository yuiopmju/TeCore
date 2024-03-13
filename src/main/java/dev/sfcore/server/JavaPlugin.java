package dev.sfcore.server;

public abstract class JavaPlugin {
    private boolean isDisabled = false;
    public abstract void onEnable();
    public abstract void onDisable();
    public abstract void onLoad();
    public void disable(){
        isDisabled = true;
        onDisable();
    }
}
