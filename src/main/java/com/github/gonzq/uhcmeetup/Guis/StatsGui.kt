package com.github.gonzq.uhcmeetup.Guis

import com.github.gonzq.uhcmeetup.Managers.StatsManager
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import fr.mrmicky.fastinv.FastInv
import fr.mrmicky.fastinv.ItemBuilder
import org.bukkit.Material
import org.bukkit.entity.Player

class StatsGui(p: Player): FastInv(9, "Stats") {
    val pl = UhcMeetup.pl

    init {
        addItem(ItemBuilder(Material.GOLD_BLOCK).name(Utils.chat("&aWins: ${StatsManager.WINS.getValue(p)}")).build())
        addItem(ItemBuilder(Material.SKELETON_SKULL).name(Utils.chat("&cDeaths: ${StatsManager.DEATHS.getValue(p)}")).build())
        addItem(ItemBuilder(Material.IRON_SWORD).name(Utils.chat("&cKills: ${StatsManager.KILLS.getValue(p)}")).build())
        addItem(ItemBuilder(Material.GOLDEN_APPLE).name(Utils.chat("&6Gapps Eaten: ${StatsManager.GAPPS.getValue(p)}")).build())
        addItem(ItemBuilder(Material.GRASS_BLOCK).name(Utils.chat("&aGames Played: ${StatsManager.PLAYED.getValue(p)}")).build())

        addOpenHandler { e -> e.player.sendMessage(Utils.chat("${pl.prefix}Opening ${p.name}'s stats...")) }
        addClickHandler {e -> e.isCancelled = true}
    }
}