package dev.mruniverse.pixelmotdold.velocity.listeners.whitelist.type;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import dev.mruniverse.pixelmotdold.velocity.PixelMOTD;
import dev.mruniverse.pixelmotdold.velocity.listeners.whitelist.AbstractWhitelistListener;

public class PlayerConnectFirst extends AbstractWhitelistListener {

    public PlayerConnectFirst(PixelMOTD plugin) {
        super(plugin);
    }


    @Subscribe(order = PostOrder.FIRST)
    public void onLogin(LoginEvent event) {
        execute(event);
    }

}
