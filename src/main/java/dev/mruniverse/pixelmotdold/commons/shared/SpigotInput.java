package dev.mruniverse.pixelmotdold.commons.shared;

import dev.mruniverse.pixelmotdold.commons.InputManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStream;

public class SpigotInput implements InputManager {
    private final JavaPlugin plugin;

    public SpigotInput(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public InputStream getInputStream(String resource) {
        return plugin.getResource(resource);
    }

    @Override
    public boolean isBungee() {
        return false;
    }
}
