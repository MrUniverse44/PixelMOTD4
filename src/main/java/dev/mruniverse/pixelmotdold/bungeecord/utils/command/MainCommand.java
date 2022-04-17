package dev.mruniverse.pixelmotdold.bungeecord.utils.command;

import dev.mruniverse.pixelmotdold.bungeecord.PixelMOTD;
import dev.mruniverse.pixelmotdold.commons.Control;
import dev.mruniverse.pixelmotdold.commons.enums.FileSaveMode;
import dev.mruniverse.pixelmotdold.commons.enums.GuardianFiles;
import dev.mruniverse.pixelmotdold.commons.utils.Updater;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MainCommand extends Command {


    private final PixelMOTD plugin;

    private final String cmdPrefix;

    private final WhitelistCommand whitelist;

    private final BlacklistCommand blacklist;

    public MainCommand(PixelMOTD plugin, String command) {
        super(command);
        this.plugin = plugin;
        this.cmdPrefix = "&8» &a/" + command;
        this.whitelist = new WhitelistCommand(plugin,command);
        this.blacklist = new BlacklistCommand(plugin,command);
    }

    public static void sendMessage(ProxiedPlayer player, String message) {
        if (message == null) message = "Unknown Message";
        message = ChatColor.translateAlternateColorCodes('&',message);
        player.sendMessage(new TextComponent(message));
    }
    public static void sendMessage(CommandSender sender, String message) {
        if (message == null) message = "Unknown Message";
        message = ChatColor.translateAlternateColorCodes('&',message);
        sender.sendMessage(new TextComponent(message));
    }

    private boolean hasPermission(CommandSender sender, String permission, boolean sendMessage) {
        boolean check = true;
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer)sender;
            check = player.hasPermission(permission);
            if (sendMessage) {
                String permissionMsg = plugin.getStorage().getFiles().getControl(GuardianFiles.MESSAGES).getColoredString("messages.others.no-perms");
                if (permissionMsg == null) permissionMsg = "&cYou need permission &7%permission% &cfor this action.";
                if (!check)
                    sendMessage(player, permissionMsg.replace("%permission%", permission));
            }
        }
        return check;
    }

    /**
     * Execute this command with the specified sender and arguments.
     *
     * @param sender the executor of this command
     * @param args   arguments used to invoke this command
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        try {
            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                sendMessage(sender," ");
                sendMessage(sender,"&a&l────── PIXEL MOTD ──────");
                sendMessage(sender,"&8Created by MrUniverse44 w/ help from Sebastnchan & SUPREMObenjamin");
                if (hasPermission(sender,"pmotd.admin.help",false) || hasPermission(sender,"pmotd.admin.help.*",false)) sendMessage(sender,cmdPrefix + " admin &8- &7Admin commands");
                sendMessage(sender,"&a&l────── PIXEL MOTD ──────");
                return;
            }
            if (args[0].equalsIgnoreCase("admin")) {
                if (args.length == 1 || args[1].equalsIgnoreCase("1")) {
                    if (hasPermission(sender, "pmotd.admin.help", true) || hasPermission(sender,"pmotd.admin.help.*",true)) {
                        sendMessage(sender," ");
                        sendMessage(sender, "&a&l────── PIXEL MOTD ──────");
                        sendMessage(sender, cmdPrefix + " admin whitelist &8- &7Whitelist Commands.");
                        sendMessage(sender, cmdPrefix + " admin blacklist &8- &7Blacklist Commands.");
                        sendMessage(sender, cmdPrefix + " admin reload &8- &7Reload the plugin.");
                        sendMessage(sender, cmdPrefix + " admin updater &8- &7Updater Command.");
                        sendMessage(sender, "&8[] &f= &bOPTIONAL &8| &8() &f= &bOBLIGATORY");
                        sendMessage(sender, "&a&l────── PIXEL MOTD ──────");
                    }
                    return;
                }

                if (args[1].equalsIgnoreCase("updater")) {
                    if (hasPermission(sender, "pmotd.admin.updater", true) || hasPermission(sender,"pmotd.admin.help.*",true)) {
                        final Control settings = plugin.getStorage().getFiles().getControl(GuardianFiles.SETTINGS);
                        if (settings.getStatus("settings.update-check", true)) {
                            sendMessage(sender, "&9Updater Command has been used, information will be posted in Console");
                            if (settings.getStatus("settings.auto-download-updates", true)) {
                                new Updater(plugin.getStorage().getLogs(), plugin.getDescription().getVersion(), 37177, plugin.getDataFolder(), Updater.UpdateType.CHECK_DOWNLOAD);
                            } else {
                                new Updater(plugin.getStorage().getLogs(), plugin.getDescription().getVersion(), 37177, plugin.getDataFolder(), Updater.UpdateType.VERSION_CHECK);
                            }
                            sendMessage(sender, "&bUpdater has been applied read info in Console.");
                        } else {
                            sendMessage(sender, "&cUpdater is not enabled in settings.yml");
                        }
                    }
                }

                if (args[1].equalsIgnoreCase("reload")) {
                    if (hasPermission(sender,"pmotd.admin.use.reload",true) || hasPermission(sender,"pmotd.admin.help.*",true)) {
                        long timeMS = System.currentTimeMillis();
                        try {
                            plugin.getStorage().getFiles().reloadFile(FileSaveMode.ALL);

                            String lang = plugin.getStorage().getFiles().getControl(GuardianFiles.SETTINGS).getString("settings.language","en");

                            plugin.getStorage().getFiles().setMessages(lang);

                            plugin.update(plugin.getStorage().getFiles().getControl(GuardianFiles.SETTINGS));

                            plugin.getPing().update();

                            plugin.getStorage().updatePriority();

                            plugin.getWhitelist().update();
                            
                        }catch (Exception exception) {
                            plugin.getStorage().getLogs().error("Something bad happened, maybe the plugin is broken, please check if you have all without issues");
                            plugin.getStorage().getLogs().error("If you are sure than this isn't your error, please contact the developer.");
                            plugin.getStorage().getLogs().error(exception);
                        }
                        String reload = plugin.getStorage().getFiles().getControl(GuardianFiles.MESSAGES).getString("messages.reload","&aThe plugin was reloaded correctly in <ms>ms.");
                        reload = reload.replace("<ms>", (System.currentTimeMillis() - timeMS) + "");
                        sendMessage(sender,reload);
                    }
                    return;
                }

                if (args[1].equalsIgnoreCase("whitelist")) {
                    if (hasPermission(sender,"pmotd.admin.help.whitelist",true) || hasPermission(sender,"pmotd.admin.help.*",true)) {
                        whitelist.usage(sender,getArguments(args));
                    }
                    return;
                }
                if (args[1].equalsIgnoreCase("blacklist")) {
                    if (hasPermission(sender,"pmotd.admin.help.blacklist",true) || hasPermission(sender,"pmotd.admin.help.*",true)) {
                        blacklist.usage(sender,getArguments(args));
                    }
                }
            }
        } catch (Exception exception) {
            plugin.getStorage().getLogs().error(exception);
        }
    }

    private String[] getArguments(String[] args) {
        String[] arguments = new String[args.length - 2];
        int argID = 0;
        int aID = 0;
        for (String arg : args) {
            if (aID != 0 && aID != 1) {
                arguments[argID] = arg;
                argID++;
            }
            aID++;
        }
        return arguments;
    }
}
