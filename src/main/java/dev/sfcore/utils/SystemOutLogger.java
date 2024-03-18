package dev.sfcore.utils;

import dev.sfcore.TeKit;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class SystemOutLogger extends Handler {
    @Override
    public void publish(LogRecord record) {
        if (record.getLevel().intValue() >= Level.INFO.intValue()) {
            System.out.println(format(getFormatter().format(record)));
        }
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
    }

    public static void redirectJULToSystemOut() {
        Logger rootLogger = Logger.getLogger("");
        for (Handler handler : rootLogger.getHandlers()) {
            rootLogger.removeHandler(handler);
        }
        rootLogger.addHandler(new SystemOutLogger());
    }

    public String format(String s){
        String r = s;
        r.replaceAll("§c", TeKit.RED);

        return r + TeKit.RESET;
    }
}
