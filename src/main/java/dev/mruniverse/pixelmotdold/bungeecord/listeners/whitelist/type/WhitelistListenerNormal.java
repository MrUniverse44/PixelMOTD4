package dev.mruniverse.pixelmotdold.bungeecord.listeners.whitelist.type;

import dev.mruniverse.pixelmotdold.bungeecord.PixelMOTD;
import dev.mruniverse.pixelmotdold.bungeecord.listeners.whitelist.AbstractWhitelistListener;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class WhitelistListenerNormal extends AbstractWhitelistListener implements Listener {
    public WhitelistListenerNormal(PixelMOTD plugin) {

        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onLogin(LoginEvent event) {
        checkPlayer(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onServerConnect(ServerConnectEvent event) {
        checkPlayer(event);
    }
}
