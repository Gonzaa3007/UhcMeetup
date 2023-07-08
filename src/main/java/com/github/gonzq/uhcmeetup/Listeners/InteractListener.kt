package com.github.gonzq.uhcmeetup.Listeners

import com.github.gonzq.uhcmeetup.Enums.GameState
import com.github.gonzq.uhcmeetup.Guis.ScenarioGui
import com.github.gonzq.uhcmeetup.Managers.GameManager
import com.github.gonzq.uhcmeetup.Managers.WorldManager
import com.github.gonzq.uhcmeetup.UhcMeetup
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class InteractListener: Listener {
    private val pl = UhcMeetup.pl
    private val world = WorldManager.getInstance()
    private val game = GameManager.getInstance()

    init {
        pl.server.pluginManager.registerEvents(this,pl)
    }

    @EventHandler
    fun onItem(e: PlayerInteractEvent) {
        val p = e.player
        val item = e.item
        if (item != null && e.hasItem()) {
            if (game.getState() == GameState.WAITING) {
                if (item.type == Material.ENCHANTED_BOOK) {
                    ScenarioGui().open(p)
                    return
                }
            }
            if (p.world == world.getLobby()) {
                when (item.type) {
                    Material.BOOK -> p.chat("/scen")
                    Material.DIAMOND -> p.chat("/stats")
                    else -> {}
                }
            }
        }
    }
}