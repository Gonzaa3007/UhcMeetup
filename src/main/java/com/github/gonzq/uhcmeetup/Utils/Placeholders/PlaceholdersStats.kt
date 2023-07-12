package com.github.gonzq.uhcmeetup.Utils.Placeholders

import com.github.gonzq.uhcmeetup.Managers.StatsManager
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.entity.Player

class PlaceholdersStats: PlaceholderExpansion() {
    private val stats = StatsManager.getInstance()

    override fun getIdentifier(): String {
        return "meetup_stats"
    }

    override fun getAuthor(): String {
        return "! Gonzq#4451"
    }

    override fun getVersion(): String {
        return "1.2"
    }

    override fun onPlaceholderRequest(p: Player?, ident: String): String? {
        if (ident.startsWith("kills")) {
            val top = stats.getTopList(StatsManager.Stats.KILLS)
            return when (ident) {
                "kills_name_1" -> top[0].key
                "kills_name_2" -> top[1].key
                "kills_name_3" -> top[2].key
                "kills_name_4" -> top[3].key
                "kills_name_5" -> top[4].key
                "kills_name_6" -> top[5].key
                "kills_name_7" -> top[6].key
                "kills_name_8" -> top[7].key
                "kills_name_9" -> top[8].key
                "kills_name_10" -> top[9].key

                "kills_value_1" -> "${top[0].value}"
                "kills_value_2" -> "${top[1].value}"
                "kills_value_3" -> "${top[2].value}"
                "kills_value_4" -> "${top[3].value}"
                "kills_value_5" -> "${top[4].value}"
                "kills_value_6" -> "${top[5].value}"
                "kills_value_7" -> "${top[6].value}"
                "kills_value_8" -> "${top[7].value}"
                "kills_value_9" -> "${top[8].value}"
                "kills_value_10" -> "${top[9].value}"
                else -> null
            }
        } else if (ident.startsWith("deaths")) {
            val top = stats.getTopList(StatsManager.Stats.DEATHS)
            return when (ident) {
                "deaths_name_1" -> top[0].key
                "deaths_name_2" -> top[1].key
                "deaths_name_3" -> top[2].key
                "deaths_name_4" -> top[3].key
                "deaths_name_5" -> top[4].key
                "deaths_name_6" -> top[5].key
                "deaths_name_7" -> top[6].key
                "deaths_name_8" -> top[7].key
                "deaths_name_9" -> top[8].key
                "deaths_name_10" -> top[9].key

                "deaths_value_1" -> "${top[0].value}"
                "deaths_value_2" -> "${top[1].value}"
                "deaths_value_3" -> "${top[2].value}"
                "deaths_value_4" -> "${top[3].value}"
                "deaths_value_5" -> "${top[4].value}"
                "deaths_value_6" -> "${top[5].value}"
                "deaths_value_7" -> "${top[6].value}"
                "deaths_value_8" -> "${top[7].value}"
                "deaths_value_9" -> "${top[8].value}"
                "deaths_value_10" -> "${top[9].value}"
                else -> null
            }
        } else if (ident.startsWith("wins")) {
            val top = stats.getTopList(StatsManager.Stats.WINS)
            return when (ident) {
                "wins_name_1" -> top[0].key
                "wins_name_2" -> top[1].key
                "wins_name_3" -> top[2].key
                "wins_name_4" -> top[3].key
                "wins_name_5" -> top[4].key
                "wins_name_6" -> top[5].key
                "wins_name_7" -> top[6].key
                "wins_name_8" -> top[7].key
                "wins_name_9" -> top[8].key
                "wins_name_10" -> top[9].key

                "wins_value_1" -> "${top[0].value}"
                "wins_value_2" -> "${top[1].value}"
                "wins_value_3" -> "${top[2].value}"
                "wins_value_4" -> "${top[3].value}"
                "wins_value_5" -> "${top[4].value}"
                "wins_value_6" -> "${top[5].value}"
                "wins_value_7" -> "${top[6].value}"
                "wins_value_8" -> "${top[7].value}"
                "wins_value_9" -> "${top[8].value}"
                "wins_value_10" -> "${top[9].value}"
                else -> null
            }
        } else if (ident.startsWith("gapps")) {
            val top = stats.getTopList(StatsManager.Stats.GAPPS)
            return when (ident) {
                "gapps_name_1" -> top[0].key
                "gapps_name_2" -> top[1].key
                "gapps_name_3" -> top[2].key
                "gapps_name_4" -> top[3].key
                "gapps_name_5" -> top[4].key
                "gapps_name_6" -> top[5].key
                "gapps_name_7" -> top[6].key
                "gapps_name_8" -> top[7].key
                "gapps_name_9" -> top[8].key
                "gapps_name_10" -> top[9].key

                "gapps_value_1" -> "${top[0].value}"
                "gapps_value_2" -> "${top[1].value}"
                "gapps_value_3" -> "${top[2].value}"
                "gapps_value_4" -> "${top[3].value}"
                "gapps_value_5" -> "${top[4].value}"
                "gapps_value_6" -> "${top[5].value}"
                "gapps_value_7" -> "${top[6].value}"
                "gapps_value_8" -> "${top[7].value}"
                "gapps_value_9" -> "${top[8].value}"
                "gapps_value_10" -> "${top[9].value}"
                else -> null
            }
        } else if (ident.startsWith("played")) {
            val top = stats.getTopList(StatsManager.Stats.PLAYED)
            return when (ident) {
                "played_name_1" -> top[0].key
                "played_name_2" -> top[1].key
                "played_name_3" -> top[2].key
                "played_name_4" -> top[3].key
                "played_name_5" -> top[4].key
                "played_name_6" -> top[5].key
                "played_name_7" -> top[6].key
                "played_name_8" -> top[7].key
                "played_name_9" -> top[8].key
                "played_name_10" -> top[9].key

                "played_value_1" -> "${top[0].value}"
                "played_value_2" -> "${top[1].value}"
                "played_value_3" -> "${top[2].value}"
                "played_value_4" -> "${top[3].value}"
                "played_value_5" -> "${top[4].value}"
                "played_value_6" -> "${top[5].value}"
                "played_value_7" -> "${top[6].value}"
                "played_value_8" -> "${top[7].value}"
                "played_value_9" -> "${top[8].value}"
                "played_value_10" -> "${top[9].value}"
                else -> null
            }
        } else if (ident.startsWith("shoots")) {
            val top = stats.getTopList(StatsManager.Stats.BOW_SHOOTS)
            return when (ident) {
                "shoots_name_1" -> top[0].key
                "shoots_name_2" -> top[1].key
                "shoots_name_3" -> top[2].key
                "shoots_name_4" -> top[3].key
                "shoots_name_5" -> top[4].key
                "shoots_name_6" -> top[5].key
                "shoots_name_7" -> top[6].key
                "shoots_name_8" -> top[7].key
                "shoots_name_9" -> top[8].key
                "shoots_name_10" -> top[9].key

                "shoots_value_1" -> "${top[0].value}"
                "shoots_value_2" -> "${top[1].value}"
                "shoots_value_3" -> "${top[2].value}"
                "shoots_value_4" -> "${top[3].value}"
                "shoots_value_5" -> "${top[4].value}"
                "shoots_value_6" -> "${top[5].value}"
                "shoots_value_7" -> "${top[6].value}"
                "shoots_value_8" -> "${top[7].value}"
                "shoots_value_9" -> "${top[8].value}"
                "shoots_value_10" -> "${top[9].value}"
                else -> null
            }
        }
        return null
    }
}