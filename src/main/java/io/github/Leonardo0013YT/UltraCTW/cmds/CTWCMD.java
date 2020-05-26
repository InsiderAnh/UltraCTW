package io.github.Leonardo0013YT.UltraCTW.cmds;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.game.Game;
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
                case "join":
                    if (args.length < 2){
                        sendHelp(sender);
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