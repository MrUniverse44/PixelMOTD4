package dev.mruniverse.pixelmotdold.spigot.listeners;

import dev.mruniverse.pixelmotdold.commons.GLogger;
import dev.mruniverse.pixelmotdold.commons.enums.IconFolders;
import dev.mruniverse.pixelmotdold.commons.enums.MotdType;
import dev.mruniverse.pixelmotdold.spigot.PixelMOTD;
import org.bukkit.util.CachedServerIcon;

import java.io.File;
import java.util.*;

public class MotdBuilder {
    private final Map<MotdType, Map<String, Icon>> icons = new HashMap<>();

    private final Random random = new Random();

    private final PixelMOTD plugin;

    private final GLogger logs;

    public MotdBuilder(PixelMOTD plugin, GLogger logs) {
        this.plugin = plugin;
        this.logs = logs;
        load();
    }

    public void update() {
        load();
    }

    private void load() {
        icons.clear();

        load(MotdType.NORMAL);
        load(MotdType.WHITELIST);
        load(MotdType.BLACKLIST);
        load(MotdType.OUTDATED_SERVER);
        load(MotdType.OUTDATED_CLIENT);

    }

    private void load(MotdType motdType) {

        final Map<String, Icon> iconsPerType = icons.computeIfAbsent(
                motdType,
                l -> new HashMap<>()
        );

        File folder = IconFolders.fromText(plugin.getStorage().getFiles(), motdType);

        if (!folder.exists()) {
            logs.info("Icon-Folder (" + folder.getName() + ") has been created. [" + folder.mkdirs() + "]");
        }

        File[] files = folder.listFiles((d, fn) -> fn.endsWith(".png"));

        if (files == null) {
            icons.put(motdType, iconsPerType);
            return;
        }

        for (File icon : files) {
            iconsPerType.put(
                    icon.getName(),
                    new Icon(
                            logs,
                            motdType,
                            icon
                    )
            );
        }
        icons.put(motdType, iconsPerType);
    }

    public CachedServerIcon getFavicon(MotdType motdType, String key) {
        Map<String, Icon> icons = this.icons.get(motdType);

        if (icons == null) {
            load(motdType);
            icons = this.icons.get(motdType);
        }

        if (key.equalsIgnoreCase("RANDOM")) {
            List<Icon> values = new ArrayList<>(icons.values());
            int randomIndex = random.nextInt(values.size());
            return values.get(randomIndex).getFavicon();
        }
        if (icons.containsKey(key)) {
            return icons.get(key).getFavicon();
        }
        return null;
    }

}
