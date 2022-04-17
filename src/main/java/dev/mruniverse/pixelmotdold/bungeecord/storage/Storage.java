package dev.mruniverse.pixelmotdold.bungeecord.storage;

import dev.mruniverse.pixelmotdold.bungeecord.PixelMOTD;
import dev.mruniverse.pixelmotdold.bungeecord.utils.command.MainCommand;
import dev.mruniverse.pixelmotdold.commons.PluginStorage;

public class Storage extends PluginStorage {
    private final PixelMOTD plugin;

    public Storage(PixelMOTD plugin) {
        this.plugin = plugin;
    }

    public void loadCommand(String command) {
        plugin.getProxy().getPluginManager().registerCommand(plugin, new MainCommand(plugin, command));
    }
}