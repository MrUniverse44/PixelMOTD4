package dev.mruniverse.pixelmotdold.commons;

import dev.mruniverse.pixelmotdold.commons.enums.MotdSettings;
import dev.mruniverse.pixelmotdold.commons.enums.MotdType;

public interface Players {
    int execute(Control control, MotdType motdType, MotdSettings path, int value);
}
