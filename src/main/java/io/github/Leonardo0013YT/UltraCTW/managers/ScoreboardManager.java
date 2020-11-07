package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.enums.State;
import io.github.Leonardo0013YT.UltraCTW.game.GameEvent;
import io.github.Leonardo0013YT.UltraCTW.game.GameFlag;
import io.github.Leonardo0013YT.UltraCTW.game.GamePlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.objects.Level;
import io.github.Leonardo0013YT.UltraCTW.team.FlagTeam;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import io.github.Leonardo0013YT.UltraCTW.utils.ScoreboardUtil;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.HashMap;

public class ScoreboardManager {

    Main plugin;
    private HashMap<Player, String> sb = new HashMap<>();
    private HashMap<Player, ScoreboardUtil> score = new HashMap<>();

    public ScoreboardManager(Main plugin) {
        this.plugin = plugin;
    }

    private void createMainBoard(Player p) {
        if (p == null || !p.isOnline()) {
            return;
        }
        sb.put(p, "main");
        p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        ScoreboardUtil scoreboardUtil = new ScoreboardUtil("main", "main");
        scoreboardUtil.setName(plugin.getLang().get(p, "scoreboards.main.title"));
        String titulo = main(p, plugin.getLang().get(p, "scoreboards.main.lines"));
        String[] title = titulo.split("\\n");
        for (int n = 1, n2 = title.length - 1; n < title.length + 1; ++n, --n2) {
            scoreboardUtil.lines(n, title[n2]);
        }
        scoreboardUtil.build(p);
        score.put(p, scoreboardUtil);
    }

    private void createWaitingBoard(Player p, Game game) {
        if (p == null || !p.isOnline()) {
            return;
        }
        sb.put(p, "waiting");
        p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        ScoreboardUtil scoreboardUtil = new ScoreboardUtil("waiting", "waiting");
        scoreboardUtil.setName(plugin.getLang().get(p, "scoreboards.waiting.title"));
        String titulo = waiting(p, plugin.getLang().get(p, "scoreboards.waiting.lines"), game);
        String[] title = titulo.split("\\n");
        for (int n = 1, n2 = title.length - 1; n < title.length + 1; ++n, --n2) {
            scoreboardUtil.lines(n, title[n2]);
        }
        scoreboardUtil.build(p);
        score.put(p, scoreboardUtil);
    }

    private void createWaitingFlagBoard(Player p, GameFlag game) {
        if (p == null || !p.isOnline()) {
            return;
        }
        sb.put(p, "waitingFlag");
        p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        ScoreboardUtil scoreboardUtil = new ScoreboardUtil("waitingFlag", "waitingFlag");
        scoreboardUtil.setName(plugin.getLang().get(p, "scoreboards.waitingFlag.title"));
        String titulo = waitingFlag(p, plugin.getLang().get(p, "scoreboards.waitingFlag.lines"), game);
        String[] title = titulo.split("\\n");
        for (int n = 1, n2 = title.length - 1; n < title.length + 1; ++n, --n2) {
            scoreboardUtil.lines(n, title[n2]);
        }
        scoreboardUtil.build(p);
        score.put(p, scoreboardUtil);
    }

    private void createStartingBoard(Player p, Game game) {
        if (p == null || !p.isOnline()) {
            return;
        }
        sb.put(p, "starting");
        p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        ScoreboardUtil scoreboardUtil = new ScoreboardUtil("starting", "starting");
        scoreboardUtil.setName(plugin.getLang().get(p, "scoreboards.starting.title"));
        String titulo = starting(p, plugin.getLang().get(p, "scoreboards.starting.lines"), game);
        String[] title = titulo.split("\\n");
        for (int n = 1, n2 = title.length - 1; n < title.length + 1; ++n, --n2) {
            scoreboardUtil.lines(n, title[n2]);
        }
        scoreboardUtil.build(p);
        score.put(p, scoreboardUtil);
    }

    private void createStartingFlagBoard(Player p, GameFlag game) {
        if (p == null || !p.isOnline()) {
            return;
        }
        sb.put(p, "startingFlag");
        p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        ScoreboardUtil scoreboardUtil = new ScoreboardUtil("startingFlag", "startingFlag");
        scoreboardUtil.setName(plugin.getLang().get(p, "scoreboards.startingFlag.title"));
        String titulo = startingFlag(p, plugin.getLang().get(p, "scoreboards.startingFlag.lines"), game);
        String[] title = titulo.split("\\n");
        for (int n = 1, n2 = title.length - 1; n < title.length + 1; ++n, --n2) {
            scoreboardUtil.lines(n, title[n2]);
        }
        scoreboardUtil.build(p);
        score.put(p, scoreboardUtil);
    }

    private void createSimpleGameBoard(Player p, Game game) {
        if (p == null || !p.isOnline()) {
            return;
        }
        sb.put(p, "simple");
        p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        Team team = game.getTeamPlayer(p);
        GamePlayer gp = game.getGamePlayer(p);
        Team t1 = game.getTeamByID(0);
        Team t2 = game.getTeamByID(1);
        ScoreboardUtil scoreboardUtil = new ScoreboardUtil("simple", "simple");
        scoreboardUtil.setName(plugin.getLang().get(p, "scoreboards.simple-game.title"));
        String titulo = simple(p, plugin.getLang().get(p, "scoreboards.simple-game.lines"), game, team, gp, t1, t2);
        String[] title = titulo.split("\\n");
        for (int n = 1, n2 = title.length - 1; n < title.length + 1; ++n, --n2) {
            scoreboardUtil.lines(n, title[n2]);
        }
        scoreboardUtil.build(p);
        score.put(p, scoreboardUtil);
    }

    private void createFlagGameBoard(Player p, GameFlag game) {
        if (p == null || !p.isOnline()) {
            return;
        }
        sb.put(p, "flag");
        p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        ScoreboardUtil scoreboardUtil = new ScoreboardUtil("flag", "flag");
        scoreboardUtil.setName(plugin.getLang().get(p, "scoreboards.flag-game.title"));
        String titulo = flag(p, plugin.getLang().get(p, "scoreboards.flag-game.lines"), game);
        String[] title = titulo.split("\\n");
        for (int n = 1, n2 = title.length - 1; n < title.length + 1; ++n, --n2) {
            scoreboardUtil.lines(n, title[n2]);
        }
        scoreboardUtil.build(p);
        score.put(p, scoreboardUtil);
    }

    private void createBigGameBoard(Player p) {
        if (p == null || !p.isOnline()) {
            return;
        }
        sb.put(p, "big");
        p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        ScoreboardUtil scoreboardUtil = new ScoreboardUtil("big", "big");
        scoreboardUtil.setName(plugin.getLang().get(p, "scoreboards.big-game.title"));
        String titulo = plugin.getLang().get(p, "scoreboards.big-game.lines");
        String[] title = titulo.split("\\n");
        for (int n = 1, n2 = title.length - 1; n < title.length + 1; ++n, --n2) {
            scoreboardUtil.lines(n, title[n2]);
        }
        scoreboardUtil.build(p);
        score.put(p, scoreboardUtil);
    }

    public void update(Player p) {
        if (p == null || !p.isOnline()) {
            return;
        }
        if (!plugin.getGm().isPlayerInGame(p) && plugin.getCm().isLobbyScoreboard()) {
            if (score.containsKey(p)) {
                ScoreboardUtil scoreboardUtil = score.get(p);
                if (sb.get(p).equals("main")) {
                    String titulo = main(p, plugin.getLang().get(p, "scoreboards.main.lines"));
                    String[] title = titulo.split("\\n");
                    for (int n = 1, n2 = title.length - 1; n < title.length + 1; ++n, --n2) {
                        scoreboardUtil.lines(n, title[n2]);
                    }
                } else {
                    createMainBoard(p);
                }
            } else {
                createMainBoard(p);
            }
            return;
        }
        if (!plugin.getGm().isPlayerInGame(p)) {
            return;
        }
        Game game = plugin.getGm().getGameByPlayer(p);
        if (game != null) {
            Team team = game.getTeamPlayer(p);
            if (score.containsKey(p)) {
                ScoreboardUtil scoreboardUtil = score.get(p);
                if (game.isState(State.WAITING)) {
                    if (sb.get(p).equals("waiting")) {
                        String titulo = waiting(p, plugin.getLang().get(p, "scoreboards.waiting.lines"), game);
                        String[] title = titulo.split("\\n");
                        for (int n = 1, n2 = title.length - 1; n < title.length + 1; ++n, --n2) {
                            scoreboardUtil.lines(n, title[n2]);
                        }
                    } else {
                        createWaitingBoard(p, game);
                    }
                } else if (game.isState(State.STARTING)) {
                    if (sb.get(p).equals("starting")) {
                        String titulo = starting(p, plugin.getLang().get(p, "scoreboards.starting.lines"), game);
                        String[] title = titulo.split("\\n");
                        for (int n = 1, n2 = title.length - 1; n < title.length + 1; ++n, --n2) {
                            scoreboardUtil.lines(n, title[n2]);
                        }
                    } else {
                        createStartingBoard(p, game);
                    }
                } else {
                    if (team == null) {
                        return;
                    }
                    if (sb.get(p).equals("simple")) {
                        GamePlayer gp = game.getGamePlayer(p);
                        Team t1 = game.getTeamByID(0);
                        Team t2 = game.getTeamByID(1);
                        String titulo = simple(p, plugin.getLang().get(p, "scoreboards.simple-game.lines"), game, team, gp, t1, t2);
                        String[] title = titulo.split("\\n");
                        for (int n = 1, n2 = title.length - 1; n < title.length + 1; ++n, --n2) {
                            scoreboardUtil.lines(n, title[n2]);
                        }
                    } else {
                        createSimpleGameBoard(p, game);
                    }
                }
            } else {
                createWaitingBoard(p, game);
            }
        }
        GameFlag gameFlag = plugin.getGm().getGameFlagByPlayer(p);
        if (gameFlag != null) {
            if (score.containsKey(p)) {
                ScoreboardUtil scoreboardUtil = score.get(p);
                if (gameFlag.isState(State.WAITING)) {
                    if (sb.get(p).equals("waitingFlag")) {
                        String titulo = waitingFlag(p, plugin.getLang().get(p, "scoreboards.waitingFlag.lines"), gameFlag);
                        String[] title = titulo.split("\\n");
                        for (int n = 1, n2 = title.length - 1; n < title.length + 1; ++n, --n2) {
                            scoreboardUtil.lines(n, title[n2]);
                        }
                    } else {
                        createWaitingFlagBoard(p, gameFlag);
                    }
                } else if (gameFlag.isState(State.STARTING)) {
                    if (sb.get(p).equals("startingFlag")) {
                        String titulo = startingFlag(p, plugin.getLang().get(p, "scoreboards.startingFlag.lines"), gameFlag);
                        String[] title = titulo.split("\\n");
                        for (int n = 1, n2 = title.length - 1; n < title.length + 1; ++n, --n2) {
                            scoreboardUtil.lines(n, title[n2]);
                        }
                    } else {
                        createStartingFlagBoard(p, gameFlag);
                    }
                } else {
                    if (sb.get(p).equals("flag")) {
                        String titulo = flag(p, plugin.getLang().get(p, "scoreboards.flag-game.lines"), gameFlag);
                        String[] title = titulo.split("\\n");
                        for (int n = 1, n2 = title.length - 1; n < title.length + 1; ++n, --n2) {
                            scoreboardUtil.lines(n, title[n2]);
                        }
                    } else {
                        createFlagGameBoard(p, gameFlag);
                    }
                }
            } else {
                createWaitingFlagBoard(p, gameFlag);
            }
        }
    }

    public String waitingFlag(Player p, String s, GameFlag game) {
        CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
        if (ctw == null) return s;
        Level level = plugin.getLvl().getLevel(p);
        return s.replace("<leveUp>", String.valueOf(level.getLevelUp()))
                .replace("<now>", String.valueOf(ctw.getXp()))
                .replace("<max>", String.valueOf(game.getMax()))
                .replace("<players>", String.valueOf(game.getPlayers().size()))
                .replace("<map>", game.getName());
    }

    public String startingFlag(Player p, String s, GameFlag game) {
        CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
        if (ctw == null) return s;
        Level level = plugin.getLvl().getLevel(p);
        return s.replace("<leveUp>", String.valueOf(level.getLevelUp()))
                .replace("<now>", String.valueOf(ctw.getXp()))
                .replace("<time>", Utils.convertTime(game.getStarting()))
                .replace("<max>", String.valueOf(game.getMax()))
                .replace("<players>", String.valueOf(game.getPlayers().size()))
                .replace("<map>", game.getName());
    }

    public String flag(Player p, String s, GameFlag game) {
        FlagTeam team = game.getTeamPlayer(p);
        GamePlayer gp = game.getGamePlayer(p);
        return s.replace("<nextPhase>", getEvent(game))
                .replace("<flagStatus>", (team.isStolen()) ? plugin.getLang().get("flagStatus.stolen") : plugin.getLang().get("flagStatus.saved"))
                .replace("<totalLifes>", team.getMaxLifes() + "")
                .replace("<lifesRemaining>", team.getLifes() + "")
                .replace("<team>", team.getName())
                .replace("<kills>", String.valueOf(gp.getKills()))
                .replace("<deaths>", String.valueOf(gp.getDeaths()));
    }

    public String getEvent(GameFlag fg){
        GameEvent ge = fg.getNowEvent();
        if (ge != null){
            return plugin.getLang().get("phases." + ge.getType().name()) + " " + Utils.convertTime(ge.getTime());
        }
        return plugin.getLang().get("phases.none");
    }

    public String main(Player p, String s) {
        CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
        if (ctw == null) return s;
        Level level = plugin.getLvl().getLevel(p);
        return s.replace("<leveUp>", String.valueOf(level.getLevelUp()))
                .replace("<gcoins>", Utils.format(ctw.getCoins()))
                .replace("<now>", String.valueOf(ctw.getXp()))
                .replace("<wins>", String.valueOf(ctw.getWins()))
                .replace("<deaths>", String.valueOf(ctw.getDeaths()))
                .replace("<captured>", String.valueOf(ctw.getWoolCaptured()))
                .replace("<kills>", String.valueOf(ctw.getKills()));
    }

    public String waiting(Player p, String s, Game game) {
        CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
        if (ctw == null) return s;
        Level level = plugin.getLvl().getLevel(p);
        return s.replace("<leveUp>", String.valueOf(level.getLevelUp()))
                .replace("<now>", String.valueOf(ctw.getXp()))
                .replace("<max>", String.valueOf(game.getMax()))
                .replace("<players>", String.valueOf(game.getPlayers().size()))
                .replace("<map>", game.getName());
    }

    public String starting(Player p, String s, Game game) {
        CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
        if (ctw == null) return s;
        Level level = plugin.getLvl().getLevel(p);
        return s.replace("<leveUp>", String.valueOf(level.getLevelUp()))
                .replace("<now>", String.valueOf(ctw.getXp()))
                .replace("<time>", Utils.convertTime(game.getStarting()))
                .replace("<max>", String.valueOf(game.getMax()))
                .replace("<players>", String.valueOf(game.getPlayers().size()))
                .replace("<map>", game.getName());
    }

    public String simple(Player p, String s, Game game, Team team, GamePlayer gp, Team t1, Team t2) {
        CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
        if (ctw == null) return s;
        Level level = plugin.getLvl().getLevel(p);
        return s.replace("<leveUp>", String.valueOf(level.getLevelUp()))
                .replace("<now>", String.valueOf(ctw.getXp()))
                .replace("<gcoins>", Utils.format(ctw.getCoins()))
                .replace("<coins>", Utils.format(gp.getCoins()))
                .replace("<time>", Utils.convertTime(game.getTime()))
                .replace("<map>", game.getName())
                .replace("<T1Wools>", Utils.getWoolsString(t1))
                .replace("<T2Wools>", Utils.getWoolsString(t2))
                .replace("<T1>", plugin.getLang().get("scoreboards.team").replace("<TColor>", t1.getColor() + "").replace("<TName>", t1.getName()))
                .replace("<T2>", plugin.getLang().get("scoreboards.team").replace("<TColor>", t2.getColor() + "").replace("<TName>", t2.getName()))
                .replace("<team>", team.getName())
                .replace("<kills>", String.valueOf(gp.getKills()))
                .replace("<deaths>", String.valueOf(gp.getDeaths()));
    }

    public void remove(Player p) {
        sb.remove(p);
        score.remove(p);
    }

}