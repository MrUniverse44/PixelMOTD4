package dev.mruniverse.pixelmotdold.commons;

import dev.mruniverse.pixelmotdold.commons.enums.DefaultMotdPriority;
import dev.mruniverse.pixelmotdold.commons.enums.GuardianFiles;

public abstract class PluginStorage {
    private FileStorage storage;
    private GLogger logs;
    private InputManager inputManager;
    private DefaultMotdPriority priority;

    public DefaultMotdPriority getPriority() {
        return priority;
    }

    public void updatePriority() {
        this.priority = DefaultMotdPriority.getFromText(
                storage.getControl(GuardianFiles.SETTINGS).getString(
                        "settings.default-priority-motd",
                        "DEFAULT"
                )
        );
    }

    public void setLogs(GLogger logs) {
        this.logs = logs;
    }

    public GLogger getLogs() {
        return logs;
    }

    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public void setStorage(FileStorage storage) {
        this.storage = storage;
    }

    public FileStorage getFiles() {
        return storage;
    }
}
