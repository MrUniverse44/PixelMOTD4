package dev.mruniverse.pixelmotdold.commons.utils;

public interface GuardianFile {
    String getFileName();

    boolean isInDifferentFolder();

    String getFolderName();

    String getPath();
}
