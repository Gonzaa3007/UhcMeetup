package com.github.gonzq.uhcmeetup.Scenarios.Types

import com.github.gonzq.uhcmeetup.Managers.ScenarioManager
import com.github.gonzq.uhcmeetup.Managers.WorldManager
import com.github.gonzq.uhcmeetup.Scenarios.Scenario
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.BlockFace
import org.bukkit.block.Chest
import org.bukkit.block.data.Directional
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.sign

class TimeBomb: Listener, Scenario("TimeBomb", ItemStack(Material.TNT),
"&7When someone dies a chest appears with their loot",
"&7that explodes after 30 seconds") {
    private val pl = UhcMeetup.pl
    private var b = false
    private val scen = ScenarioManager.getInstance()
    private val world = WorldManager.getInstance()

    @EventHandler
    fun onDeath(e: PlayerDeathEvent) {
        val p = e.entity
        if (p.world == world.getMeetupWorld()) {
            val loc = p.location
            if (sign(loc.blockY.toDouble()) == -1.0 || sign(loc.blockY.toDouble()) == 0.0) return

            val leftSide = loc.block
            val rightSide = loc.clone().add(-1.0, 0.0, 0.0).block

            if (rightSide.getRelative(BlockFace.UP).type == Material.BEDROCK)
                rightSide.getRelative(BlockFace.UP).type = Material.AIR
            if (leftSide.getRelative(BlockFace.UP).type == Material.BEDROCK)
                leftSide.getRelative(BlockFace.UP).type = Material.AIR

            leftSide.type = Material.CHEST
            rightSide.type = Material.CHEST

            val leftData = leftSide.blockData
            (leftData as Directional).facing = BlockFace.NORTH
            leftSide.blockData = leftData

            val chestDataLeft = leftData as org.bukkit.block.data.type.Chest
            chestDataLeft.type = org.bukkit.block.data.type.Chest.Type.RIGHT
            leftSide.blockData = chestDataLeft

            val leftChest = leftSide.state as Chest

            val rightData = rightSide.blockData
            (rightData as Directional).facing = BlockFace.NORTH
            rightSide.blockData = rightData

            val chestDataRight = rightData as org.bukkit.block.data.type.Chest
            chestDataRight.type = org.bukkit.block.data.type.Chest.Type.LEFT
            rightSide.blockData = chestDataRight

            val rightChest = rightSide.state as Chest

            for (i in e.drops.indices) {
                val item = e.drops[i]
                if (item == null || item.type == Material.AIR) continue

                if (i < 27) {
                    leftChest.inventory.addItem(item)
                } else rightChest.inventory.addItem(item)
            }

            if (scen.getScenario("GoldenRetriever")!!.isEnabled()) {
                if (e.drops.size + 1 <= 27) {
                    leftChest.inventory.addItem(Utils.goldenHead())
                } else rightChest.inventory.addItem(Utils.goldenHead())
            }

            if (scen.getScenario("HeavyPockets")!!.isEnabled()) {
                val a = ItemStack(Material.NETHERITE_SCRAP,2)
                val g = ItemStack(Material.GOLD_INGOT,4)
                if (e.drops.size + 6 <= 27) {
                    leftChest.inventory.addItem(a,g)
                } else rightChest.inventory.addItem(a,g)
            }
            e.drops.clear()

            var stand = p.world.spawnEntity(leftChest.location.clone().add(0.0, 1.0, 0.5), EntityType.ARMOR_STAND) as ArmorStand
            stand.isCustomNameVisible = true
            stand.isSmall = true
            stand.setGravity(false)
            stand.isVisible = false
            stand.isMarker = true

            object : BukkitRunnable() {
                var time = 30
                override fun run() {
                    time--
                    if (time == 0) {
                        Bukkit.broadcastMessage(Utils.chat("&8[&4TimeBomb&8] &a${p.name}'s &fcorpse has explode!"))
                        leftChest.inventory.clear()
                        leftSide.type = Material.AIR
                        rightSide.type = Material.AIR
                        loc.world!!.createExplosion(loc.blockX + 0.5, loc.blockY + 0.5, loc.blockZ + 0.5,5f,false,true)
                        loc.world!!.strikeLightningEffect(loc)
                        stand.remove()
                        cancel()
                    } else if (time == 1) {
                        stand.customName = Utils.chat("&4" + time + "s")
                        Bukkit.getOnlinePlayers().forEach{p -> p.playSound(stand.location, Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE,1f,1f)}
                    } else if (time == 2) {
                        stand.customName = Utils.chat("&c" + time + "s")
                        Bukkit.getOnlinePlayers().forEach{p -> p.playSound(stand.location, Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE,1f,1f)}
                    } else if (time == 3) {
                        stand.customName = Utils.chat("&6" + time + "s")
                        Bukkit.getOnlinePlayers().forEach{p -> p.playSound(stand.location, Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE,1f,1f)}
                    } else if (time <= 15) {
                        stand.customName = Utils.chat("&e" + time + "s")
                    } else stand.customName = Utils.chat("&a" + time + "s")
                }
            }.runTaskTimer(pl,0,20)
        }
    }

    override fun setEnabled(enable: Boolean) {
        b = enable
    }

    override fun isEnabled(): Boolean {
        return b
    }

}