package dev.sfcore;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.sfcore.server.InputThread;
import dev.sfcore.server.OutputThread;
import dev.sfcore.utils.PluginsLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Launcher {
    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        File home = TeKit.getDataFolder();

        List<File> folders = List.of(new File(home, "plugins"), new File(home, "libraries"), new File(home, "conf"));

        List<File> files = List.of(new File(home + "/conf", "conf.json"), new File(home + "/plugins", "plugins.json"));

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

        for(File file : files){
            if(file.exists()){
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
        TeKit.getLoader().loadPackages();
        TeKit.getLoader().loadPlugins();
        TeKit.getLogger().info("Loaded " + TeKit.getLoader().getPlugins().size() + " plugins.");
        TeKit.getLoader().enablePlugins();

        System.out.println("Done! (" + (System.currentTimeMillis() - l) + "ms);");

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
