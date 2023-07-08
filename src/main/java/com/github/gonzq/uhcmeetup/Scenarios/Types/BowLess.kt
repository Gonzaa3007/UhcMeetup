package com.github.gonzq.uhcmeetup.Scenarios.Types

import com.github.gonzq.uhcmeetup.Scenarios.Scenario
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.inventory.ItemStack

class BowLess: Listener, Scenario("BowLess", ItemStack(Material.BOW), "&7You cannot use bows") {
    private var b = false

    @EventHandler
    fun onShoot(e: EntityShootBowEvent) {
        if (e.entity is Player) e.isCancelled = true
    }

    override fun setEnabled(enable: Boolean) {
        b = enable
    }

    override fun isEnabled(): Boolean {
        return b
    }
}