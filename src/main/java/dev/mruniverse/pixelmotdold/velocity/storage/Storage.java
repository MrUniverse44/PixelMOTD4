package dev.mruniverse.pixelmotdold.velocity.storage;

import dev.mruniverse.pixelmotdold.commons.PluginStorage;
import dev.mruniverse.pixelmotdold.velocity.PixelMOTD;

public class Storage extends PluginStorage {
    private final PixelMOTD builder;

    public Storage(PixelMOTD builder) {
        this.builder = builder;
    }

    public void loadCommand(String command) {
        //TODO
    }


}
