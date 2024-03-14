package dev.sfcore.utils;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class SystemOutLogger extends Handler {
    @Override
    public void publish(LogRecord record) {
        if (record.getLevel().intValue() >= Level.INFO.intValue()) {
            System.out.println(getFormatter().format(record));
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
}
