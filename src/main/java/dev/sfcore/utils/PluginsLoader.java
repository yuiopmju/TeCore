package dev.sfcore.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import dev.sfcore.Handler;
import dev.sfcore.TeKit;
import dev.sfcore.commands.CommandExecutor;
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
    private final List<Class<? extends ResponseHandler>> responseHandlers = new ArrayList<>();
    private final List<Class<? extends CommandExecutor>> commandExecutors = new ArrayList<>();
    private final Map<Handler, File> handlers = new LinkedHashMap<>();
    private final HashMap<String, String> packages = new HashMap<>();
    private final HashMap<String, String> descriptions = new HashMap<>();
    private final HashMap<String, String> versions = new HashMap<>();
    private final HashMap<String, String> names = new HashMap<>();
    private final HashMap<String, String> dependencies = new HashMap<>();
    private final HashMap<String, String> fileNames = new HashMap<>();

    private void loadJar(File file) {
        try {
            URL jarUrl = new URL("file:" + file.getPath());
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl});
            Thread.currentThread().setContextClassLoader(classLoader);

            SystemOutLogger.redirectJULToSystemOut();

            Class<? extends JavaPlugin> pluginClass = getMainClass(file);
            Class<? extends ResponseHandler> handler = getResponseHandler(file);

            responseHandlers.add(handler);

            if (pluginClass != null) {
                JavaPlugin pluginInstance = pluginClass.getDeclaredConstructor().newInstance();
                pluginInstance.setName(names.get(file.getName().substring(0, file.getName().length() - 4))); // For plugin with name for example "FeTuro-1.0.0-SNAPSHOT.jar" you will need type "FeTuro-1.0.0-SNAPSHOT"
                pluginInstance.setFile(file);
                pluginInstance.setDescription(descriptions.get(file.getName().substring(0, file.getName().length() - 4)));
                pluginInstance.setDirectory(new File(file.getParentFile().getAbsolutePath() + "/" + pluginInstance.getName()));
                pluginInstance.setVersion(versions.get(file.getName().substring(0, file.getName().length() - 4)));
                pluginInstance.setDependencies(dependencies.get(file.getName().substring(0, file.getName().length() - 4)).split(", "));
                if(getCommandExecutors(file) != null){
                    commandExecutors.addAll(getCommandExecutors(file));
                }
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

    public Class<? extends JavaPlugin> getMainClass(File file){
        Set<Class<? extends JavaPlugin>> set = TeKit.getClasses(file, JavaPlugin.class);

        for (Class<? extends JavaPlugin> clazz : set) {
            return clazz;
        }

        TeKit.getLogger().error("Cannot load plugin " + file.getName() + ": JavaPlugin class doesn't exist.");
        return null;
    }

    public Collection<Class<? extends CommandExecutor>> getCommandExecutors(File file){
        Set<Class<? extends CommandExecutor>> set = TeKit.getClasses(file, CommandExecutor.class);

        System.out.println("Searching: " + set);

        if(set == null){
            //System.out.println("ResponseListener dont found in " + file.getName());
            return null;
        }

        Collection<Class<? extends CommandExecutor>> rt = new ArrayList<>(set);

        return rt;
    }

    public Class<? extends ResponseHandler> getResponseHandler(File file){
        Set<Class<? extends ResponseHandler>> set = TeKit.getClasses(file, ResponseHandler.class);

        if(set == null){
            //System.out.println("ResponseListener dont found in " + file.getName());
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
            JsonObject value = entry.getValue().getAsJsonObject();
            JsonElement name = value.get("name");
            JsonElement pack = value.get("package");
            JsonElement description = value.get("description");
            JsonElement dependencies = value.get("dependencies");
            JsonElement fileNames = value.get("file");
            JsonElement version = value.get("version");

            checkAndPut(key, description, descriptions);
            checkAndPut(key, dependencies, this.dependencies);
            checkAndPut(key, version, versions);

            if (name == null) {
                System.out.println("Cannot load " + key + " plugin: Json config does not contains name value");
                return;
            } else {
                names.put(key, name.getAsString());
            }

            if (pack == null) {
                System.out.println("Cannot load " + key + " plugin: Json config does not contains package value");
                return;
            } else {
                packages.put(key, pack.getAsString());
            }

            if (file == null) {
                System.out.println("Cannot load " + key + " plugin: Json config does not contains file value");
                return;
            } else {
                this.fileNames.put(key, fileNames.getAsString());
            }

            /*System.out.println("Loading plugin " + name.getAsString() + " using package " + pack.getAsString());
            System.out.println(descriptions);
            System.out.println(this.dependencies);
            System.out.println(versions);
            System.out.println(names);
            System.out.println(packages);
            System.out.println(this.fileNames);*/
        }
    }

    public List<Class<? extends ResponseHandler>> getResponseHandlers() {
        return responseHandlers;
    }

    private void checkAndPut(String key, JsonElement obj, HashMap<String, String> put){
        if (obj != null) {
            put.put(key, obj.getAsString());
        }
    }

    public List<Class<? extends CommandExecutor>> getCommandExecutors() {
        return commandExecutors;
    }
}
