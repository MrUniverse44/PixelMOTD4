package dev.mruniverse.pixelmotdold.commons;

public interface GLogger {

    void error(String message);

    void error(Exception throwable);

    void warn(String message);

    void debug(String message);

    void info(String message);

    void sendMessage(String message);
}


