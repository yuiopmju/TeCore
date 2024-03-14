package dev.sfcore;

import dev.sfcore.responses.MainHandler;
import dev.sfcore.utils.PluginsLoader;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.Set;

public class TeKit {
    private static PluginsLoader loader;
    private static MainHandler mainHandler;
    public static File getDataFolder(){
        return new File(Bot.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile();
    }

    public static void downloadFile(String fileUrl, File destination) throws IOException {
        URL url = new URL(fileUrl);
        try (InputStream in = new BufferedInputStream(url.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(destination.getAbsolutePath())) {

            byte[] dataBuffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        }
    }

    public static <T> Set<Class<? extends T>> getClasses(File file, Class<T> type){

        for(String key : TeKit.getLoader().getPackages().keySet()){
            Reflections reflections = new Reflections(TeKit.getLoader().getPackages().get(key));
            Set<Class<? extends T>> classes = reflections.getSubTypesOf(type);

            if(file.getName().startsWith(key)){
                if(!classes.isEmpty()){
                    return classes;
                }
            }
        }

        return null;
    }

    public static Logger getLogger(){
        return LoggerFactory.getLogger("TeKit");
    }

    public static PluginsLoader getLoader() {
        return loader;
    }

    protected static void setLoader(PluginsLoader loader){
        TeKit.loader = loader;
    }

    public static String read(String filePath) {
        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonContent.toString();
    }

    public static void write(String path, String content) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MainHandler getMainHandler() {
        return mainHandler;
    }

    public static void loadMainHandler(){
        mainHandler = new MainHandler();
    }
}
