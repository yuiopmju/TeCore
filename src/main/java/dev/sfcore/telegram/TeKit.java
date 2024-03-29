package dev.sfcore.telegram;

import dev.sfcore.telegram.responses.MainHandler;
import dev.sfcore.telegram.server.client.Chat;
import dev.sfcore.telegram.utils.CommandsLoader;
import dev.sfcore.telegram.utils.PluginsLoader;
import dev.sfcore.telegram.utils.SystemOutLogger;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

public class TeKit {
    private static PluginsLoader loader;
    private static CommandsLoader commandsLoader;
    private static MainHandler mainHandler;
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    private static int id = 0;
    protected static boolean isDownloading = false;
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
                isDownloading = true;
            }

            isDownloading = false;
        }
    }

    public static <T> Set<Class<? extends T>> getClasses(File file, Class<T> type){

        for(String key : TeKit.getLoader().getPackages().keySet()){
            if(!key.equals(file.getName().substring(0, file.getName().length() - 4))){
                continue;
            }
            Reflections reflections = new Reflections(TeKit.getLoader().getPackages().get(key));
            System.out.println(type);
            System.out.println(file.getAbsolutePath());
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

    protected static void setCommandsLoader(CommandsLoader loader){
        TeKit.commandsLoader = loader;
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

    public static int getNewId(){
        id = id + 1;
        return id;
    }

    public static CommandsLoader getCommandsLoader(){
        return commandsLoader;
    }

    public static void loadJar(File file){
        URL jarUrl = null;
        try {
            jarUrl = new URL("file:" + file.getPath());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl});
        Thread.currentThread().setContextClassLoader(classLoader);

        SystemOutLogger.redirectJULToSystemOut();
    }

    public static void loadJars(String[] urls) {
        for (String url : urls) {
            try {
                File tempFile = File.createTempFile("temp", ".jar");
                tempFile.deleteOnExit();

                URL jarUrl = new URL(url);
                try (InputStream inputStream = jarUrl.openStream();
                     OutputStream outputStream = new FileOutputStream(tempFile)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
                Class<URLClassLoader> sysClass = URLClassLoader.class;
                java.lang.reflect.Method method = sysClass.getDeclaredMethod("addURL", URL.class);
                method.setAccessible(true);
                method.invoke(classLoader, tempFile.toURI().toURL());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Chat getChat(long id){
        return new ChatHandler(id);
    }

    private static class ChatHandler implements Chat {
        private long chatId;
        public ChatHandler(long chatId){
            this.chatId = chatId;
        }

        @Override
        public long getChatId() {
            return chatId;
        }
    }
}
