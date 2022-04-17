package dev.mruniverse.pixelmotdold.bungeecord.listeners.ping.type;

import dev.mruniverse.pixelmotdold.bungeecord.PixelMOTD;
import dev.mruniverse.pixelmotdold.bungeecord.listeners.ping.AbstractPingListener;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class ProxyPingListenerHighest extends AbstractPingListener implements Listener {

    public ProxyPingListenerHighest(PixelMOTD plugin) {
        super(plugin);
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPing(final ProxyPingEvent event) {
        execute(event);
    }
}
