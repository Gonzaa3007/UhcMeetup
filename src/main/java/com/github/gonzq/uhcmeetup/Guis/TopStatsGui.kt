package com.github.gonzq.uhcmeetup.Guis

import com.github.gonzq.uhcmeetup.Managers.StatsManager
import com.github.gonzq.uhcmeetup.Utils.Utils
import fr.mrmicky.fastinv.FastInv
import fr.mrmicky.fastinv.ItemBuilder
import org.bukkit.Material
import org.bukkit.entity.Player

class TopStatsGui(p: Player): FastInv(9, "Top Stats") {

    init {
        addItem(ItemBuilder(Material.GOLD_BLOCK).name(Utils.chat("&aTop Wins:")).lore(StatsManager.WINS.getTop(p)).build())
        addItem(ItemBuilder(Material.SKELETON_SKULL).name(Utils.chat("&cTop Deaths:")).lore(StatsManager.DEATHS.getTop(p)).build())
        addItem(ItemBuilder(Material.IRON_SWORD).name(Utils.chat("&cTop Kills:")).lore(StatsManager.KILLS.getTop(p)).build())
        addItem(ItemBuilder(Material.GOLDEN_APPLE).name(Utils.chat("&6Top Gapps Eaten")).lore(StatsManager.GAPPS.getTop(p)).build())
        addItem(ItemBuilder(Material.GRASS_BLOCK).name(Utils.chat("&aTop Games Played")).lore(StatsManager.PLAYED.getTop(p)).build())
        addClickHandler{e -> e.isCancelled = true}
    }
}