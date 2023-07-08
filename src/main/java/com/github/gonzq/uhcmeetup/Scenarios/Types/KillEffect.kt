package com.github.gonzq.uhcmeetup.Scenarios.Types

import com.github.gonzq.uhcmeetup.Scenarios.Scenario
import com.github.gonzq.uhcmeetup.Utils.Utils
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class KillEffect: Listener, Scenario("KillEffect", ItemStack(Material.IRON_SWORD),
"&7When you kill someone it will give you a random potion effect") {
    private var b = false

    @EventHandler
    fun onKill(e: PlayerDeathEvent) {
        val k = e.entity.killer
        if (k != null) {
            when (Utils.getRandomInt(14)) {
                1 -> k.addPotionEffect(PotionEffect(PotionEffectType.GLOWING,250,1));
                2 -> k.addPotionEffect(PotionEffect(PotionEffectType.SPEED,250,0));
                3 -> k.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY,250,0));
                4 -> k.addPotionEffect(PotionEffect(PotionEffectType.SLOW,250,1));
                5 -> k.addPotionEffect(PotionEffect(PotionEffectType.SATURATION,250,0));
                6 -> k.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS,250,1));
                7 -> k.addPotionEffect(PotionEffect(PotionEffectType.CONDUIT_POWER,250,1));
                8 -> k.addPotionEffect(PotionEffect(PotionEffectType.FIRE_RESISTANCE,250,0));
                9 -> k.addPotionEffect(PotionEffect(PotionEffectType.CONFUSION,250,2));
                10 -> k.addPotionEffect(PotionEffect(PotionEffectType.DOLPHINS_GRACE,250,1));
                11 -> k.addPotionEffect(PotionEffect(PotionEffectType.HEALTH_BOOST,250,4));
                12 -> k.addPotionEffect(PotionEffect(PotionEffectType.LUCK,250,4));
                13 -> k.addPotionEffect(PotionEffect(PotionEffectType.WITHER,250,0));
                14 -> k.addPotionEffect(PotionEffect(PotionEffectType.POISON,250,0));
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