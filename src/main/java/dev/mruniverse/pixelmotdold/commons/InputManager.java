package dev.mruniverse.pixelmotdold.commons;

import java.io.InputStream;

public interface InputManager {

    InputStream getInputStream(String resource);

    boolean isBungee();
}
