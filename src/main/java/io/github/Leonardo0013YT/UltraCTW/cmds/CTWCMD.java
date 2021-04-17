package io.github.Leonardo0013YT.UltraCTW.cmds;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.enums.State;
import io.github.Leonardo0013YT.UltraCTW.game.GameFlag;
import io.github.Leonardo0013YT.UltraCTW.game.GamePlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CTWCMD implements CommandExecutor {

    private UltraCTW plugin;

    public CTWCMD(UltraCTW plugin) {
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
                case "buffdebuff": {
                    GameFlag gf = plugin.getGm().getGameFlagByPlayer(p);
                    if (gf != null) {
                        plugin.getFgm().createMainBuffDebuffMenu(p);
                    }
                    break;
                }
                case "upgrades": {
                    GameFlag gf = plugin.getGm().getGameFlagByPlayer(p);
                    if (gf != null) {
                        plugin.getFgm().createMainUpgradeMenu(p);
                    }
                    break;
                }
                case "kits": {
                    Game g = plugin.getGm().getGameByPlayer(p);
                    if (g != null){
                        plugin.getUim().getPages().put(p, 1);
                        plugin.getUim().createKitSelectorMenu(p);
                    } else {
                        GameFlag gf = plugin.getGm().getGameFlagByPlayer(p);
                        if (gf != null) {
                            plugin.getUim().getPages().put(p, 1);
                            plugin.getUim().createKitSelectorMenu(p);
                        }
                    }
                    break;
                }
                case "shop": {
                    Game g = plugin.getGm().getGameByPlayer(p);
                    if (g != null) {
                        plugin.getGem().createShopMenu(p);
                    } else {
                        GameFlag gf = plugin.getGm().getGameFlagByPlayer(p);
                        if (gf != null) {
                            plugin.getGem().createShopMenu(p);
                        }
                    }
                    break;
                }
                case "stats":
                    p.sendMessage(plugin.getLang().get(p, "stats"));
                    break;
                case "lobby":
                    plugin.getUim().openContentInventory(p, plugin.getUim().getMenus("lobby"));
                    break;
                case "gcoins":
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
                            Game gpgg = plugin.getGm().getGameByPlayer(on);
                            GameFlag gpgf = plugin.getGm().getGameFlagByPlayer(on);
                            GamePlayer gp;
                            if (gpgg != null) {
                                gp = gpgg.getGamePlayer(on);
                            } else {
                                gp = gpgf.getGamePlayer(on);
                            }
                            gp.addCoins(amount);
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
                            Game gpgg1 = plugin.getGm().getGameByPlayer(on1);
                            GameFlag gpgf1 = plugin.getGm().getGameFlagByPlayer(on1);
                            GamePlayer gp1;
                            if (gpgg1 != null) {
                                gp1 = gpgg1.getGamePlayer(on1);
                            } else {
                                gp1 = gpgf1.getGamePlayer(on1);
                            }
                            gp1.removeCoins(amount1);
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
                            Game gpgg2 = plugin.getGm().getGameByPlayer(on2);
                            GameFlag gpgf2 = plugin.getGm().getGameFlagByPlayer(on2);
                            GamePlayer gp2;
                            if (gpgg2 != null) {
                                gp2 = gpgg2.getGamePlayer(on2);
                            } else {
                                gp2 = gpgf2.getGamePlayer(on2);
                            }
                            gp2.setCoins(amount2);
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
                    if (plugin.getCm().isBungeeModeEnabled() && plugin.getCm().isSendLobbyOnQuit()) {
                        plugin.sendToServer(p, plugin.getCm().getBungeeModeLobbyServer());
                    }
                    break;
                case "randomjoin":
                    if (args.length < 2) {
                        sendHelp(sender);
                        return true;
                    }
                    String type = args[1].toLowerCase();
                    switch (type) {
                        case "wool":
                            if (plugin.getGm().isPlayerInGame(p)) {
                                p.sendMessage(plugin.getLang().get("messages.alreadyIngame"));
                                return true;
                            }
                            Game selected2 = plugin.getGm().getSelectedGame();
                            if (selected2 == null) return true;
                            if (selected2.getPlayers().size() >= selected2.getMax()) {
                                p.sendMessage(plugin.getLang().get("messages.maxPlayers"));
                                return true;
                            }
                            plugin.getGm().addPlayerGame(p, selected2.getId());
                            break;
                        case "flag":
                            if (plugin.getGm().isPlayerInGame(p)) {
                                p.sendMessage(plugin.getLang().get("messages.alreadyIngame"));
                                return true;
                            }
                            GameFlag selected3 = plugin.getGm().getRandomGameFlag();
                            if (selected3 == null) return true;
                            plugin.getGm().addPlayerGameFlag(p, selected3.getId());
                            break;
                        default:
                            sendHelp(sender);
                            break;
                    }
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
                        if (selected.getPlayers().size() >= selected.getMax()) {
                            p.sendMessage(plugin.getLang().get("messages.maxPlayers"));
                            return true;
                        }
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
                case "joinflag":
                    if (args.length < 2) {
                        sendHelp(sender);
                        return true;
                    }
                    if (plugin.getGm().isPlayerInGame(p)) {
                        p.sendMessage(plugin.getLang().get("messages.alreadyIngame"));
                        return true;
                    }
                    String arena2 = args[1];
                    GameFlag found2 = plugin.getGm().getGameFlagByName(arena2);
                    if (found2 == null) {
                        p.sendMessage(plugin.getLang().get("messages.gameNotExists"));
                        return true;
                    }
                    if (found2.isState(State.GAME)) {
                        p.sendMessage(plugin.getLang().get("messages.alreadyStart"));
                        return true;
                    }
                    plugin.getGm().addPlayerGameFlag(p, found2.getId());
                    break;
                case "kitsmenu":
                    plugin.getUim().getPages().put(p, 1);
                    Game game2 = plugin.getGm().getGameByPlayer(p);
                    if (game2 == null) {
                        plugin.getUim().createKitFlagSelectorMenu(p);
                        return true;
                    }
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
                case "shopkeepermenu":
                    plugin.getUim().getPages().put(p, 1);
                    plugin.getUim().createShopKeeperSelectorMenu(p);
                    break;
                case "multiplier":
                    if (!p.hasPermission("ctw.admin")) {
                        p.sendMessage(plugin.getLang().get(p, "messages.noPermission"));
                        return true;
                    }
                    if (args.length < 5) {
                        sendHelp(sender);
                        return true;
                    }
                    switch (args[1].toLowerCase()) {
                        case "coins":
                            Player on = Bukkit.getPlayer(args[2]);
                            if (on == null) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noOnline"));
                                return true;
                            }
                            double amount;
                            try {
                                amount = Double.parseDouble(args[3]);
                            } catch (NumberFormatException e) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                                return true;
                            }
                            int seconds;
                            try {
                                seconds = Integer.parseInt(args[4]);
                            } catch (NumberFormatException e) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                                return true;
                            }
                            plugin.getDb().createMultiplier("COINS", on.getName(), amount, System.currentTimeMillis() + (seconds * 1000), b -> {
                                if (b) {
                                    plugin.getDb().loadMultipliers(b1 -> {
                                        if (b1) {
                                            p.sendMessage(plugin.getLang().get(p, "messages.multiplier").replaceAll("<type>", "Coins").replace("<name>", on.getName()).replace("<amount>", String.valueOf(amount)).replace("<time>", Utils.convertTime(seconds)));
                                        }
                                    });
                                }
                            });
                            break;
                        case "xp":
                            Player on3 = Bukkit.getPlayer(args[2]);
                            if (on3 == null) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noOnline"));
                                return true;
                            }
                            double amount3;
                            try {
                                amount3 = Double.parseDouble(args[3]);
                            } catch (NumberFormatException e) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                                return true;
                            }
                            int seconds3;
                            try {
                                seconds3 = Integer.parseInt(args[4]);
                            } catch (NumberFormatException e) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                                return true;
                            }
                            plugin.getDb().createMultiplier("XP", on3.getName(), amount3, System.currentTimeMillis() + (seconds3 * 1000), b -> {
                                if (b) {
                                    plugin.getDb().loadMultipliers(b1 -> {
                                        if (b1) {
                                            p.sendMessage(plugin.getLang().get(p, "messages.multiplier").replaceAll("<type>", "XP").replace("<name>", on3.getName()).replace("<amount>", String.valueOf(amount3)).replace("<time>", Utils.convertTime(seconds3)));
                                        }
                                    });
                                }
                            });
                            break;
                        default:
                            sendHelp(sender);
                            break;
                    }
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
        s.sendMessage("§e/ctw joinflag <name> §a- §bJoin to flag arena with name.");
        s.sendMessage("§e/ctw randomjoin <wool/flag>§a- §bRandom Join to arena.");
        s.sendMessage("§e/ctw leave §a- §bLeave the game.");
        s.sendMessage("§e/ctw coins add/remove/set <player> <coins> §7- §aHandles the addition, removal and set of coins.");
        s.sendMessage("§e/ctw gcoins add/remove/set <player> <coins> §7- §aHandles the addition, removal and set of coins.");
        s.sendMessage("§e/ctw multiplier <type> <player> <amount> <time> §7- §aCreate a multiplier of type with custom amount and duration.");
        s.sendMessage("§7§m-----------------------------");
    }

}