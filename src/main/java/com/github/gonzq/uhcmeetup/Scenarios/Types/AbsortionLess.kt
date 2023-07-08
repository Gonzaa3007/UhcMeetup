package com.github.gonzq.uhcmeetup.Scenarios.Types

import com.github.gonzq.uhcmeetup.Scenarios.Scenario
import com.github.gonzq.uhcmeetup.Utils.Utils
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffectType

class AbsortionLess: Listener, Scenario("AbsortionLess", ItemStack(Material.GOLDEN_APPLE),
"&7Eating a golden apple will not give you the absorption") {
    private var b = false

    @EventHandler
    fun onConsume(e: PlayerItemConsumeEvent) {
        if (e.item.type == Material.GOLDEN_APPLE) Utils.delay(1)  {
            e.player.removePotionEffect(PotionEffectType.ABSORPTION)
        }
    }

    override fun setEnabled(enable: Boolean) {
        b = enable
    }

    override fun isEnabled(): Boolean {
        return b
    }
}