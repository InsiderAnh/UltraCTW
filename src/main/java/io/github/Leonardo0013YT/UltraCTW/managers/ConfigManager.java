package io.github.Leonardo0013YT.UltraSkyWars.managers;

import io.github.Leonardo0013YT.UltraSkyWars.Main;
import io.github.Leonardo0013YT.UltraSkyWars.enums.GameType;
import io.github.Leonardo0013YT.UltraSkyWars.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ConfigManager {

    private ItemStack head;
    private byte waitingState, startingState, emptyState;
    private short redPanelData;
    private int gamesToRestart, itemLobbySlot, ticksAni3, executesAni3, winTime, progressBarAmount, ticksAni1, upYAni1, cubeletChance, maxMultiplier, starting, pregame, coinsKill, coinsWin, coinsAssists, xpKill, xpWin, xpAssists, soulsKill, soulsWin, soulsAssists;
    private Sound wineffectschicken, wineffectsvulcanfire, wineffectvulcanwool, wineffectnotes, cubeletReward, cubeletAni3, cubeletAni1, noSouls, common, uncommon, rare, epic, legendary, upgradeSound, degradeSound, startingSound, pregameSound, cancelStartSound, dragonSound, refillSound, noneSound, tntSound, witherSound, zombieSound, killEffectTNT, killEffectSquid, soulWell;
    private Material back, lobby, team, redPanelMaterial, spectate, options, play, leave, kits, votes, setup, center, island, soulwell;
    private boolean leaderheads, MVdWPlaceholderAPI, damageProtect, lobbyProtection, lobbyVoidTeleport, lobbyJoinTeleport, preLobbySystem, partyandfriends, kitLevelsOrder, slimeworldmanager, bungeeModeEnabled, bungeeModeLobby, bungeeModeGame, itemLobbyEnabled, redPanelInLocked, lobbyScoreboard, placeholdersAPI, tabapi, cubeletsEnabled, soulwellEnabled, parties, chatSystem, holograms, holographicdisplays, signsRight, signsLeft, vault, playerpoints, coins, nametagedit;
    private String lobbyWorld, slimeworldmanagerLoader, perm, url, itemLobbyCMD, lobbyServer, gameServer;
    private GameType gameType;
    private List<String> whitelistedCMD;
    private Main plugin;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        this.wineffectschicken = Sound.valueOf(plugin.getConfig().getString("sounds.wineffects.chicken"));
        this.wineffectnotes = Sound.valueOf(plugin.getConfig().getString("sounds.wineffects.notes"));
        this.wineffectsvulcanfire = Sound.valueOf(plugin.getConfig().getString("sounds.wineffects.vulcanfire"));
        this.wineffectvulcanwool = Sound.valueOf(plugin.getConfig().getString("sounds.wineffects.vulcanwool"));
        this.lobbyWorld = plugin.getConfig().getString("lobbyProtection.lobbyWorld");
        this.damageProtect = plugin.getConfig().getBoolean("lobbyProtection.damageProtect");
        this.lobbyProtection = plugin.getConfig().getBoolean("lobbyProtection.enabled");
        this.lobbyVoidTeleport = plugin.getConfig().getBoolean("lobbyProtection.voidTeleport");
        this.lobbyJoinTeleport = plugin.getConfig().getBoolean("lobbyProtection.joinTeleport");
        this.preLobbySystem = plugin.getConfig().getBoolean("preLobbySystem");
        this.kitLevelsOrder = plugin.getConfig().getBoolean("kitLevelsOrder");
        this.whitelistedCMD = plugin.getConfig().getStringList("whitelistedCMD");
        this.gamesToRestart = plugin.getConfig().getInt("bungee.gamesToRestart");
        this.gameServer = plugin.getConfig().getString("bungee.gameServer");
        this.lobbyServer = plugin.getConfig().getString("bungee.lobbyServer");
        this.gameType = GameType.valueOf(plugin.getConfig().getString("bungee.gameType"));
        this.bungeeModeEnabled = plugin.getConfig().getBoolean("bungee.enabled");
        this.bungeeModeLobby = plugin.getConfig().getBoolean("bungee.lobby");
        this.bungeeModeGame = plugin.getConfig().getBoolean("bungee.game");
        this.lobby = Material.valueOf(plugin.getConfig().getString("materials.lobby"));
        this.itemLobbyEnabled = plugin.getConfig().getBoolean("items.lobby.enabled");
        this.itemLobbySlot = plugin.getConfig().getInt("items.lobby.slot");
        this.itemLobbyCMD = plugin.getConfig().getString("items.lobby.cmd");
        this.ticksAni3 = plugin.getCubelets().getInt("animations.flames.ticks");
        this.executesAni3 = plugin.getCubelets().getInt("animations.flames.executes");
        this.winTime = plugin.getConfig().getInt("gameDefaults.winTime");
        this.redPanelData = (short) plugin.getConfig().getInt("redPanel.data");
        this.redPanelMaterial = Material.valueOf(plugin.getConfig().getString("redPanel.material"));
        this.redPanelInLocked = plugin.getConfig().getBoolean("redPanelInLocked");
        this.progressBarAmount = plugin.getConfig().getInt("progressBarAmount");
        this.url = plugin.getCubelets().get(null, "animations.head.url");
        this.head = ItemBuilder.createSkull("§7", "§7", plugin.getCubelets().get(null, "animations.head.url"));
        this.upYAni1 = plugin.getCubelets().getInt("animations.fireworks.upY");
        this.ticksAni1 = plugin.getCubelets().getInt("animations.fireworks.ticks");
        this.cubeletsEnabled = plugin.getConfig().getBoolean("rewards.cubelets");
        this.soulwellEnabled = plugin.getConfig().getBoolean("rewards.soulwell");
        this.lobbyScoreboard = plugin.getConfig().getBoolean("lobbyScoreboard");
        this.chatSystem = plugin.getConfig().getBoolean("chatSystem");
        this.slimeworldmanagerLoader = plugin.getConfig().getString("addons.slimeworldmanager.loader");
        this.slimeworldmanager = plugin.getConfig().getBoolean("addons.slimeworldmanager.enabled");
        this.partyandfriends = plugin.getConfig().getBoolean("addons.partyandfriends");
        this.leaderheads = plugin.getConfig().getBoolean("addons.leaderheads");
        this.parties = plugin.getConfig().getBoolean("addons.parties");
        this.nametagedit = plugin.getConfig().getBoolean("addons.nametagedit");
        this.holograms = plugin.getConfig().getBoolean("addons.holograms");
        this.holographicdisplays = plugin.getConfig().getBoolean("addons.holographicdisplays");
        this.perm = plugin.getConfig().getString("permCMD");
        this.MVdWPlaceholderAPI = plugin.getConfig().getBoolean("addons.MVdWPlaceholderAPI");
        this.placeholdersAPI = plugin.getConfig().getBoolean("addons.placeholdersAPI");
        this.tabapi = plugin.getConfig().getBoolean("addons.tabapi");
        this.vault = plugin.getConfig().getBoolean("addons.vault");
        this.playerpoints = plugin.getConfig().getBoolean("addons.playerpoints");
        this.coins = plugin.getConfig().getBoolean("addons.coins");
        this.signsRight = plugin.getConfig().getBoolean("signs.right");
        this.signsLeft = plugin.getConfig().getBoolean("signs.left");
        this.waitingState = (byte) plugin.getConfig().getInt("states.waiting");
        this.startingState = (byte) plugin.getConfig().getInt("states.starting");
        this.emptyState = (byte) plugin.getConfig().getInt("states.empty");
        this.maxMultiplier = plugin.getConfig().getInt("gameDefaults.maxMultiplier");
        this.cubeletChance = plugin.getConfig().getInt("gameDefaults.cubeletsChance");
        this.starting = plugin.getConfig().getInt("gameDefaults.starting");
        this.pregame = plugin.getConfig().getInt("gameDefaults.pregame");
        this.coinsKill = plugin.getConfig().getInt("gameDefaults.coins.kill");
        this.coinsWin = plugin.getConfig().getInt("gameDefaults.coins.win");
        this.coinsAssists = plugin.getConfig().getInt("gameDefaults.coins.assists");
        this.xpKill = plugin.getConfig().getInt("gameDefaults.xp.kill");
        this.xpWin = plugin.getConfig().getInt("gameDefaults.xp.win");
        this.xpAssists = plugin.getConfig().getInt("gameDefaults.xp.assists");
        this.soulsKill = plugin.getConfig().getInt("gameDefaults.souls.kill");
        this.soulsWin = plugin.getConfig().getInt("gameDefaults.souls.win");
        this.soulsAssists = plugin.getConfig().getInt("gameDefaults.souls.assists");
        this.cubeletReward = Sound.valueOf(plugin.getConfig().getString("sounds.cubelets.reward"));
        this.cubeletAni3 = Sound.valueOf(plugin.getConfig().getString("sounds.cubelets.ani3"));
        this.cubeletAni1 = Sound.valueOf(plugin.getConfig().getString("sounds.cubelets.ani1"));
        this.noSouls = Sound.valueOf(plugin.getConfig().getString("sounds.noSouls"));
        this.common = Sound.valueOf(plugin.getConfig().getString("sounds.rewards.common"));
        this.uncommon = Sound.valueOf(plugin.getConfig().getString("sounds.rewards.uncommon"));
        this.rare = Sound.valueOf(plugin.getConfig().getString("sounds.rewards.rare"));
        this.epic = Sound.valueOf(plugin.getConfig().getString("sounds.rewards.epic"));
        this.legendary = Sound.valueOf(plugin.getConfig().getString("sounds.rewards.legendary"));
        this.soulWell = Sound.valueOf(plugin.getConfig().getString("sounds.soulWell"));
        this.killEffectTNT = Sound.valueOf(plugin.getConfig().getString("sounds.killeffects.tnt"));
        this.killEffectSquid = Sound.valueOf(plugin.getConfig().getString("sounds.killeffects.squid"));
        this.upgradeSound = Sound.valueOf(plugin.getConfig().getString("sounds.upgrade"));
        this.degradeSound = Sound.valueOf(plugin.getConfig().getString("sounds.degrade"));
        this.startingSound = Sound.valueOf(plugin.getConfig().getString("sounds.starting"));
        this.pregameSound = Sound.valueOf(plugin.getConfig().getString("sounds.pregame"));
        this.cancelStartSound = Sound.valueOf(plugin.getConfig().getString("sounds.cancelStart"));
        this.dragonSound = Sound.valueOf(plugin.getConfig().getString("sounds.events.dragon"));
        this.refillSound = Sound.valueOf(plugin.getConfig().getString("sounds.events.refill"));
        this.noneSound = Sound.valueOf(plugin.getConfig().getString("sounds.events.none"));
        this.tntSound = Sound.valueOf(plugin.getConfig().getString("sounds.events.tnt"));
        this.witherSound = Sound.valueOf(plugin.getConfig().getString("sounds.events.wither"));
        this.zombieSound = Sound.valueOf(plugin.getConfig().getString("sounds.events.zombie"));
        this.soulwell = Material.valueOf(plugin.getConfig().getString("materials.soulwell"));
        this.team = Material.valueOf(plugin.getConfig().getString("materials.team"));
        this.spectate = Material.valueOf(plugin.getConfig().getString("materials.spectate"));
        this.options = Material.valueOf(plugin.getConfig().getString("materials.options"));
        this.play = Material.valueOf(plugin.getConfig().getString("materials.play"));
        this.leave = Material.valueOf(plugin.getConfig().getString("materials.leave"));
        this.kits = Material.valueOf(plugin.getConfig().getString("materials.kits"));
        this.votes = Material.valueOf(plugin.getConfig().getString("materials.votes"));
        this.setup = Material.valueOf(plugin.getConfig().getString("materials.setup"));
        this.center = Material.valueOf(plugin.getConfig().getString("materials.center"));
        this.island = Material.valueOf(plugin.getConfig().getString("materials.island"));
        this.back = Material.valueOf(plugin.getConfig().getString("materials.closeitem"));
    }

    public Material getBack() {
        return back;
    }

    public boolean isLeaderheads() {
        return leaderheads;
    }

    public boolean isMVdWPlaceholderAPI() {
        return MVdWPlaceholderAPI;
    }

    public Sound getWineffectschicken() {
        return wineffectschicken;
    }

    public Sound getWineffectnotes() {
        return wineffectnotes;
    }

    public Sound getWineffectvulcanwool() {
        return wineffectvulcanwool;
    }

    public Sound getWineffectsvulcanfire() {
        return wineffectsvulcanfire;
    }

    public String getLobbyWorld() {
        return lobbyWorld;
    }

    public boolean isDamageProtect() {
        return damageProtect;
    }

    public boolean isLobbyJoinTeleport() {
        return lobbyJoinTeleport;
    }

    public boolean isLobbyProtection() {
        return lobbyProtection;
    }

    public boolean isLobbyVoidTeleport() {
        return lobbyVoidTeleport;
    }

    public boolean isPreLobbySystem() {
        return preLobbySystem;
    }

    public boolean isPartyandfriends() {
        return partyandfriends;
    }

    public boolean isKitLevelsOrder() {
        return kitLevelsOrder;
    }

    public int getGamesToRestart() {
        return gamesToRestart;
    }

    public boolean isSlimeworldmanager() {
        return slimeworldmanager;
    }

    public String getSlimeworldmanagerLoader() {
        return slimeworldmanagerLoader;
    }

    public GameType getGameType() {
        return gameType;
    }

    public String getGameServer() {
        return gameServer;
    }

    public List<String> getWhitelistedCMD() {
        return whitelistedCMD;
    }

    public String getLobbyServer() {
        return lobbyServer;
    }

    public boolean isBungeeModeEnabled() {
        return bungeeModeEnabled;
    }

    public boolean isBungeeModeLobby() {
        return bungeeModeLobby;
    }

    public boolean isBungeeModeGame() {
        return bungeeModeGame;
    }

    public Material getLobby() {
        return lobby;
    }

    public boolean isItemLobbyEnabled() {
        return itemLobbyEnabled;
    }

    public String getItemLobbyCMD() {
        return itemLobbyCMD;
    }

    public int getItemLobbySlot() {
        return itemLobbySlot;
    }

    public Material getTeam() {
        return team;
    }

    public Material getRedPanelMaterial() {
        return redPanelMaterial;
    }

    public short getRedPanelData() {
        return redPanelData;
    }

    public boolean isRedPanelInLocked() {
        return redPanelInLocked;
    }

    public boolean isLobbyScoreboard() {
        return lobbyScoreboard;
    }

    public boolean isPlaceholdersAPI() {
        return placeholdersAPI;
    }

    public boolean isTabapi() {
        return tabapi;
    }

    public boolean isCubeletsEnabled() {
        return cubeletsEnabled;
    }

    public boolean isSoulwellEnabled() {
        return soulwellEnabled;
    }

    public boolean isParties() {
        return parties;
    }

    public boolean isNametagedit() {
        return nametagedit;
    }

    public boolean isChatSystem() {
        return chatSystem;
    }

    public boolean isHolograms() {
        return holograms;
    }

    public boolean isHolographicdisplays() {
        return holographicdisplays;
    }

    public boolean isVault() {
        return vault;
    }

    public boolean isPlayerpoints() {
        return playerpoints;
    }

    public boolean isCoins() {
        return coins;
    }

    public boolean isSignsLeft() {
        return signsLeft;
    }

    public boolean isSignsRight() {
        return signsRight;
    }

    public Material getSoulwell() {
        return soulwell;
    }

    public Material getSpectate() {
        return spectate;
    }

    public Material getOptions() {
        return options;
    }

    public Material getPlay() {
        return play;
    }

    public Material getLeave() {
        return leave;
    }

    public Material getKits() {
        return kits;
    }

    public Material getVotes() {
        return votes;
    }

    public Material getSetup() {
        return setup;
    }

    public Material getCenter() {
        return center;
    }

    public Material getIsland() {
        return island;
    }

    public byte getWaitingState() {
        return waitingState;
    }

    public byte getStartingState() {
        return startingState;
    }

    public byte getEmptyState() {
        return emptyState;
    }

    public int getMaxMultiplier() {
        return maxMultiplier;
    }

    public Sound getCubeletAni3() {
        return cubeletAni3;
    }

    public Sound getCubeletReward() {
        return cubeletReward;
    }

    public Sound getCubeletAni1() {
        return cubeletAni1;
    }

    public Sound getNoSouls() {
        return noSouls;
    }

    public Sound getCommon() {
        return common;
    }

    public Sound getUncommon() {
        return uncommon;
    }

    public Sound getRare() {
        return rare;
    }

    public Sound getEpic() {
        return epic;
    }

    public Sound getLegendary() {
        return legendary;
    }

    public Sound getSoulWell() {
        return soulWell;
    }

    public Sound getKillEffectTNT() {
        return killEffectTNT;
    }

    public Sound getKillEffectSquid() {
        return killEffectSquid;
    }

    public Sound getUpgradeSound() {
        return upgradeSound;
    }

    public Sound getDegradeSound() {
        return degradeSound;
    }

    public Sound getDragonSound() {
        return dragonSound;
    }

    public Sound getNoneSound() {
        return noneSound;
    }

    public Sound getRefillSound() {
        return refillSound;
    }

    public Sound getWitherSound() {
        return witherSound;
    }

    public Sound getTntSound() {
        return tntSound;
    }

    public Sound getZombieSound() {
        return zombieSound;
    }

    public Sound getPregameSound() {
        return pregameSound;
    }

    public Sound getCancelStartSound() {
        return cancelStartSound;
    }

    public Sound getStartingSound() {
        return startingSound;
    }

    public int getExecutesAni3() {
        return executesAni3;
    }

    public int getTicksAni3() {
        return ticksAni3;
    }

    public int getWinTime() {
        return winTime;
    }

    public int getProgressBarAmount() {
        return progressBarAmount;
    }

    public int getCubeletChance() {
        return cubeletChance;
    }

    public int getPregame() {
        return pregame;
    }

    public int getStarting() {
        return starting;
    }

    public int getCoinsKill() {
        return coinsKill;
    }

    public int getCoinsWin() {
        return coinsWin;
    }

    public int getCoinsAssists() {
        return coinsAssists;
    }

    public int getXpKill() {
        return xpKill;
    }

    public int getXpWin() {
        return xpWin;
    }

    public int getXpAssists() {
        return xpAssists;
    }

    public int getSoulsKill() {
        return soulsKill;
    }

    public int getSoulsWin() {
        return soulsWin;
    }

    public int getSoulsAssists() {
        return soulsAssists;
    }

    public String getUrl() {
        return url;
    }

    public String getPerm() {
        return perm;
    }

    public int getTicksAni1() {
        return ticksAni1;
    }

    public int getUpYAni1() {
        return upYAni1;
    }

    public ItemStack getHead() {
        return head;
    }
}