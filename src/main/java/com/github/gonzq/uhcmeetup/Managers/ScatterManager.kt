package com.github.gonzq.uhcmeetup.Managers

import com.github.gonzq.uhcmeetup.Enums.PlayerState
import com.github.gonzq.uhcmeetup.Players.GamePlayer
import com.github.gonzq.uhcmeetup.UhcMeetup
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Biome
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.Random


class ScatterManager {
    private var pl = UhcMeetup.pl

    companion object {
        private var instance = ScatterManager()

        fun getInstance(): ScatterManager {
            return instance
        }
    }

    fun scatter(p: GamePlayer) {
        val player = p.getPlayer()
        player.teleport(calculateCoords(), PlayerTeleportEvent.TeleportCause.PLUGIN)
        player.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, 120000, 5))
        player.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 120000, 127))
        player.addPotionEffect(PotionEffect(PotionEffectType.JUMP, 120000, 200))
        player.addPotionEffect(PotionEffect(PotionEffectType.SLOW_DIGGING, 120000, 5))
        player.addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 12000, 5))
        player.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 120000, 5))
        player.addPotionEffect(PotionEffect(PotionEffectType.WATER_BREATHING, 120000, 5))
        player.gameMode = GameMode.SURVIVAL
        p.setKit()
    }

    fun postScatter(p: GamePlayer) {
        p.getPlayer().activePotionEffects.forEach { pe -> p.getPlayer().removePotionEffect(pe.type) }
        p.setState(PlayerState.PLAYING)
        p.getPlayer().isInvulnerable = false
        StatsManager.getInstance().add(p, StatsManager.Stats.PLAYED)
    }

    fun calculateCoords(): Location {
        val wm = WorldManager.getInstance()
        val rand = Random()
        val upperbound = wm.getBorder()
        var x = rand.nextInt(upperbound).toDouble()
        var z = rand.nextInt(upperbound).toDouble()
        val randDouble = Math.random()
        val randDouble2 = Math.random()
        if (randDouble <= 0.5) x *= -1
        if (randDouble2 <= 0.5) z *= -1
        val y = wm.getMeetupWorld()!!.getHighestBlockYAt(x.toInt(),z.toInt()) + 1
        val l = Location(wm.getMeetupWorld(), x,y.toDouble(),z);
        return if (isWaterNearby(l)) calculateCoords() else l;
    }

    private fun isWaterNearby(l: Location): Boolean {
        var isWater = false
        if (l.block.type == Material.WATER) {
            isWater = true;
        }
        else if (l.block.biome.equals(Biome.OCEAN) ) {
            isWater = true;
        }
        else if (l.block.biome.equals(Biome.RIVER)) {
            isWater = true;
        }
        if (l.add(0.0,-1.0,0.0).block.type == Material.WATER) {
            isWater = true;
        }
        if (l.block.type.equals(Material.WATER)) {
            isWater = true;
        }
        if (l.block.type.equals(Material.VOID_AIR)) {
            isWater = true;
        }
        if (l.add(0.0,-1.0,0.0).block.type == Material.VOID_AIR) {
            isWater = true;
        }
        return isWater
    }
}