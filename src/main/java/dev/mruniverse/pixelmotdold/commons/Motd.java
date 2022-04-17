package dev.mruniverse.pixelmotdold.commons;

import dev.mruniverse.pixelmotdold.commons.enums.IconFolders;
import dev.mruniverse.pixelmotdold.commons.enums.MotdSettings;

public interface Motd {

    String getPath();

    String getName();

    String getSettings(MotdSettings settings);

    IconFolders getIconFolders();

    boolean isHexMotd();

}
