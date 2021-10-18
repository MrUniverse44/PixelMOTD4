package dev.mruniverse.pixelmotd.global.shared;

import dev.mruniverse.pixelmotd.global.InputManager;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.InputStream;

public class BungeeInput implements InputManager {
    private final Plugin plugin;

    public BungeeInput(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public InputStream getInputStream(String resource) {
        return plugin.getResourceAsStream(resource);
    }

    @Override
    public boolean isBungee() {
        return true;
    }
}
