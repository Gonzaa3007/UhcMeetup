package com.github.gonzq.uhcmeetup.Utils.Placeholders

import com.github.gonzq.uhcmeetup.Enums.GameState
import com.github.gonzq.uhcmeetup.Enums.PlayerState
import com.github.gonzq.uhcmeetup.Managers.GameManager
import com.github.gonzq.uhcmeetup.Managers.ScenarioManager
import com.github.gonzq.uhcmeetup.Managers.WorldManager
import com.github.gonzq.uhcmeetup.Players.GamePlayer
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class Placeholders: PlaceholderExpansion() {
    private val pl = UhcMeetup.pl
    override fun getIdentifier(): String {
        return "meetup"
    }

    override fun getAuthor(): String {
        return "! Gonzq#4451"
    }

    override fun getVersion(): String {
        return "1.2"
    }

    override fun onPlaceholderRequest(player: Player, identifier: String): String {
        val game = GameManager.getInstance()
        val wm = WorldManager.getInstance()
        val pm = pl.pm;
        val p = pm.getUhcPlayer(player)
        when (identifier) {
            "border" -> return "${wm.getBorder()}"
            "players_to_start" -> return "${pl.config.getConfig().getInt("players-to-start")}"
            "spectators" -> return "${pm.getPlayerList().stream().filter(GamePlayer::isOnline).filter{u -> u.getState() == PlayerState.SPECTATING}
                .toList().size}"
            "timeelapsed" -> return Utils.timeConvert(game.getTimeElapsed())
            "playersalive" -> return "${pm.getPlayingPlayers().size}"
            "kills" -> return "${p.getKills()}"
            "online" -> return "${Bukkit.getOnlinePlayers().size}"
            "countdown" -> return "${game.getCountdown()}"
            "winner" -> {
                if (game.getState() == GameState.FINALIZED) {
                    return pm.getWinner().name
                }
                return "none"
            } else -> return null!!
        }
    }
}