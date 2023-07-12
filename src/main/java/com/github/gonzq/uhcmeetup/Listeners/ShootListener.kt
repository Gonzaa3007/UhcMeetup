package com.github.gonzq.uhcmeetup.Listeners

import com.github.gonzq.uhcmeetup.Enums.GameState
import com.github.gonzq.uhcmeetup.Managers.GameManager
import com.github.gonzq.uhcmeetup.Managers.StatsManager
import com.github.gonzq.uhcmeetup.UhcMeetup
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityShootBowEvent

class ShootListener: Listener {
    private val pl = UhcMeetup.pl
    init {
        pl.server.pluginManager.registerEvents(this,pl)
    }

    @EventHandler
    fun onShoot(e: EntityShootBowEvent) {
        val ent = e.entity
        if (GameManager.getInstance().getState() == GameState.STARTED &&
                ent is Player) {
            StatsManager.getInstance().add(pl.pm.getUhcPlayer(ent), StatsManager.Stats.BOW_SHOOTS)
        }
    }
}