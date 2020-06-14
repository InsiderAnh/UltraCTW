package io.github.Leonardo0013YT.UltraCTW.cmds;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.enums.NPCType;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CTWCMD implements CommandExecutor {

    private Main plugin;

    public CTWCMD(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length < 1) {
                sendHelp(sender);
                return true;
            }
            switch (args[0].toLowerCase()) {
                case "lobby":
                    plugin.getUim().openContentInventory(p, plugin.getUim().getMenus("lobby"));
                    break;
                case "coins":
                    if (args.length < 4) {
                        sendHelp(sender);
                        return true;
                    }
                    if (!p.hasPermission("ctw.admin")) {
                        p.sendMessage(plugin.getLang().get(p, "messages.noPermission"));
                        return true;
                    }
                    switch (args[1].toLowerCase()) {
                        case "add":
                            Player on = Bukkit.getPlayer(args[2]);
                            if (on == null) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noOnline"));
                                return true;
                            }
                            int amount;
                            try {
                                amount = Integer.parseInt(args[3]);
                            } catch (NumberFormatException e) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                                return true;
                            }
                            CTWPlayer sw = plugin.getDb().getCTWPlayer(on);
                            sw.addCoins(amount);
                            p.sendMessage(plugin.getLang().get(p, "coins.add.you").replaceAll("<coins>", String.valueOf(amount)).replaceAll("<player>", on.getName()));
                            on.sendMessage(plugin.getLang().get(p, "coins.add.receiver").replaceAll("<coins>", String.valueOf(amount)).replaceAll("<sender>", p.getName()));
                            Utils.updateSB(on);
                            break;
                        case "remove":
                            Player on1 = Bukkit.getPlayer(args[2]);
                            if (on1 == null) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noOnline"));
                                return true;
                            }
                            int amount1;
                            try {
                                amount1 = Integer.parseInt(args[3]);
                            } catch (NumberFormatException e) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                                return true;
                            }
                            CTWPlayer sw1 = plugin.getDb().getCTWPlayer(on1);
                            sw1.removeCoins(amount1);
                            p.sendMessage(plugin.getLang().get(p, "coins.remove.you").replaceAll("<coins>", String.valueOf(amount1)).replaceAll("<player>", on1.getName()));
                            on1.sendMessage(plugin.getLang().get(p, "coins.remove.receiver").replaceAll("<coins>", String.valueOf(amount1)).replaceAll("<sender>", p.getName()));
                            Utils.updateSB(on1);
                            break;
                        case "set":
                            Player on2 = Bukkit.getPlayer(args[2]);
                            if (on2 == null) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noOnline"));
                                return true;
                            }
                            int amount2;
                            try {
                                amount2 = Integer.parseInt(args[3]);
                            } catch (NumberFormatException e) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                                return true;
                            }
                            CTWPlayer sw2 = plugin.getDb().getCTWPlayer(on2);
                            sw2.setCoins(amount2);
                            p.sendMessage(plugin.getLang().get(p, "coins.set.you").replaceAll("<coins>", String.valueOf(amount2)).replaceAll("<player>", on2.getName()));
                            on2.sendMessage(plugin.getLang().get(p, "coins.set.receiver").replaceAll("<coins>", String.valueOf(amount2)).replaceAll("<sender>", p.getName()));
                            Utils.updateSB(on2);
                            break;
                        default:
                            sendHelp(sender);
                            break;
                    }
                    break;
                case "leave":
                    plugin.getGm().removePlayerGame(p, true);
                    p.sendMessage(plugin.getLang().get("messages.leaveGame"));
                    break;
                case "randomjoin":
                    if (plugin.getGm().isPlayerInGame(p)) {
                        p.sendMessage(plugin.getLang().get("messages.alreadyIngame"));
                        return true;
                    }
                    Game selected2 = plugin.getGm().getSelectedGame();
                    if (selected2 == null) return true;
                    plugin.getGm().addPlayerGame(p, selected2.getId());
                    break;
                case "join":
                    if (args.length < 2) {
                        sendHelp(sender);
                        return true;
                    }
                    if (plugin.getGm().isPlayerInGame(p)) {
                        p.sendMessage(plugin.getLang().get("messages.alreadyIngame"));
                        return true;
                    }
                    if (plugin.getGm().getSelectedGame() != null) {
                        Game selected = plugin.getGm().getSelectedGame();
                        plugin.getGm().addPlayerGame(p, selected.getId());
                        p.sendMessage(plugin.getLang().get("messages.noOther"));
                        return true;
                    }
                    String arena = args[1];
                    Game found = plugin.getGm().getGameByName(arena);
                    if (found == null) {
                        p.sendMessage(plugin.getLang().get("messages.gameNotExists"));
                        return true;
                    }
                    plugin.getGm().addPlayerGame(p, found.getId());
                    break;
                case "kitsmenu":
                    plugin.getUim().getPages().put(p, 1);
                    plugin.getUim().createKitSelectorMenu(p);
                    break;
                case "killsoundsmenu":
                    plugin.getUim().getPages().put(p, 1);
                    plugin.getUim().createKillSoundSelectorMenu(p);
                    break;
                case "killeffectsmenu":
                    plugin.getUim().getPages().put(p, 1);
                    plugin.getUim().createKillEffectSelectorMenu(p);
                    break;
                case "windancesmenu":
                    plugin.getUim().getPages().put(p, 1);
                    plugin.getUim().createWinDanceSelectorMenu(p);
                    break;
                case "wineffectsmenu":
                    plugin.getUim().getPages().put(p, 1);
                    plugin.getUim().createWinEffectSelectorMenu(p);
                    break;
                case "trailsmenu":
                    plugin.getUim().getPages().put(p, 1);
                    plugin.getUim().createTrailsSelectorMenu(p);
                    break;
                case "tauntsmenu":
                    plugin.getUim().getPages().put(p, 1);
                    plugin.getUim().createTauntsSelectorMenu(p);
                    break;
                default:
                    sendHelp(sender);
                    break;
            }
        }
        return false;
    }

    private void sendHelp(CommandSender s) {
        s.sendMessage("§7§m-----------------------------");
        s.sendMessage("§e/ctw lobby §a- §bOpen the lobby menu.");
        s.sendMessage("§e/ctw join <name> §a- §bJoin to arena with name.");
        s.sendMessage("§e/ctw leave §a- §bLeave the game.");
        s.sendMessage("§e/ctw coins add/remove/set <player> <coins> §7- §aHandles the addition, removal and set of coins.");
        s.sendMessage("§7§m-----------------------------");
    }

}