package dev.sfcore.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import dev.sfcore.TeKit;
import dev.sfcore.responses.ResponseHandler;
import dev.sfcore.server.JavaPlugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

import com.google.gson.JsonObject;

public class PluginsLoader {

    private final List<JavaPlugin> plugins = new ArrayList<>();
    private final List<Class<? extends ResponseHandler>> handlers = new ArrayList<>();
    private final HashMap<String, String> packages = new HashMap<>();

    private void loadJar(File file) {
        try {
            URL jarUrl = new URL("file:" + file.getPath());
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl});
            Thread.currentThread().setContextClassLoader(classLoader);

            SystemOutLogger.redirectJULToSystemOut(); // Redirect Java's built-in logging

            Class<? extends JavaPlugin> pluginClass = getMainClass(file);
            Class<? extends ResponseHandler> handler = getResponseHandler(file);

            handlers.add(handler);

            if (pluginClass != null) {
                JavaPlugin pluginInstance = pluginClass.getDeclaredConstructor().newInstance();
                pluginInstance.onLoad();
                plugins.add(pluginInstance);
            } else {
                System.out.println("Cannot load plugin " + file.getName() + ": JavaPlugin class doesn't exist."); // TODO: Doesnt see JavaPlugin extends class.
            }

        } catch (MalformedURLException | ReflectiveOperationException e) {
            System.out.println("Error loading plugin");
            e.printStackTrace();
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

        TeKit.loadMainHandler();

        TeKit.getMainHandler().load();
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

    public Class<? extends ResponseHandler> getResponseHandler(File file){
        Set<Class<? extends ResponseHandler>> set = TeKit.getClasses(file, ResponseHandler.class);

        if(set == null){
            System.out.println("ResponseListener dont found in " + file.getName());
            return null;
        }
        for (Class<? extends ResponseHandler> clazz : set) {
            return clazz;
        }

        return null;
    }

    public void disablePlugins(){
        for(JavaPlugin plugin : plugins){
            plugin.disable();
        }
    }

    public HashMap<String, String> getPackages() {
        return packages;
    }

    public void loadPackages(){
        File file = new File(TeKit.getDataFolder() + "/plugins", "plugins.json");

        Gson gson = new Gson();
        JsonObject obj =  gson.fromJson(TeKit.read(file.getPath()), JsonObject.class);

        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().getAsString();
            System.out.println("Loading plugin " + key + " using package " + value);
            packages.put(key, value);
        }
    }

    public List<Class<? extends ResponseHandler>> getHandlers() {
        return handlers;
    }
}
