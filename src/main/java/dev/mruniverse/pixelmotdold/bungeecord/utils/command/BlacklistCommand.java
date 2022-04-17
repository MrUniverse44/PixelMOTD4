package dev.mruniverse.pixelmotdold.bungeecord.utils.command;

import dev.mruniverse.pixelmotdold.bungeecord.PixelMOTD;
import dev.mruniverse.pixelmotdold.commons.Control;
import dev.mruniverse.pixelmotdold.commons.Converter;
import dev.mruniverse.pixelmotdold.commons.enums.GuardianFiles;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.ChatColor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlacklistCommand {
    private final PixelMOTD plugin;
    private final String cmdPrefix;

    public BlacklistCommand(PixelMOTD plugin, String command) {
        this.plugin = plugin;
        this.cmdPrefix = "&8» &a/" + command;
    }

    public void usage(CommandSender sender, String[] arguments) {
        if (arguments.length == 0 || arguments[0].equalsIgnoreCase("help")) {
            help(sender);
            return;
        }
        if (arguments[0].equalsIgnoreCase("add")) {
            if (arguments.length >= 2) {
                String value = arguments[1];
                PlayerType type = PlayerType.fromUnknown(value);
                String server;
                if (arguments.length == 3) {
                    server = arguments[2];
                } else {
                    server = "global";
                }
                List<String> list;
                String path;
                if (type == PlayerType.PLAYER) {
                    path = "players." + server + ".by-name";
                } else {
                    path = "players." + server + ".by-uuid";
                }
                list = plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).getStringList(path);
                if (list.contains(value)) {
                    sendMessage(
                            sender,
                            plugin.getStorage().getFiles().getControl(GuardianFiles.MESSAGES).getColoredString(
                                            "messages.already-blacklisted",
                                            "&a<type> &e<player> &ais already in the blacklist!"
                                    )
                                    .replace("<type>",type.getName())
                                    .replace("<player>",value)
                    );
                    return;
                }
                list.add(value);
                sendMessage(
                        sender,
                        plugin.getStorage().getFiles().getControl(GuardianFiles.MESSAGES).getColoredString(
                                        "messages.blacklist-player-add",
                                        "&a<type> &e<player> &ahas been &badded &ato the blacklist."
                                )
                                .replace("<type>",type.getName())
                                .replace("<player>",value)
                );
                plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).set(path,value);
                plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).save();
                plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).reload();
                return;
            }
            argumentsIssue(sender);
            return;
        }

        if (arguments[0].equalsIgnoreCase("on")) {
            if (arguments.length >= 2) {
                String author;
                if (sender instanceof ProxiedPlayer) {
                    author = sender.getName();
                } else {
                    author = plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).getString("settings.console-name","Console");
                }
                String server = arguments[1];
                String reason;
                if (arguments.length >= 3) {
                    reason = Converter.ListToStringText(
                            Arrays.asList(getArguments(arguments, 2))
                    );
                } else {
                    reason = plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).getString("settings.default-reason","The server will be updated!");
                }
                if (!plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).contains("blacklist." + server + ".kick-message")) {
                    List<String> defaultKick = plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).getStringList("settings.default-kick-message");
                    Map<String,String> replacements = new HashMap<>();
                    if (server.equalsIgnoreCase("global")) {
                        replacements.put("%server%", server);
                    } else {
                        replacements.put("%server%", "the Network");
                    }
                    defaultKick = Converter.listReplacer(defaultKick,replacements);
                    plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).set("blacklist." + server + ".kick-message",defaultKick);
                }
                plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).set("blacklist." + server + ".reason",reason);
                plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).set("blacklist." + server + ".Enabled",true);
                plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).set("blacklist." + server + ".author",author);
                plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).save();
                plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).reload();
                sendMessage(sender,plugin.getStorage().getFiles().getControl(GuardianFiles.MESSAGES).getColoredString("messages.blacklist-enabled"));
                return;
            }
            argumentsIssue(sender);
            return;
        }
        if (arguments[0].equalsIgnoreCase("off")) {
            if (arguments.length >= 2) {
                String author;
                if (sender instanceof ProxiedPlayer) {
                    author = sender.getName();
                } else {
                    author = plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).getString("settings.console-name","Console");
                }
                String server = arguments[1];
                String reason;
                if (arguments.length >= 3) {
                    reason = Converter.ListToStringText(
                            Arrays.asList(getArguments(arguments, 2))
                    );
                } else {
                    reason = plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).getString("settings.default-reason","You are blocked from this server!");
                }
                if (!plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).contains("blacklist." + server + ".kick-message")) {
                    List<String> defaultKick = plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).getStringList("settings.default-kick-message");
                    plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).set("blacklist." + server + ".kick-message",defaultKick);
                }
                plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).set("blacklist." + server + ".reason",reason);
                plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).set("blacklist." + server + ".Enabled",false);
                plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).set("blacklist." + server + ".author",author);
                plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).save();
                plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).reload();
                sendMessage(sender,plugin.getStorage().getFiles().getControl(GuardianFiles.MESSAGES).getColoredString("messages.blacklist-disabled"));
                return;
            }
            argumentsIssue(sender);
            return;
        }

        if (arguments[0].equalsIgnoreCase("remove")) {
            if (arguments.length >= 2) {
                String value = arguments[1];
                PlayerType type = PlayerType.fromUnknown(value);
                String server;
                if (arguments.length == 3) {
                    server = arguments[2];
                } else {
                    server = "global";
                }
                List<String> list;
                String path;
                if (type == PlayerType.PLAYER) {
                    path = "players." + server + ".by-name";
                } else {
                    path = "players." + server + ".by-uuid";
                }
                list = plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).getStringList(path);
                if (!list.contains(value)) {
                    sendMessage(
                            sender,
                            plugin.getStorage().getFiles().getControl(GuardianFiles.MESSAGES).getColoredString(
                                            "messages.not-blacklisted",
                                            "&a<type> &e<player> &ais not in the blacklist!"
                                    )
                                    .replace("<type>",type.getName())
                                    .replace("<player>",value)
                    );
                    return;
                }
                list.remove(value);
                sendMessage(
                        sender,
                        plugin.getStorage().getFiles().getControl(GuardianFiles.MESSAGES).getColoredString(
                                        "messages.blacklist-player-remove",
                                        "&a<type> &e<player> &ahas been&b removed &afrom the blacklist."
                                )
                                .replace("<type>",type.getName())
                                .replace("<player>",value)
                );
                plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).set(path,value);
                plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).save();
                plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST).reload();
                return;
            }
            argumentsIssue(sender);
            return;
        }
        if (arguments[0].equalsIgnoreCase("list")) {
            sendMessage(sender,"&a&l────── PIXEL MOTD ──────");
            Control control = plugin.getStorage().getFiles().getControl(GuardianFiles.BLACKLIST);
            for (String key : control.getContent("players",false)) {
                sendList(sender,control,key);
            }
            sendMessage(sender,"&8");
            return;
        }
        help(sender);
    }

    private void sendList(CommandSender sender,Control control,String key) {
        String player = "players." + key + ".by-name";
        String uuid =  "players." + key + ".by-uuid";
        List<String> players = control.getStringList(player);
        List<String> uuids = control.getStringList(uuid);
        sendMessage(sender,"&8" + key + " players: (&7" + players.size() + "&8)");
        for (String value : players) {
            sendMessage(sender,"  &8- &7" + value);
        }
        sendMessage(sender,"&8" + key + " uuids: (&7" + players.size() + "&8)");
        for (String value : uuids) {
            sendMessage(sender,"  &8- &7" + value);
        }
    }

    private void help(CommandSender sender) {
        space(sender);
        sendMessage(sender,"&a&l────── PIXEL MOTD ──────");
        sendMessage(sender,"&8Admin - Blacklist Commands:");
        sendMessage(sender,cmdPrefix + " admin blacklist list &8- &7List of players,uuids in the whitelist");
        sendMessage(sender,cmdPrefix + " admin blacklist add (player) [global/<server|world>] &8- &7Add a player to the blacklist");
        sendMessage(sender,cmdPrefix + " admin blacklist remove (player) [global/<server|world>] &8- &7Remove a player from blacklist");
        sendMessage(sender,cmdPrefix + " admin blacklist on [global/<server|world>] [reason] &8- &7Turn on the Blacklist");
        sendMessage(sender,cmdPrefix + " admin blacklist off [global/<server|world>] [reason] &8- &7Turn off the Blacklist");
        sendMessage(sender,"&a&l────── PIXEL MOTD ──────");
    }

    private void space(CommandSender sender) {
        sender.sendMessage(
                new TextComponent(
                        " "
                )
        );
    }

    private String[] getArguments(String[] args, int size) {
        String[] arguments = new String[args.length - size];
        int argID = 0;
        int aID = 0;
        for (String arg : args) {
            if (aID < size) {
                arguments[argID] = arg;
                argID++;
            }
            aID++;
        }
        return arguments;
    }

    private void sendMessage(CommandSender sender,String message) {
        sender.sendMessage(
                new TextComponent(
                        ChatColor.translateAlternateColorCodes('&',message)
                )
        );
    }

    private void argumentsIssue(CommandSender sender) {
        sendMessage(sender,"&7Invalid arguments, please use the correct syntax.");
    }
}
