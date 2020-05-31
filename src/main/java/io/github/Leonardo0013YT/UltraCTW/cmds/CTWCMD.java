package io.github.Leonardo0013YT.UltraCTW.cmds;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
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
        if (sender instanceof Player){
            Player p = (Player) sender;
            if (args.length < 1){
                sendHelp(sender);
                return true;
            }
            switch (args[0].toLowerCase()) {
                case "leave":
                    plugin.getGm().removePlayerGame(p, true);
                    p.sendMessage(plugin.getLang().get("messages.leaveGame"));
                    break;
                case "join":
                    if (args.length < 2){
                        sendHelp(sender);
                        return true;
                    }
                    if (plugin.getGm().isPlayerInGame(p)){
                        p.sendMessage(plugin.getLang().get("messages.alreadyIngame"));
                        return true;
                    }
                    String arena = args[1];
                    Game found = plugin.getGm().getGameByName(arena);
                    if (found == null){
                        p.sendMessage(plugin.getLang().get("messages.gameNotExists"));
                        return true;
                    }
                    plugin.getGm().addPlayerGame(p, found.getId());
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

    private void sendHelp(CommandSender s){
        s.sendMessage("§7§m-----------------------------");
        s.sendMessage("§e/ctw join <name> §a- §bJoin to arena with name.");
        s.sendMessage("§7§m-----------------------------");
    }

}