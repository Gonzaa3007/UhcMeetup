package com.github.gonzq.uhcmeetup.Scenarios.Types

import com.github.gonzq.uhcmeetup.Managers.ScenarioManager
import com.github.gonzq.uhcmeetup.Managers.WorldManager
import com.github.gonzq.uhcmeetup.Scenarios.Scenario
import com.github.gonzq.uhcmeetup.Utils.Utils
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack

class GoldenRetriever: Listener, Scenario("GoldenRetriever", ItemStack(Material.GOLDEN_APPLE),
"&7When someone dies they drop a golden head") {
    private var b = false
    private val scen = ScenarioManager.getInstance()
    private val world = WorldManager.getInstance()

    @EventHandler
    fun onDeath(e: PlayerDeathEvent) {
        if (!scen.getScenario("TimeBomb")!!.isEnabled() && !scen.getScenario("GraveRobbers")!!.isEnabled()) {
            if (e.entity.world == world.getMeetupWorld()) e.drops.add(Utils.goldenHead())
        }
    }

    override fun setEnabled(enable: Boolean) {
        b = enable
    }

    override fun isEnabled(): Boolean {
        return b
    }
}