package com.github.gonzq.uhcmeetup.Tasks

import com.github.gonzq.uhcmeetup.Enums.GameState
import com.github.gonzq.uhcmeetup.Enums.PlayerState
import com.github.gonzq.uhcmeetup.Managers.GameManager
import com.github.gonzq.uhcmeetup.Managers.ScenarioManager
import com.github.gonzq.uhcmeetup.Managers.WorldManager
import com.github.gonzq.uhcmeetup.Players.GamePlayer
import com.github.gonzq.uhcmeetup.Scenarios.Scenario
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.UUID

class ScoreboardTask: BukkitRunnable() {
    private val pl = UhcMeetup.pl
    private val lastShowScen = mutableMapOf<UUID, Int>()


    override fun run() {
        pl.boards.values.forEach {f ->
            f.updateTitle(Utils.chat(pl.board.getConfig().getString("scoreboard.title")))
            val lines: List<String> = when (GameManager.getInstance().getState()) {
                GameState.WAITING -> pl.board.getConfig().getStringList("scoreboard.lobby")
                GameState.STARTING -> pl.board.getConfig().getStringList("scoreboard.starting")
                GameState.STARTED -> pl.board.getConfig().getStringList("scoreboard.started")
                GameState.FINALIZED -> pl.board.getConfig().getStringList("scoreboard.finalized")
            }

            for (i in lines.toTypedArray().indices) {
                var line = lines[i]
                if (pl.papi) {
                    f.updateLine(i, PlaceholderAPI.setPlaceholders(f.player, replace(f.player, line)))
                } else {
                    line = replace(f.player, line)
                    f.updateLine(i, line)
                }
            }
        }
    }

    private fun replace(player: Player, line: String): String {
        var l = line
        val game = GameManager.getInstance()
        val wm = WorldManager.getInstance()
        val pm = pl.pm
        val scen = ScenarioManager.getInstance()
        val p = pm.getUhcPlayer(player)
        val key = "%meetup_"
        if (line.contains(key + "border%")) l = l.replace(key + "border%", "${wm.getBorder()}")
        if (line.contains(key + "players_to_start%")) l = l.replace(key + "players_to_start%", "${pl.config.getConfig().getInt("players-to-start")}")
        if (line.contains(key + "spectators%")) l = l.replace(key + "spectators%", "${pm.getPlayerList().stream().filter(GamePlayer::isOnline)
            .filter {a -> a.getState() == PlayerState.SPECTATING }.toList().size}")
        if (line.contains(key + "timeelapsed%")) l = l.replace(key + "timeelapsed%", Utils.timeConvert(game.getTimeElapsed()))
        if (line.contains(key + "playersalive%")) l = l.replace(key + "playersalive%", "${pm.getPlayingPlayers().size}")
        if (line.contains(key + "kills%")) l = l.replace(key + "kills%", "${p.getKills()}")
        if (line.contains(key + "online%")) l = l.replace(key + "online%", "${pm.getPlayerList()
            .stream().filter(GamePlayer::isOnline).toList().size}")
        if (line.contains(key + "countdown%")) l = l.replace(key + "countdown%", "${game.getCountdown()}")
        if (line.contains(key + "scenarios%")) {
            if (scen.getEnabledScenarios().isEmpty()) {
                l = l.replace(key + "scenarios%", "-")
            } else {
                val active = scen.getEnabledScenarios().toTypedArray()
                var showScen = lastShowScen.getOrPut(p.uid){-1} + 1
                if (showScen >= active.size) {
                    showScen = 0
                }
                lastShowScen[p.uid] = showScen
                l = l.replace(key + "scenarios%", active[showScen].getName())
            }
        }

        if (line.contains(key + "winner%")) {
            l = if (game.getState() == GameState.FINALIZED){
                l.replace(key + "winner%", pm.getWinner().name)
            } else l.replace(key + "winner%", "none")
        }
        return Utils.chat(l)
    }
}