package com.github.gonzq.uhcmeetup.Scenarios.Types

import com.github.gonzq.uhcmeetup.Managers.WorldManager
import com.github.gonzq.uhcmeetup.Scenarios.Scenario
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack

class WebCage: Listener, Scenario("WebCage", ItemStack(Material.COBWEB), "&7Killing someone spawns a box of cobwebs around",
"&7of the player who died.") {
    private var b = false

    @EventHandler
    fun onDeath(e: PlayerDeathEvent) {
        val p = e.entity
        if (p.world == WorldManager.getInstance().getMeetupWorld()) {
            p.world.getBlockAt(p.location.add(3.0,0.0,0.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(3.0,1.0,0.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(3.0,2.0,0.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(0.0,0.0,3.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(0.0,1.0,3.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(0.0,2.0,3.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-3.0,0.0,0.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-3.0,1.0,0.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-3.0,2.0,0.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(0.0,0.0,-3.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(0.0,1.0,-3.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(0.0,2.0,-3.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(2.0,0.0,2.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(2.0,1.0,2.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(2.0,2.0,2.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-2.0,0.0,2.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-2.0,1.0,2.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-2.0,2.0,2.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-2.0,0.0,-2.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-2.0,1.0,-2.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-2.0,2.0,-2.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(2.0,0.0,-2.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(2.0,1.0,-2.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(2.0,2.0,-2.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(3.0,0.0,1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(3.0,1.0,1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(3.0,2.0,1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(1.0,0.0,3.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(1.0,1.0,3.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(1.0,2.0,3.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-3.0,0.0,1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-3.0,1.0,1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-3.0,2.0,1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(1.0,0.0,-3.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(1.0,1.0,-3.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(1.0,2.0,-3.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(3.0,0.0,-1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(3.0,1.0,-1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(3.0,2.0,-1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-1.0,0.0,3.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-1.0,1.0,3.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-1.0,2.0,3.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-3.0,0.0,-1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-3.0,1.0,-1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-3.0,2.0,-1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-1.0,0.0,-3.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-1.0,1.0,-3.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-1.0,2.0,-3.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-1.0,3.0,-2.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-1.0,3.0,-1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-1.0,3.0,0.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-1.0,3.0,2.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-1.0,3.0,1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(0.0,3.0,-1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(1.0,3.0,2.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(1.0,3.0,1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(1.0,3.0,0.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(1.0,3.0,-2.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(1.0,3.0,-1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(0.0,3.0,1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(2.0,3.0,1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-2.0,3.0,1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(2.0,3.0,-1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-2.0,3.0,-1.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(2.0,3.0,0.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(-2.0,3.0,0.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(0.0,3.0,2.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(0.0,3.0,-2.0)).type = Material.COBWEB
            p.world.getBlockAt(p.location.add(0.0,3.0,0.0)).type = Material.COBWEB
        }
    }

    override fun setEnabled(enable: Boolean) {
        b = enable
    }

    override fun isEnabled(): Boolean {
        return b
    }
}