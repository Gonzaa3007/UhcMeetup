package com.github.gonzq.uhcmeetup.Scenarios.Types

import com.github.gonzq.uhcmeetup.Managers.ScenarioManager
import com.github.gonzq.uhcmeetup.Managers.WorldManager
import com.github.gonzq.uhcmeetup.Scenarios.Scenario
import com.github.gonzq.uhcmeetup.Utils.Utils
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.block.Chest
import org.bukkit.block.Sign
import org.bukkit.block.data.Directional
import org.bukkit.block.data.Rotatable
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack
import kotlin.math.sign

class GraveRobbers: Listener, Scenario("GraveRobbers", ItemStack(Material.GRAVEL),
"&7When killing someone, their grave comes out with their items.") {
    private var b = false
    private val scen = ScenarioManager.getInstance()
    private val world = WorldManager.getInstance()

    @EventHandler
    fun onDeath(e: PlayerDeathEvent) {
        val p = e.entity
        val loc = p.location
        if (p.world == world.getMeetupWorld()) {
            if (sign(loc.blockY.toDouble()) == -1.0 || sign(loc.blockY.toDouble()) == 0.0) return
            val grava = Material.GRAVEL
            loc.clone().add(0.0,+1.0,0.0).block.type = grava
            loc.clone().add(-1.0, +1.0,0.0).block.type = grava
            loc.clone().add(-2.0, +1.0, 0.0).block.type = Material.COBBLESTONE
            loc.clone().add(-2.0, +1.0, -1.0).block.type = Material.MOSSY_COBBLESTONE
            loc.clone().add(-1.0, +1.0, -1.0).block.type = Material.COBBLESTONE
            loc.clone().add(0.0, +1.0, -1.0).block.type = Material.MOSSY_COBBLESTONE
            loc.clone().add(+1.0, +1.0, -1.0).block.type = Material.COBBLESTONE
            loc.clone().add(+1.0, +1.0, 0.0).block.type = Material.COBBLESTONE
            loc.clone().add(+1.0, +1.0, +1.0).block.type = Material.COBBLESTONE
            loc.clone().add(0.0, +1.0, +1.0).block.type = Material.MOSSY_COBBLESTONE
            loc.clone().add(-1.0, +1.0, +1.0).block.type = Material.COBBLESTONE
            loc.clone().add(-2.0, +1.0, +1.0).block.type = Material.MOSSY_COBBLESTONE
            val s = loc.clone().add(-2.0, +2.0, 0.0).block
            s.type = Material.WARPED_SIGN
            val sign = s.state as Sign
            val dir = sign.blockData as Rotatable
            dir.rotation = BlockFace.EAST
            sign.blockData = dir
            sign.update(true)
            sign.lines[0] = Utils.chat("&cR.I.P")
            sign.lines[1] = p.name
            if (p.killer != null) {
                sign.lines[2] = Utils.chat("&cKilled by")
                sign.lines[3] = p.killer!!.name
            }
            sign.update()

            val leftSide = loc.block
            val rightSide = loc.clone().add(-1.0,0.0,0.0).block

            if (rightSide.getRelative(BlockFace.UP).type == Material.BEDROCK) {
                rightSide.getRelative(BlockFace.UP).type = Material.AIR
            }
            if (leftSide.getRelative(BlockFace.UP).type == Material.BEDROCK) {
                leftSide.getRelative(BlockFace.UP).type = Material.AIR
            }

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
                    leftChest.inventory.addItem(a)
                    leftChest.inventory.addItem(g)
                } else {
                    rightChest.inventory.addItem(a)
                    rightChest.inventory.addItem(g)
                }
            }
            e.drops.clear()
        }
    }

    override fun setEnabled(enable: Boolean) {
        b = enable
    }

    override fun isEnabled(): Boolean {
        return b
    }
}