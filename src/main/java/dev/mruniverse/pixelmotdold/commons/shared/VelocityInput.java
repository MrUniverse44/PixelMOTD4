package dev.mruniverse.pixelmotdold.commons.shared;

import dev.mruniverse.pixelmotdold.commons.InputManager;
import dev.mruniverse.pixelmotdold.velocity.PixelMOTD;

import java.io.InputStream;

public class VelocityInput implements InputManager {
    private final PixelMOTD plugin;

    public VelocityInput(PixelMOTD plugin) {
        this.plugin = plugin;
    }

    @Override
    public InputStream getInputStream(String resource) {
        return VelocityInput.class.getResourceAsStream(resource);
    }

    @Override
    public boolean isBungee() {
        return false;
    }
}
