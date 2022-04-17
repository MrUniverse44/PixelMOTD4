package dev.mruniverse.pixelmotdold.commons.enums;

import dev.mruniverse.pixelmotdold.commons.FileStorage;
import dev.mruniverse.pixelmotdold.commons.utils.Folder;

import java.io.File;

public enum IconFolders implements Folder {
    GENERAL("icons"),
    NORMAL("normal"),
    WHITELIST("whitelist"),
    OUTDATED_SERVER("outdatedServer"),
    OUTDATED_CLIENT("outdatedClient");

    private final String name;

    IconFolders(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public static File fromText(FileStorage storage,MotdType motdType) {
        return storage.getIconsFolder(motdType);
    }
}
