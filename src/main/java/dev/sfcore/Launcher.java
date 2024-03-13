package dev.sfcore;

import dev.sfcore.utils.PluginsLoader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Launcher {
    public static void main(String[] args) {
        File home = TeKit.getDataFolder();

        List<File> folders = List.of(new File(home, "plugins"), new File(home, "libraries"), new File(home, "config"));

        List<File> files = List.of(new File(home + "/config", "token.txt"), new File(home + "/config", "name.txt"));

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
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Bot.main(args);

        TeKit.setLoader(new PluginsLoader());
        TeKit.getLoader().loadPlugins();
        TeKit.getLogger().info("Loaded " + TeKit.getLoader().getPlugins().size() + " plugins.");
        TeKit.getLoader().enablePlugins();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Disabling...");
            TeKit.getLoader().disablePlugins();
        }));
    }
}
