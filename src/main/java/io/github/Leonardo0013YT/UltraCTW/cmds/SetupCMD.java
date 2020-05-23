package io.github.Leonardo0013YT.UltraCTW.cmds;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.UltraInventory;
import io.github.Leonardo0013YT.UltraCTW.setup.ArenaSetup;
import io.github.Leonardo0013YT.UltraCTW.setup.KitSetup;
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
                    w.setSpawnLocation(0, 75, 0);
                    plugin.getWc().resetMap(w.getSpawnLocation(), schematic);
                    p.teleport(w.getSpawnLocation());
                    p.getInventory().remove(plugin.getIm().getSetup());
                    p.getInventory().remove(plugin.getIm().getPoints());
                    p.getInventory().addItem(plugin.getIm().getPoints());
                    p.getInventory().addItem(plugin.getIm().getSetup());
                    break;
                case "kits":
                    if (args.length < 2){
                        sendHelp(sender);
                        return true;
                    }
                    if (plugin.getSm().isSetupKit(p)) {
                        plugin.getSem().createSetupKitMenu(p, plugin.getSm().getSetupKit(p));
                        return true;
                    }
                    String kitName = args[1];
                    p.sendMessage(plugin.getLang().get(p, "setup.kits.created"));
                    plugin.getSm().setSetupKit(p, new KitSetup(plugin, kitName));
                    plugin.getSem().createSetupKitMenu(p, plugin.getSm().getSetupKit(p));
                    break;
                case "setmainlobby":

                    break;
                case "inventory":
                    if (args.length < 2) {
                        sendHelp(p);
                        return true;
                    }
                    switch (args[1].toLowerCase()) {
                        case "setup":
                        case "teamsetup":
                            UltraInventory inv = plugin.getUim().getMenus(args[1].toLowerCase());
                            plugin.getUim().openInventory(p, inv);
                            plugin.getSm().setSetupInventory(p, inv);
                            break;
                        default:
                            p.sendMessage("§cThe available menus are:");
                            p.sendMessage("§7 - §eSetup");
                            p.sendMessage("§7 - §eTeamSetup");
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

    private void sendHelp(CommandSender s){
        s.sendMessage("§7§m--------------------------------");
        s.sendMessage("§e/ctws create <name> <schematic> §7- §aCreate a new arena.");
        s.sendMessage("§e/ctws kits <name> §7- §aCreate a new kit.");
        s.sendMessage("§e/ctws inventory <type> §7- §aEdit a inventory.");
        s.sendMessage("§7§m--------------------------------");
    }

}