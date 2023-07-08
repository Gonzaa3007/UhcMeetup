package com.github.gonzq.uhcmeetup.Managers

import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.FileUtils
import com.github.gonzq.uhcmeetup.Utils.Utils
import org.bukkit.entity.Player
import java.util.Comparator
import java.util.HashMap

enum class StatsManager (var stat: String) {
    KILLS("kills"),
    DEATHS("deaths"),
    WINS("wins"),
    GAPPS("gapps-eaten"),
    PLAYED("games-played");


    fun getValue(p: Player): Int {
        return UhcMeetup.pl.pm.getUhcPlayer(p).getFile().c().getInt("stats.$stat", 0)
    }

    fun add(p: Player) {
        val file = UhcMeetup.pl.pm.getUhcPlayer(p).getFile()
        file.c().set("stats.$stat", file.c().getInt("stats.$stat",0) + 1)
        file.save()
    }

    fun getTop(p: Player): MutableList<String> {
        val list = mutableListOf<String>()
        val hash = HashMap<String, Int>()
        FileUtils.getConfigs().forEach { c -> hash[c.getString("username")!!] = c.getInt("stats$stat", 0) }
        val entry: List<Map.Entry<String, Int>> = hash.entries.stream().sorted(
            java.util.Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(10).toList()

        for (i in entry.toTypedArray().indices) {
            val name = entry[i].key
            val value = entry[i].value
            val top = i + 1
            list.add(Utils.chat("&f#$top &a$name: &6$value"))
        }
        return list
    }
}