package com.github.gonzq.uhcmeetup.Players

import com.github.gonzq.uhcmeetup.Enums.PlayerState
import com.github.gonzq.uhcmeetup.Managers.GameManager
import com.github.gonzq.uhcmeetup.Managers.ScenarioManager
import com.github.gonzq.uhcmeetup.Managers.StatsManager
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.FileUtils
import com.github.gonzq.uhcmeetup.Utils.Utils
import fr.mrmicky.fastinv.ItemBuilder
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionType
import java.util.UUID
import kotlin.properties.Delegates

class GamePlayer (var uid: UUID, var name: String) {
    private var state: PlayerState
    private var kills: Int = 0
    private var isVoteScen: Boolean
    private var file: PlayerFile

    init {
        isVoteScen = false
        state = PlayerState.WAITING
        file = PlayerFile.get(uid)
    }

    fun getPlayer(): Player {
        return Bukkit.getPlayer(uid)!!
    }

    fun isVotedScen(): Boolean {
        return isVoteScen
    }

    fun setVoteScen(b: Boolean) {
        isVoteScen = b
    }

    fun getFile(): PlayerFile {
        return file
    }

    fun isOnline(): Boolean {
        return getPlayer() != null
    }

    fun getState(): PlayerState {
        return state
    }

    fun isPlaying(): Boolean {
        return state == PlayerState.PLAYING
    }

    fun isSpectating(): Boolean {
        return state == PlayerState.SPECTATING
    }

    fun setState(state: PlayerState) {
        this.state = state
    }

    fun getKills(): Int {
        return kills
    }

    fun addKill() {
        StatsManager.getInstance().add(this, StatsManager.Stats.KILLS)
        kills++
    }

    fun setKit() {
        val c = UhcMeetup.pl.kit.getConfig()
        if (isOnline()) {
            val weaponKey = "kits.weapons"
            val armorKey = "kits.armor"
            val inv = getPlayer().inventory
            inv.clear()
            inv.setItemInOffHand(ItemStack(Material.SHIELD))


            // -------------------------------------------- Helmet --------------------------------------------
            val helmet: ItemStack
            val hNumber = Utils.getRandomInt(c.getConfigurationSection("$armorKey.helmets")!!.getKeys(false).size)
            helmet = ItemStack(Material.valueOf(c.getString("$armorKey.helmets.$hNumber.item")!!))
            if (c.getString("$armorKey.helmets.$hNumber.enchants") != "none") {
                c.getString("$armorKey.helmets.$hNumber.enchants")!!.split(";").forEach { s ->
                    val enchant = s.split("-")
                    helmet.addEnchantment(Enchantment.getByName(enchant[0])!!, enchant[1].toInt())
                }
            }
            inv.helmet = helmet

            // -------------------------------------------- ChestPlate --------------------------------------------
            val chesplate: ItemStack
            val cNumber = Utils.getRandomInt(c.getConfigurationSection("$armorKey.chestplates")!!.getKeys(false).size)
            chesplate = ItemStack(Material.valueOf(c.getString("$armorKey.chestplates.$cNumber.item")!!))
            if (c.getString("$armorKey.chestplates.$cNumber.enchants") != "none") {
                c.getString("$armorKey.chestplates.$cNumber.enchants")!!.split(";").forEach { s ->
                    val enchant = s.split("-")
                    chesplate.addEnchantment(Enchantment.getByName(enchant[0])!!, enchant[1].toInt())
                }
            }
            inv.chestplate = chesplate


            // -------------------------------------------- Leggings --------------------------------------------
            val leggings: ItemStack
            val lNumber = Utils.getRandomInt(c.getConfigurationSection("$armorKey.leggings")!!.getKeys(false).size)
            leggings = ItemStack(Material.valueOf(c.getString("$armorKey.leggings.$lNumber.item")!!))
            if (c.getString("$armorKey.leggings.$lNumber.enchants") != "none") {
                c.getString("$armorKey.leggings.$lNumber.enchants")!!.split(";").forEach{s ->
                    val enchant = s.split("-")
                    leggings.addEnchantment(Enchantment.getByName(enchant[0])!!, enchant[1].toInt())
                }
            }
            inv.leggings = leggings


            // -------------------------------------------- Boots --------------------------------------------
            val boots: ItemStack
            val bNumber = Utils.getRandomInt(c.getConfigurationSection("$armorKey.boots")!!.getKeys(false).size)
            boots = ItemStack(Material.valueOf(c.getString("$armorKey.boots.$bNumber.item")!!))
            if (c.getString("$armorKey.boots.$bNumber.enchants") != "none") {
                c.getString("$armorKey.boots.$bNumber.enchants")!!.split(";").forEach{s ->
                    val enchant = s.split("-")
                    boots.addEnchantment(Enchantment.getByName(enchant[0])!!, enchant[1].toInt())
                }
            }
            inv.boots = boots


            // -------------------------------------------- Swords --------------------------------------------
            val sword: ItemStack
            val swordNumber = Utils.getRandomInt(c.getConfigurationSection("$weaponKey.swords")!!.getKeys(false).size)
            sword = ItemStack(Material.valueOf(c.getString("$weaponKey.swords.$swordNumber.item")!!))
            if (c.getString("$weaponKey.swords.$swordNumber.enchants") != "none") {
                c.getString("$weaponKey.swords.$swordNumber.enchants")!!.split(";").forEach{s ->
                    val enchant = s.split("-")
                    sword.addEnchantment(Enchantment.getByName(enchant[0])!!, enchant[1].toInt())
                }
            }
            inv.addItem(sword)


            // -------------------------------------------- Axe --------------------------------------------
            val axe: ItemStack
            val axeNumber = Utils.getRandomInt(c.getConfigurationSection("$weaponKey.axe")!!.getKeys(false).size)
            axe = ItemStack(Material.valueOf(c.getString("$weaponKey.axe.$axeNumber.item")!!))
            if (c.getString("$weaponKey.axe.$axeNumber.enchants") != "none") {
                c.getString("$weaponKey.axe.$axeNumber.enchants")!!.split(";").forEach{s ->
                    val enchant = s.split("-")
                    axe.addEnchantment(Enchantment.getByName(enchant[0])!!, enchant[1].toInt())
                }
            }
            inv.addItem(axe)


            // -------------------------------------------- Bow --------------------------------------------
            val bowNumber = Utils.getRandomInt(c.getConfigurationSection("$weaponKey.bow")!!.getKeys(false).size - 2)
            val bow = ItemStack(Material.valueOf(c.getString("$weaponKey.bow.item")!!))
            if (c.getString("$weaponKey.bow.$bowNumber.enchants") != "none") {
                c.getString("$weaponKey.bow.$bowNumber.enchants")!!.split(";").forEach{s ->
                    val enchant = s.split("-")
                    bow.addEnchantment(Enchantment.getByName(enchant[0])!!, enchant[1].toInt())
                }
            }
            inv.addItem(bow)
            inv.addItem(ItemStack(Material.ARROW, Utils.getRandomInt(c.getInt("$weaponKey.bow.arrows.min"),
            c.getInt("$weaponKey.bow.arrows.max"))))

            // Healing
            inv.addItem(ItemStack(Material.GOLDEN_APPLE, Utils.getRandomInt(c.getInt("kits.healing.golden-apples.min"),
            c.getInt("kits.healing.golden-apples.max"))),
            ItemBuilder(Utils.goldenHead()).amount(Utils.getRandomInt(c.getInt("kits.healing.golden-heads.min"),
            c.getInt("kits.healing.golden-heads.max"))).build())

            // Cobwebs
            if (Utils.getRandomInt(100) - c.getInt("kits.cobwebs.chance") <= 1) {
                inv.addItem(ItemStack(Material.COBWEB, Utils.getRandomInt(c.getInt("kits.cobwebs.min"), c.getInt("kits.cobwebs.max"))))
            }

            // Fire Res
            if (Utils.getRandomInt(100) - c.getInt("kits.potions.fire-resistance-chance") <= 1) {
                val potion = ItemStack(Material.POTION)
                val meta = potion.itemMeta as PotionMeta
                meta.basePotionData = PotionData(PotionType.FIRE_RESISTANCE)
                potion.setItemMeta(meta)
                inv.addItem(potion)
            }

            // Speed
            if (Utils.getRandomInt(100) - c.getInt("kits.potions.speed-chance") <= 1) {
                val potion = ItemStack(Material.POTION)
                val meta = potion.itemMeta as PotionMeta
                meta.basePotionData = PotionData(PotionType.SPEED)
                potion.setItemMeta(meta)
                inv.addItem(potion)
            }

            if (Utils.getRandomInt(100) - c.getInt("kits.potions.poison-chance") <= 1) {
                val potion = ItemStack(Material.SPLASH_POTION)
                val meta = potion.itemMeta as PotionMeta
                meta.basePotionData = PotionData(PotionType.POISON)
                potion.setItemMeta(meta)
                inv.addItem(potion)
            }
            val miscellaneous: ItemStack
            val miscNumber = Utils.getRandomInt(c.getConfigurationSection("kits.miscellaneous")!!.getKeys(false).size)
            if (miscNumber > 0) {
                miscellaneous = ItemStack(Material.valueOf(c.getString("kits.miscellaneous.$miscNumber.item")!!))
                if (c.getString("kits.miscellaneous.$miscNumber.enchants") != "none") {
                    c.getString("kits.miscellaneous.$miscNumber.enchants")!!.split(";").forEach{s ->
                        val enchant = s.split("-")
                        miscellaneous.addEnchantment(Enchantment.getByName(enchant[0])!!, enchant[1].toInt())
                    }
                }
                inv.addItem(miscellaneous)
            }

            inv.addItem(ItemStack(Material.LAVA_BUCKET))
            inv.addItem(ItemStack(Material.WATER_BUCKET))
            inv.addItem(ItemStack(Material.COBBLESTONE,64))
            inv.addItem(ItemStack(Material.COOKED_BEEF,32))
            inv.addItem(ItemStack(Material.OAK_PLANKS,64))
            inv.addItem(ItemStack(Material.ANVIL))
            inv.addItem(ItemStack(Material.DIAMOND_PICKAXE))
            inv.addItem(ItemStack(Material.LAPIS_LAZULI,64))
            inv.addItem(ItemStack(Material.CRAFTING_TABLE))
            inv.addItem(ItemStack(Material.ENCHANTING_TABLE))
            inv.addItem(ItemStack(Material.EXPERIENCE_BOTTLE, c.getInt("kits.xpbottles")))
            if (c.getInt("kits.book") > 0) inv.addItem(ItemStack(Material.BOOK, c.getInt("kits.book")))
            if (ScenarioManager.getInstance().getScenario("HeavyPockets")!!.isEnabled()) inv.addItem(ItemStack(Material.SMITHING_TABLE))
        }
    }
}