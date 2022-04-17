package dev.mruniverse.pixelmotdold.velocity.listeners.whitelist.type;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import dev.mruniverse.pixelmotdold.velocity.PixelMOTD;
import dev.mruniverse.pixelmotdold.velocity.listeners.whitelist.AbstractWhitelistListener;

public class PlayerConnectLate extends AbstractWhitelistListener {

    public PlayerConnectLate(PixelMOTD plugin) {
        super(plugin);
    }

    @Subscribe(order = PostOrder.NORMAL)
    public void onLogin(LoginEvent event) {
        execute(event);
    }
}
