package com.github.gonzq.uhcmeetup.Listeners

import com.github.gonzq.uhcmeetup.Enums.GameState
import com.github.gonzq.uhcmeetup.Managers.GameManager
import com.github.gonzq.uhcmeetup.Managers.StatsManager
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class ConsumeListener: Listener {
    private val pl = UhcMeetup.pl
    private val game = GameManager.getInstance()

    init {
        pl.server.pluginManager.registerEvents(this,pl)
    }

    @EventHandler
    fun onConsume(e: PlayerItemConsumeEvent) {
        val p = pl.pm.getUhcPlayer(e.player)
        if (game.getState() == GameState.STARTED) {
            if (e.item.type == Material.GOLDEN_APPLE)
                StatsManager.getInstance().add(p, StatsManager.Stats.GAPPS)
        }

        if (e.item.isSimilar(Utils.goldenHead())) {
            p.getPlayer().addPotionEffect(PotionEffect(PotionEffectType.REGENERATION,200,1))
        }
    }
}