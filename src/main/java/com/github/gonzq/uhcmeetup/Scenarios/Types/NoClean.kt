package com.github.gonzq.uhcmeetup.Scenarios.Types

import com.github.gonzq.uhcmeetup.Scenarios.Scenario
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import org.bukkit.Material
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.entity.SpectralArrow
import org.bukkit.entity.Trident
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerBucketEmptyEvent
import org.bukkit.inventory.ItemStack
import java.util.UUID

class NoClean(val pl: UhcMeetup, val time: Int): Listener, Scenario("NoClean", ItemStack(Material.QUARTZ),
"&7When you kill someone you will be immune for $time") {
    private var b = false
    private val noCleanList = mutableListOf<UUID>()

    @EventHandler
    fun noClean(e: PlayerDeathEvent) {
        if (e.entity.killer != null) {
            val k = e.entity.killer!!
            k.isInvulnerable = true
            noCleanList.add(k.uniqueId)
            k.sendMessage(Utils.chat(pl.prefix + pl.lang.getConfig().getString("noclean")!!
                .replace("%time%", "$time")))

            Utils.delay(time * 20L) {
                if (noCleanList.contains(k.uniqueId)) {
                    noCleanList.remove(k.uniqueId)
                    k.isInvulnerable = false
                    k.sendMessage(Utils.chat("${pl.prefix}You are no longer invulnerable."))
                }
            }
        }
    }

    @EventHandler
    fun onDamage(e: EntityDamageByEntityEvent) {
        val p = e.entity
        val damager = e.damager
        if (p is Player) {
            if (damager is Player) {
                if (noCleanList.contains(damager.uniqueId)) {
                    noCleanList.remove(damager.uniqueId)
                    damager.isInvulnerable = false
                    damager.sendMessage(Utils.chat("${pl.prefix}You are no longer invulnerable"))
                }
            } else if (damager is Arrow && damager.shooter is Player) {
                val d = damager.shooter as Player
                if (noCleanList.contains(d.uniqueId)) {
                    noCleanList.remove(d.uniqueId)
                    d.isInvulnerable = false
                    d.sendMessage(Utils.chat("${pl.prefix}You are no longer invulnerable"))
                }
            } else if (damager is SpectralArrow && damager.shooter is Player) {
                val d = damager.shooter as Player
                if (noCleanList.contains(d.uniqueId)) {
                    noCleanList.remove(d.uniqueId)
                    d.isInvulnerable = false
                    d.sendMessage(Utils.chat("${pl.prefix}You are no longer invulnerable"))
                }
            } else if (damager is Trident && damager.shooter is Player) {
                val d = damager.shooter as Player
                if (noCleanList.contains(d.uniqueId)) {
                    noCleanList.remove(d.uniqueId)
                    d.isInvulnerable = false
                    d.sendMessage(Utils.chat("${pl.prefix}You are no longer invulnerable"))
                }
            }
        }
    }

    @EventHandler
    fun onLava(e: PlayerBucketEmptyEvent) {
        val p = e.player
        if (e.bucket == Material.LAVA_BUCKET) {
            if (noCleanList.contains(p.uniqueId)) {
                noCleanList.remove(p.uniqueId)
                p.isInvulnerable = false
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