package com.github.gonzq.uhcmeetup.Managers

import com.github.gonzq.uhcmeetup.Players.GamePlayer
import com.github.gonzq.uhcmeetup.Players.PlayerFile
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import org.bukkit.Bukkit
import java.util.UUID

class StatsManager {

    companion object{
        private val instance = StatsManager()
        fun getInstance(): StatsManager {
            return instance
        }
    }

    private val kills = mutableMapOf<UUID, Int>()
    private val deaths = mutableMapOf<UUID, Int>()
    private val wins = mutableMapOf<UUID, Int>()
    private val gapps = mutableMapOf<UUID, Int>()
    private val played = mutableMapOf<UUID, Int>()

    fun getValue(p: GamePlayer, stat: Stats): Int {

        return when (stat) {
            Stats.KILLS -> kills.getOrPut(p.uid){0}
            Stats.DEATHS -> deaths.getOrPut(p.uid){0}
            Stats.WINS -> wins.getOrPut(p.uid){0}
            Stats.GAPPS -> gapps.getOrPut(p.uid){0}
            Stats.PLAYED -> played.getOrPut(p.uid){0}
        }
    }

    fun add(p: GamePlayer, stat: Stats) {
        when (stat) {
            Stats.KILLS -> kills[p.uid] = kills.getOrPut(p.uid){0} + 1
            Stats.DEATHS -> deaths[p.uid] = deaths.getOrPut(p.uid){0} + 1
            Stats.WINS -> wins[p.uid] = wins.getOrPut(p.uid){0} + 1
            Stats.GAPPS -> gapps[p.uid] = gapps.getOrPut(p.uid){0} + 1
            Stats.PLAYED -> played[p.uid] = played.getOrPut(p.uid){0} + 1
        }
    }

    fun getTop(stat: Stats): MutableList<String> {
        val list = mutableListOf<String>()
        val hash = when (stat){
            Stats.KILLS -> kills
            Stats.DEATHS -> deaths
            Stats.WINS -> wins
            Stats.GAPPS -> gapps
            Stats.PLAYED -> played
        }

        val entry: List<Map.Entry<UUID, Int>> = hash.entries.stream().sorted(java.util.Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .limit(10).toList()

        for (i in entry.toTypedArray().indices) {
            val name = Bukkit.getOfflinePlayer(entry[i].key).name
            val value = entry[i].value
            val top = i + 1
            list.add(Utils.chat("&f#$top &a$name: &6$value"))
        }
        return list
    }

    fun setUpStats() {
        Bukkit.getScheduler().runTaskAsynchronously(UhcMeetup.pl, Runnable {
            for (p in Bukkit.getOfflinePlayers()) {
                val file = PlayerFile.get(p.uniqueId)
                kills[p.uniqueId] = file.c().getInt("stats.kills",0)
                deaths[p.uniqueId] = file.c().getInt("stats.deaths",0)
                wins[p.uniqueId] = file.c().getInt("stats.wins",0)
                gapps[p.uniqueId] = file.c().getInt("stats.gapps",0)
                played[p.uniqueId] = file.c().getInt("stats.played",0)
            }
            Bukkit.getLogger().info("Stats has been setted")
        })
    }

    fun saveStats() {
        for (p in Bukkit.getOfflinePlayers()) {
            val file = PlayerFile.get(p.uniqueId)
            file.c().set("stats.kills", kills.getOrPut(p.uniqueId){0})
            file.c().set("stats.deaths", deaths.getOrPut(p.uniqueId){0})
            file.c().set("stats.wins", wins.getOrPut(p.uniqueId){0})
            file.c().set("stats.gapps", gapps.getOrPut(p.uniqueId){0})
            file.c().set("stats.played", played.getOrPut(p.uniqueId){0})
            file.save()
        }
    }


    enum class Stats {
        KILLS,
        DEATHS,
        WINS,
        GAPPS,
        PLAYED
    }
}