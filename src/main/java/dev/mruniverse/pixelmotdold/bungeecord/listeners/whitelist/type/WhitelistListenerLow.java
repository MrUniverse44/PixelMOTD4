package dev.mruniverse.pixelmotdold.bungeecord.listeners.whitelist.type;

import dev.mruniverse.pixelmotdold.bungeecord.PixelMOTD;
import dev.mruniverse.pixelmotdold.bungeecord.listeners.whitelist.AbstractWhitelistListener;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class WhitelistListenerLow extends AbstractWhitelistListener implements Listener {
    public WhitelistListenerLow(PixelMOTD plugin) {

        super(plugin);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onLogin(LoginEvent event) {
        checkPlayer(event);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onServerConnect(ServerConnectEvent event) {
        checkPlayer(event);
    }
}
