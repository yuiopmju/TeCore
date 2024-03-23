package dev.sfcore;

// Maked by airdead.ru

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.sfcore.server.InputThread;
import dev.sfcore.server.OutputThread;
import dev.sfcore.utils.CommandsLoader;
import dev.sfcore.utils.PluginsLoader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;

public class Launcher {
    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        File home = TeKit.getDataFolder();

        List<File> folders = List.of(new File(home, "plugins"), new File(home, "libraries"), new File(home, "conf"));

        List<File> files = List.of(new File(home + "/conf", "conf.json"), new File(home + "/plugins", "plugins.json"));

        List<String> locs = List.of("commons-lang3.jar", "commons-logging.jar", "gson.jar", "httpclient.jar", "jackson-annotations.jar", "jackson-core.jar", "jackson-databind.jar", "javassist.jar", "reflections.jar", "slf4j-api.jar", "telegram-api.jar");

        System.out.println("files = " + files);
        System.out.println("folders = " + folders);
        System.out.println("home = " + home);

        for(File folder : folders){
            if(folder.exists()){
                continue;
            }

            System.out.println("Loading " + folder.getName() + " folder...");
            folder.mkdir();

        }

        Thread th = new Thread(() -> {
            for(String f : locs) {
                File file = new File(home, f);
                if (file.exists()) {
                    continue;
                }

                if (file.getName().endsWith(".jar")) {
                    try {
                        TeKit.downloadFile("https://sfcore.ru/share/tecore/" + file.getName(), home);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    continue;
                }
            }

            shutdown();
        });

        int i = 0;

        while(th.isAlive()){
            i++;
        }

        for(File file : files){

            if(file.getName().endsWith(".jar")){
                TeKit.loadJar(file);
            }

            if (file.exists()) {
                continue;
            }

            try {
                System.out.println("Loading " + file.getName() + " files...");
                file.createNewFile();

                if(file.getName().endsWith(".json")){
                    TeKit.write(file.getAbsolutePath(), "{\n\n}");
                }

                if(file.getName().equals("conf.json")){
                    Gson gson = new Gson();
                    JsonObject obj = gson.fromJson(TeKit.read(file.getAbsolutePath()), JsonObject.class);
                    obj.addProperty("token", "tokenHere");
                    obj.addProperty("bot-name", "botNameHere");

                    String newJson = gson.toJson(obj);

                    TeKit.write(file.getAbsolutePath(), newJson);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Gson gson = new Gson();
        JsonObject obj = gson.fromJson(TeKit.read(files.get(0).getAbsolutePath()), JsonObject.class);

        Bot.main(new String[]{obj.get("token").getAsString(), obj.get("bot-name").getAsString()});

        TeKit.setLoader(new PluginsLoader());
        TeKit.setCommandsLoader(new CommandsLoader());
        TeKit.getLoader().loadPackages();
        TeKit.getLoader().loadPlugins();
        TeKit.getLogger().info("Loaded " + TeKit.getLoader().getPlugins().size() + " plugins.");
        TeKit.getLoader().enablePlugins();

        System.out.println("Done (" + ((double) (System.currentTimeMillis() - l) / 1000.0) + "s)! For help, type \"help\"");

        InputThread inputThread = new InputThread();
        inputThread.start();

        BlockingQueue<String> queue = new LinkedBlockingQueue<>();

        OutputThread outputThread = new OutputThread(queue);
        outputThread.start();

        try {
            inputThread.join();
            outputThread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(Launcher::shutdown));
    }

    public static void shutdown(){
        System.out.println("Disabling...");
        TeKit.getLoader().disablePlugins();

        try {
            Thread.sleep(1000);
            Runtime.getRuntime().exit(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
