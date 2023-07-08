package com.github.gonzq.uhcmeetup.Scenarios.Types

import com.github.gonzq.uhcmeetup.Scenarios.Scenario
import com.github.gonzq.uhcmeetup.Utils.Utils
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class GappleRoulette: Listener, Scenario("GappleRoulette", ItemStack(Material.GOLDEN_APPLE),
"&7Eating a golden apple will give you a random potion effect") {
    private var b = false


    @EventHandler
    fun onConsume(e: PlayerItemConsumeEvent) {
        val p = e.player
        if (e.item.type == Material.GOLDEN_APPLE) {
            when (Utils.getRandomInt(14)) {
                1 -> p.addPotionEffect(PotionEffect(PotionEffectType.GLOWING, 250, 1));
                2 -> p.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 250, 0));
                3 -> p.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 250, 0));
                4 -> p.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 250, 1));
                5 -> p.addPotionEffect(PotionEffect(PotionEffectType.SATURATION, 250, 0));
                6 -> p.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, 250, 1));
                7 -> p.addPotionEffect(PotionEffect(PotionEffectType.CONDUIT_POWER, 250, 1));
                8 -> p.addPotionEffect(PotionEffect(PotionEffectType.FIRE_RESISTANCE, 250, 0));
                9 -> p.addPotionEffect(PotionEffect(PotionEffectType.CONFUSION, 250, 2));
                10 -> p.addPotionEffect(PotionEffect(PotionEffectType.DOLPHINS_GRACE, 250, 1));
                11 -> p.addPotionEffect(PotionEffect(PotionEffectType.HEALTH_BOOST, 250, 4));
                12 -> p.addPotionEffect(PotionEffect(PotionEffectType.LUCK, 250, 4));
                13 -> p.addPotionEffect(PotionEffect(PotionEffectType.WITHER, 250, 0));
                14 -> p.addPotionEffect(PotionEffect(PotionEffectType.POISON, 250, 0));
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