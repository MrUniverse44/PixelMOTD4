package dev.mruniverse.pixelmotd.spigot.listeners;

import com.google.common.io.Files;
import dev.mruniverse.pixelmotd.global.GLogger;
import org.bukkit.Bukkit;
import org.bukkit.util.CachedServerIcon;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MotdBuilder {
    private final Random random = new Random();

    private final GLogger logs;

    public MotdBuilder(GLogger logs) {
        this.logs = logs;
    }
    @SuppressWarnings("UnstableApiUsage")
    public CachedServerIcon getIcon(File[] icons, String icon, File motdFolder) {
        if (icons != null && icons.length != 0) {
            List<File> validIcons = new ArrayList<>();
            if(icon.equalsIgnoreCase("RANDOM")) {
                for (File image : icons) {
                    if (Files.getFileExtension(image.getPath()).equals("png")) {
                        validIcons.add(image);
                    }
                }
            } else {
                File finding = new File(motdFolder,icon);
                if(!finding.exists()) {
                    logs.error("File " + icon + " doesn't exists");
                } else {
                    if (Files.getFileExtension(finding.getPath()).equals("png")) {
                        validIcons.add(finding);
                    } else {
                        logs.error("This image " + icon + " isn't (.png) format");
                    }
                }
            }
            if (validIcons.size() != 0) {
                return getConverted(validIcons.get(random.nextInt(validIcons.size())));
            }
        }
        return null;
    }

    private CachedServerIcon getConverted(File file) {
        try {
            return Bukkit.loadServerIcon(file);
        } catch(Throwable ignored) {
            reportBadImage(file.getPath());
            return null;
        }
    }

    private void reportBadImage(String filePath) {
        logs.warn("Can't read image: &b" + filePath + "&f. Please check image size: 64x64 or check if the image isn't corrupted.");
    }


}
