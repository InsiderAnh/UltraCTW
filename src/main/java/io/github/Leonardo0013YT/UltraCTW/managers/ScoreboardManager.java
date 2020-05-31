package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.game.GamePlayer;
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
        String titulo = plugin.getLang().get(p, "scoreboards.main.lines");
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
        String titulo = simple(plugin.getLang().get(p, "scoreboards.simple-game.lines"), team, gp, t1, t2);
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
        if (!plugin.getGm().isPlayerInGame(p)) {
            if (score.containsKey(p)) {
                ScoreboardUtil scoreboardUtil = score.get(p);
                if (sb.get(p).equals("main")) {
                    String titulo = plugin.getLang().get(p, "scoreboards.main");
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
        } else {
            Game game = plugin.getGm().getGameByPlayer(p);
            Team team = game.getTeamPlayer(p);
            if (team == null){
                return;
            }
            if (score.containsKey(p)) {
                ScoreboardUtil scoreboardUtil = score.get(p);
                if (sb.get(p).equals("simple")) {
                    GamePlayer gp = game.getGamePlayer(p);
                    Team t1 = game.getTeamByID(0);
                    Team t2 = game.getTeamByID(1);
                    String titulo = simple(plugin.getLang().get(p, "scoreboards.simple-game.lines"), team, gp, t1, t2);
                    String[] title = titulo.split("\\n");
                    for (int n = 1, n2 = title.length - 1; n < title.length + 1; ++n, --n2) {
                        scoreboardUtil.lines(n, title[n2]);
                    }
                } else {
                    createSimpleGameBoard(p, game);
                }
            } else {
                createSimpleGameBoard(p, game);
            }
        }
    }

    public String simple(String s, Team team, GamePlayer gp, Team t1, Team t2){
        return s.replaceAll("<T1Wools>", Utils.getWoolsString(t1))
                .replaceAll("<T2Wools>", Utils.getWoolsString(t2))
                .replaceAll("<T1>", plugin.getLang().get("scoreboards.team").replaceAll("<TColor>", t1.getColor() + "").replaceAll("<TName>", t1.getName()))
                .replaceAll("<T2>", plugin.getLang().get("scoreboards.team").replaceAll("<TColor>", t2.getColor() + "").replaceAll("<TName>", t2.getName()))
                .replaceAll("<team>", team.getName()).replaceAll("<kills>", String.valueOf(gp.getKills())).replaceAll("<deaths>", String.valueOf(gp.getDeaths()));
    }

    public void remove(Player p) {
        sb.remove(p);
        score.remove(p);
    }

}