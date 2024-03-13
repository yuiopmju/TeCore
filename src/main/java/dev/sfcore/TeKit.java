package dev.sfcore;

import dev.sfcore.utils.PluginsLoader;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.Set;

public class TeKit {
    private static PluginsLoader loader;
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
        Reflections reflections = new Reflections("");
        return reflections.getSubTypesOf(type);
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
}
