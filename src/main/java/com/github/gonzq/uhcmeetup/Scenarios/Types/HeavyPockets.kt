package com.github.gonzq.uhcmeetup.Scenarios.Types

import com.github.gonzq.uhcmeetup.Managers.ScenarioManager
import com.github.gonzq.uhcmeetup.Managers.WorldManager
import com.github.gonzq.uhcmeetup.Scenarios.Scenario
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack

class HeavyPockets: Listener, Scenario("HeavyPockets", ItemStack(Material.NETHERITE_SCRAP),
"&7When someone dies drop 2 netherite scrap") {
    private var b = false
    private val scen = ScenarioManager.getInstance()

    @EventHandler
    fun onDeath(e: PlayerDeathEvent) {
        val p = e.entity
        if (!scen.getScenario("TimeBomb")!!.isEnabled() && !scen.getScenario("GraveRobbers")!!.isEnabled()) {
            if (p.world == WorldManager.getInstance().getMeetupWorld()) {
                e.drops.add(ItemStack(Material.NETHERITE_SCRAP,2))
                e.drops.add(ItemStack(Material.GOLD_INGOT,4))
            }
        }
    }

    override fun setEnabled(enable: Boolean) {
        b = enable
    }

    override fun isEnabled(): Boolean {
        return b
    }
}