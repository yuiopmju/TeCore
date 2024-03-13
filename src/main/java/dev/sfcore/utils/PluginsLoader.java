package dev.sfcore.utils;

import dev.sfcore.TeKit;
import dev.sfcore.server.JavaPlugin;
import org.glassfish.jersey.server.internal.scanning.JarFileScanner;
import org.reflections.Reflections;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PluginsLoader {

    private final List<JavaPlugin> plugins = new ArrayList<>();

    private void loadJar(File file) {
        try {
            URL jarUrl = new URL("file:" + file.getPath());
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl});
            Thread.currentThread().setContextClassLoader(classLoader);

            Class<? extends JavaPlugin> pluginClass = getMainClass(file);

            if (pluginClass != null) {
                JavaPlugin pluginInstance = pluginClass.getDeclaredConstructor().newInstance();
                TeKit.getLogger().info("Loading " + file.getName().replace(".jar", "") + "...");
                pluginInstance.onLoad();
                plugins.add(pluginInstance);
            } else {
                TeKit.getLogger().error("Cannot load plugin " + file.getName() + ": JavaPlugin class doesn't exist.");
            }

        } catch (MalformedURLException | ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadPlugins(){
        for(File plugin : new File(TeKit.getDataFolder(), "plugins").listFiles()){
            if(plugin.getName().endsWith(".jar")){
                if(plugin.isFile()){
                    loadJar(plugin);
                }
            }
        }
    }

    public List<JavaPlugin> getPlugins() {
        return plugins;
    }

    public void enablePlugins(){
        for(JavaPlugin plugin : plugins){
            plugin.onEnable();
        }
    }

    public Class<? extends JavaPlugin> getMainClass(File file) {
        Set<Class<? extends JavaPlugin>> set = TeKit.getClasses(file, JavaPlugin.class);

        for (Class<? extends JavaPlugin> clazz : set) {
            return clazz;
        }

        TeKit.getLogger().error("Cannot load plugin " + file.getName() + ": JavaPlugin class doesn't exist.");
        return null;
    }

    public void disablePlugins(){
        for(JavaPlugin plugin : plugins){
            plugin.disable();
        }
    }
}
