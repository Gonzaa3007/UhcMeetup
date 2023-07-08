package com.github.gonzq.uhcmeetup.Guis

import com.github.gonzq.uhcmeetup.Managers.StatsManager
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import fr.mrmicky.fastinv.FastInv
import fr.mrmicky.fastinv.ItemBuilder
import org.bukkit.Material
import org.bukkit.entity.Player

class StatsGui(player: Player): FastInv(9, "Stats") {
    val pl = UhcMeetup.pl
    val stats = StatsManager.getInstance()

    init {
        val p = pl.pm.getUhcPlayer(player)
        addItem(ItemBuilder(Material.GOLD_BLOCK).name(Utils.chat("&aWins: ${stats.getValue(p, StatsManager.Stats.WINS)}")).build())
        addItem(ItemBuilder(Material.SKELETON_SKULL).name(Utils.chat("&cDeaths: ${stats.getValue(p, StatsManager.Stats.DEATHS)}")).build())
        addItem(ItemBuilder(Material.IRON_SWORD).name(Utils.chat("&cKills: ${stats.getValue(p, StatsManager.Stats.KILLS)}")).build())
        addItem(ItemBuilder(Material.GOLDEN_APPLE).name(Utils.chat("&6Gapps Eaten: ${stats.getValue(p, StatsManager.Stats.GAPPS)}")).build())
        addItem(ItemBuilder(Material.GRASS_BLOCK).name(Utils.chat("&aGames Played: ${stats.getValue(p, StatsManager.Stats.PLAYED)}")).build())

        addOpenHandler { e -> e.player.sendMessage(Utils.chat("${pl.prefix}Opening ${p.name}'s stats...")) }
        addClickHandler {e -> e.isCancelled = true}
    }
}