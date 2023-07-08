package com.github.gonzq.uhcmeetup.Scenarios.Types

import com.github.gonzq.uhcmeetup.Scenarios.Scenario
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class FastGetaway: Listener, Scenario("FastGetaway", ItemStack(Material.SUGAR),
"&7When you kill someone you will have Speed II for 1 minute") {
    private var b = false

    @EventHandler
    fun onKill(e: PlayerDeathEvent) {
        e.entity.killer?.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 1200, 1, true, true))
    }

    override fun setEnabled(enable: Boolean) {
        b = enable
    }

    override fun isEnabled(): Boolean {
        return b
    }
}