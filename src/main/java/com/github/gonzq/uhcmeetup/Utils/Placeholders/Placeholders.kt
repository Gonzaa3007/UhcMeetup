package com.github.gonzq.uhcmeetup.Utils.Placeholders

import com.github.gonzq.uhcmeetup.Enums.GameState
import com.github.gonzq.uhcmeetup.Enums.PlayerState
import com.github.gonzq.uhcmeetup.Managers.GameManager
import com.github.gonzq.uhcmeetup.Managers.ScenarioManager
import com.github.gonzq.uhcmeetup.Managers.StatsManager
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
        val stats = StatsManager.getInstance()
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
            } else ->{
                if (identifier.startsWith("stats")) {
                    val ident = identifier.replace("stats_","")
                    if (ident.startsWith("kills")) {
                        val top = stats.getTopList(StatsManager.Stats.KILLS)
                        return when (identifier) {
                            "stats_kills_name_1" -> top[0].key
                            "stats_kills_name_2" -> top[1].key
                            "stats_kills_name_3" -> top[2].key
                            "stats_kills_name_4" -> top[3].key
                            "stats_kills_name_5" -> top[4].key
                            "stats_kills_name_6" -> top[5].key
                            "stats_kills_name_7" -> top[6].key
                            "stats_kills_name_8" -> top[7].key
                            "stats_kills_name_9" -> top[8].key
                            "stats_kills_name_10" -> top[9].key

                            "stats_kills_value_1" -> "${top[0].value}"
                            "stats_kills_value_2" -> "${top[1].value}"
                            "stats_kills_value_3" -> "${top[2].value}"
                            "stats_kills_value_4" -> "${top[3].value}"
                            "stats_kills_value_5" -> "${top[4].value}"
                            "stats_kills_value_6" -> "${top[5].value}"
                            "stats_kills_value_7" -> "${top[6].value}"
                            "stats_kills_value_8" -> "${top[7].value}"
                            "stats_kills_value_9" -> "${top[8].value}"
                            "stats_kills_value_10" -> "${top[9].value}"
                            else -> null!!
                        }
                    } else if (ident.startsWith("deaths")) {
                        val top = stats.getTopList(StatsManager.Stats.DEATHS)
                        return when (identifier) {
                            "stats_deaths_name_1" -> top[0].key
                            "stats_deaths_name_2" -> top[1].key
                            "stats_deaths_name_3" -> top[2].key
                            "stats_deaths_name_4" -> top[3].key
                            "stats_deaths_name_5" -> top[4].key
                            "stats_deaths_name_6" -> top[5].key
                            "stats_deaths_name_7" -> top[6].key
                            "stats_deaths_name_8" -> top[7].key
                            "stats_deaths_name_9" -> top[8].key
                            "stats_deaths_name_10" -> top[9].key

                            "stats_deaths_value_1" -> "${top[0].value}"
                            "stats_deaths_value_2" -> "${top[1].value}"
                            "stats_deaths_value_3" -> "${top[2].value}"
                            "stats_deaths_value_4" -> "${top[3].value}"
                            "stats_deaths_value_5" -> "${top[4].value}"
                            "stats_deaths_value_6" -> "${top[5].value}"
                            "stats_deaths_value_7" -> "${top[6].value}"
                            "stats_deaths_value_8" -> "${top[7].value}"
                            "stats_deaths_value_9" -> "${top[8].value}"
                            "stats_deaths_value_10" -> "${top[9].value}"
                            else -> null!!
                        }
                    }  else if (ident.startsWith("wins")) {
                        val top = stats.getTopList(StatsManager.Stats.WINS)
                        return when (identifier) {
                            "stats_wins_name_1" -> top[0].key
                            "stats_wins_name_2" -> top[1].key
                            "stats_wins_name_3" -> top[2].key
                            "stats_wins_name_4" -> top[3].key
                            "stats_wins_name_5" -> top[4].key
                            "stats_wins_name_6" -> top[5].key
                            "stats_wins_name_7" -> top[6].key
                            "stats_wins_name_8" -> top[7].key
                            "stats_wins_name_9" -> top[8].key
                            "stats_wins_name_10" -> top[9].key

                            "stats_wins_value_1" -> "${top[0].value}"
                            "stats_wins_value_2" -> "${top[1].value}"
                            "stats_wins_value_3" -> "${top[2].value}"
                            "stats_wins_value_4" -> "${top[3].value}"
                            "stats_wins_value_5" -> "${top[4].value}"
                            "stats_wins_value_6" -> "${top[5].value}"
                            "stats_wins_value_7" -> "${top[6].value}"
                            "stats_wins_value_8" -> "${top[7].value}"
                            "stats_wins_value_9" -> "${top[8].value}"
                            "stats_wins_value_10" -> "${top[9].value}"
                            else -> null!!
                        }
                    }else if (ident.startsWith("gapps")) {
                        val top = stats.getTopList(StatsManager.Stats.GAPPS)
                        return when (identifier) {
                            "stats_gapps_name_1" -> top[0].key
                            "stats_gapps_name_2" -> top[1].key
                            "stats_gapps_name_3" -> top[2].key
                            "stats_gapps_name_4" -> top[3].key
                            "stats_gapps_name_5" -> top[4].key
                            "stats_gapps_name_6" -> top[5].key
                            "stats_gapps_name_7" -> top[6].key
                            "stats_gapps_name_8" -> top[7].key
                            "stats_gapps_name_9" -> top[8].key
                            "stats_gapps_name_10" -> top[9].key

                            "stats_gapps_value_1" -> "${top[0].value}"
                            "stats_gapps_value_2" -> "${top[1].value}"
                            "stats_gapps_value_3" -> "${top[2].value}"
                            "stats_gapps_value_4" -> "${top[3].value}"
                            "stats_gapps_value_5" -> "${top[4].value}"
                            "stats_gapps_value_6" -> "${top[5].value}"
                            "stats_gapps_value_7" -> "${top[6].value}"
                            "stats_gapps_value_8" -> "${top[7].value}"
                            "stats_gapps_value_9" -> "${top[8].value}"
                            "stats_gapps_value_10" -> "${top[9].value}"
                            else -> null!!
                        }
                    } else if (ident.startsWith("played")) {
                        val top = stats.getTopList(StatsManager.Stats.PLAYED)
                        return when (identifier) {
                            "stats_played_name_1" -> top[0].key
                            "stats_played_name_2" -> top[1].key
                            "stats_played_name_3" -> top[2].key
                            "stats_played_name_4" -> top[3].key
                            "stats_played_name_5" -> top[4].key
                            "stats_played_name_6" -> top[5].key
                            "stats_played_name_7" -> top[6].key
                            "stats_played_name_8" -> top[7].key
                            "stats_played_name_9" -> top[8].key
                            "stats_played_name_10" -> top[9].key

                            "stats_played_value_1" -> "${top[0].value}"
                            "stats_played_value_2" -> "${top[1].value}"
                            "stats_played_value_3" -> "${top[2].value}"
                            "stats_played_value_4" -> "${top[3].value}"
                            "stats_played_value_5" -> "${top[4].value}"
                            "stats_played_value_6" -> "${top[5].value}"
                            "stats_played_value_7" -> "${top[6].value}"
                            "stats_played_value_8" -> "${top[7].value}"
                            "stats_played_value_9" -> "${top[8].value}"
                            "stats_played_value_10" -> "${top[9].value}"
                            else -> null!!
                        }
                    }else if (ident.startsWith("shoots")) {
                        val top = stats.getTopList(StatsManager.Stats.BOW_SHOOTS)
                        return when (identifier) {
                            "stats_shoots_name_1" -> top[0].key
                            "stats_shoots_name_2" -> top[1].key
                            "stats_shoots_name_3" -> top[2].key
                            "stats_shoots_name_4" -> top[3].key
                            "stats_shoots_name_5" -> top[4].key
                            "stats_shoots_name_6" -> top[5].key
                            "stats_shoots_name_7" -> top[6].key
                            "stats_shoots_name_8" -> top[7].key
                            "stats_shoots_name_9" -> top[8].key
                            "stats_shoots_name_10" -> top[9].key

                            "stats_shoots_value_1" -> "${top[0].value}"
                            "stats_shoots_value_2" -> "${top[1].value}"
                            "stats_shoots_value_3" -> "${top[2].value}"
                            "stats_shoots_value_4" -> "${top[3].value}"
                            "stats_shoots_value_5" -> "${top[4].value}"
                            "stats_shoots_value_6" -> "${top[5].value}"
                            "stats_shoots_value_7" -> "${top[6].value}"
                            "stats_shoots_value_8" -> "${top[7].value}"
                            "stats_shoots_value_9" -> "${top[8].value}"
                            "stats_shoots_value_10" -> "${top[9].value}"
                            else -> null!!
                        }
                    }
                }
            }
        }
        return null!!
    }
}