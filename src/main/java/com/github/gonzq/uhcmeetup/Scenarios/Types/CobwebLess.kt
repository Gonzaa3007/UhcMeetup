package com.github.gonzq.uhcmeetup.Scenarios.Types

import com.github.gonzq.uhcmeetup.Scenarios.Scenario
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.inventory.ItemStack

class CobwebLess: Listener, Scenario("CobwebLess", ItemStack(Material.COBWEB), "&7You can't use cobwebs") {
    private var b = false

    @EventHandler
    fun onPlace(e: BlockPlaceEvent) {
        if (e.block.type == Material.COBWEB) e.isCancelled = true
    }

    override fun setEnabled(enable: Boolean) {
        b = enable
    }

    override fun isEnabled(): Boolean {
        return b
    }
}