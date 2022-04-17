package dev.mruniverse.pixelmotdold.commons;

import dev.mruniverse.pixelmotdold.commons.enums.FileSaveMode;
import dev.mruniverse.pixelmotdold.commons.enums.GuardianFiles;
import dev.mruniverse.pixelmotdold.commons.enums.MotdType;

import java.io.File;

public interface FileStorage {

    File getIconsFolder(MotdType motdType);

    File getIconsFolder(MotdType motdType, String motdName);

    File getMainIcons();

    File getFile(GuardianFiles fileToGet);

    Control getControl(GuardianFiles file);

    void checkIconFolder();

    void setMessages(String code);

    void reloadFile(FileSaveMode mode);

    void save(FileSaveMode mode);
}


