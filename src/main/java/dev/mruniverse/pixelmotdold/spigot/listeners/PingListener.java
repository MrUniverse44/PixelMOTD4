package dev.mruniverse.pixelmotdold.spigot.listeners;

import dev.mruniverse.pixelmotdold.commons.FileStorage;
import dev.mruniverse.pixelmotdold.commons.Ping;
import dev.mruniverse.pixelmotdold.commons.Priority;
import dev.mruniverse.pixelmotdold.commons.enums.FileSaveMode;
import dev.mruniverse.pixelmotdold.commons.enums.GuardianFiles;
import dev.mruniverse.pixelmotdold.commons.enums.Type;
import dev.mruniverse.pixelmotdold.spigot.PixelMOTD;
import dev.mruniverse.pixelmotdold.spigot.storage.Storage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.net.InetAddress;

public class PingListener implements Listener, Ping {

    private final PixelMOTD plugin;

    private final PingBuilder pingBuilder;

    private boolean isWhitelisted;

    public PingListener(PixelMOTD plugin) {
        this.plugin = plugin;
        this.pingBuilder = new PingBuilder(plugin);
        plugin.getStorage().getLogs().info("Custom Priority Don't Affect without ProtocolLIB");
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        load();
    }

    private void load() {

        Storage storage = plugin.getStorage();
        FileStorage fileStorage = storage.getFiles();

        fileStorage.reloadFile(FileSaveMode.MOTDS);

        isWhitelisted = fileStorage.getControl(GuardianFiles.WHITELIST).getStatus("whitelist.global.Enabled");
        pingBuilder.update();
    }

    @Override
    public void update() {
        load();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMotd(ServerListPingEvent event) {

        final InetAddress address = event.getAddress();

        final String user;

        user = getPlayerDatabase().getPlayer(address.getHostAddress());

        if (isWhitelisted) {
            pingBuilder.execute(plugin.getStorage().getPriority().get(Type.WHITELISTED), event, user);
            return;
        }
        pingBuilder.execute(plugin.getStorage().getPriority().get(Type.DEFAULT), event, user);
    }

    @Override
    public void setWhitelist(boolean status) {
        isWhitelisted = status;
    }

    public static EventPriority get(Priority priority) {
        switch (priority) {
            case HIGHEST:
                return EventPriority.HIGHEST;
            case NORMAL:
                return EventPriority.NORMAL;
            default:
            case HIGH:
                return EventPriority.HIGH;
            case LOWEST:
                return EventPriority.LOWEST;
            case LOW:
                return EventPriority.LOW;
            case MONITOR:
                return EventPriority.MONITOR;
        }
    }
}
