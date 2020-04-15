package io.github.Leonardo0013YT.UltraCTW.cmds;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.setup.ArenaSetup;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetupCMD implements CommandExecutor {

    private Main plugin;

    public SetupCMD(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player)sender;
            if (args.length < 1){
                sendHelp(sender);
                return true;
            }
            switch (args[0].toLowerCase()) {
                case "create":
                    if (args.length < 3){
                        sendHelp(sender);
                        return true;
                    }
                    if (plugin.getSm().isSetup(p)) {
                        p.sendMessage(plugin.getLang().get(p, "setup.alreadyCreating"));
                        return true;
                    }
                    String name = args[1];
                    String schematic;
                    if (args[2].endsWith(".schematic")) {
                        schematic = args[2];
                    } else {
                        schematic = args[2] + ".schematic";
                    }
                    if (!Utils.existsFile(schematic)) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noSchema"));
                        return true;
                    }
                    plugin.getSm().setSetup(p, new ArenaSetup(plugin, p, name, schematic));
                    World w = plugin.getWc().createEmptyWorld(name);
                    w.getBlockAt(0, 75, 0).setType(Material.STONE);
                    plugin.getWc().resetMap(w.getSpawnLocation(), schematic);
                    p.teleport(w.getSpawnLocation());
                    p.getInventory().remove(plugin.getIm().getSetup());
                    p.getInventory().addItem(plugin.getIm().getSetup());
                    break;
                default:
                    sendHelp(sender);
                    break;
            }
        }
        return false;
    }

    private void sendHelp(CommandSender s){
        s.sendMessage("§7§m--------------------------------");
        s.sendMessage("§e/ctws create <name> <schematic> §7- §aCreate a new arena.");
        s.sendMessage("§7§m--------------------------------");
    }

}