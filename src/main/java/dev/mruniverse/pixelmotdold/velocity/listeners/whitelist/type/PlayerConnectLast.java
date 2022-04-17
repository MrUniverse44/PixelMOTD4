package dev.mruniverse.pixelmotdold.velocity.listeners.whitelist.type;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import dev.mruniverse.pixelmotdold.velocity.PixelMOTD;
import dev.mruniverse.pixelmotdold.velocity.listeners.whitelist.AbstractWhitelistListener;

public class PlayerConnectLast extends AbstractWhitelistListener {

    public PlayerConnectLast(PixelMOTD plugin) {
        super(plugin);
    }
    
    @Subscribe(order = PostOrder.LAST)
    public void onLogin(LoginEvent event) {
        execute(event);
    }
}
