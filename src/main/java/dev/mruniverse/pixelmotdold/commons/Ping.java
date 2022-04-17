package dev.mruniverse.pixelmotdold.commons;

import dev.mruniverse.pixelmotdold.commons.players.PlayersDatabase;

public interface Ping {
    PlayersDatabase database = new PlayersDatabase();

    default PlayersDatabase getPlayerDatabase() {
        return database;
    }

    void update();

    void setWhitelist(boolean status);
}
