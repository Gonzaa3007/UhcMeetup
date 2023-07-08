package com.github.gonzq.uhcmeetup.Scenarios.Types

import com.github.gonzq.uhcmeetup.Enums.GameState
import com.github.gonzq.uhcmeetup.Managers.GameManager
import com.github.gonzq.uhcmeetup.Scenarios.Scenario
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack

class BloodHunter: Listener, Scenario("BloodHunter", ItemStack(Material.IRON_SWORD),
"&7Killing someone adds an extra heart") {
    private var b = false
    private val game = GameManager.getInstance()

    @EventHandler
    fun onKill(e: PlayerDeathEvent) {
        val p = e.entity
        val k = p.killer
        if (game.getState() == GameState.STARTED && k != null) k.maxHealth += 2
    }

    override fun setEnabled(enable: Boolean) {
        b = enable
    }

    override fun isEnabled(): Boolean {
        return b
    }
}