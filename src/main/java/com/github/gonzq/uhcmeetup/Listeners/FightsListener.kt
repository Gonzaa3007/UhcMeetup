package com.github.gonzq.uhcmeetup.Listeners

import com.github.gonzq.uhcmeetup.Enums.GameState
import com.github.gonzq.uhcmeetup.Managers.GameManager
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import org.bukkit.Sound
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class FightsListener: Listener {
    private val pl = UhcMeetup.pl
    private val game = GameManager.getInstance()
    private val msg = pl.lang.getConfig().getString("arrow-damage")!!

    init {
        pl.server.pluginManager.registerEvents(this,pl)
    }

    @EventHandler
    fun onShoot(e: EntityDamageByEntityEvent) {
        val p = e.entity
        val damager = e.damager
        if (game.getState() == GameState.STARTED) {
            if (p is Player && damager is Arrow && damager.shooter is Player) {
                val d = damager.shooter as Player
                Utils.delay(1) {
                    val h = p.health + p.absorptionAmount
                    if (h > 20) {
                        d.sendMessage(Utils.chat(msg.replace("%player%", p.name).replace("%color%", "&2")
                            .replace("%hearts%", "${h.toInt()}")))
                    } else if (h >= 16) {
                        d.sendMessage(Utils.chat(msg.replace("%player%", p.name).replace("%color%", "&2")
                            .replace("%hearts%", "${h.toInt()}")))
                    } else if (h >= 12){
                        d.sendMessage(Utils.chat(msg.replace("%player%", p.name).replace("%color%", "&a")
                            .replace("%hearts%", "${h.toInt()}")))
                    } else if (h >= 8) {
                        d.sendMessage(Utils.chat(msg.replace("%player%", p.name).replace("%color%", "&6")
                            .replace("%hearts%", "${h.toInt()}")))
                    } else if (h >= 4) {
                        d.sendMessage(Utils.chat(msg.replace("%player%", p.name).replace("%color%", "&c")
                            .replace("%hearts%", "${h.toInt()}")))
                    } else d.sendMessage(Utils.chat(msg.replace("%player%", p.name).replace("%color%", "&4")
                        .replace("%hearts%", "${h.toInt()}")))
                }
            }
        }
    }

    @EventHandler
    fun onShieldSound(e: EntityDamageByEntityEvent) {
        if (pl.config.getConfig().getBoolean("shield-break-sound")) {
            val p = e.entity
            val d = e.damager
            if (p is Player && d is Player) {
                if (d.inventory.itemInMainHand.type.toString().endsWith("_AXE") && p.isBlocking) {
                    val h1 = p.health + p.absorptionAmount
                    Utils.delay(1) {
                        val h2 = p.health + p.absorptionAmount
                        if (h1 == h2) d.playSound(p.location, Sound.ITEM_SHIELD_BREAK,1f,1f)
                    }
                }
            }
        }
    }
}