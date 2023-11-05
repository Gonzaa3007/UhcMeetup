package com.github.gonzq.uhcmeetup.Listeners

import com.github.gonzq.uhcmeetup.Enums.GameState
import com.github.gonzq.uhcmeetup.Enums.PlayerState
import com.github.gonzq.uhcmeetup.Managers.GameManager
import com.github.gonzq.uhcmeetup.Managers.ScatterManager
import com.github.gonzq.uhcmeetup.Managers.ScenarioManager
import com.github.gonzq.uhcmeetup.Managers.WorldManager
import com.github.gonzq.uhcmeetup.Players.GamePlayer
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import fr.mrmicky.fastboard.FastBoard
import fr.mrmicky.fastinv.ItemBuilder
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.player.PlayerBucketEmptyEvent
import org.bukkit.event.player.PlayerGameModeChangeEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerRespawnEvent
import java.util.Objects

class HubListener: Listener {
    private val pl = UhcMeetup.pl
    private val game = GameManager.getInstance()
    private val scatter = ScatterManager.getInstance()
    private val world = WorldManager.getInstance()
    private val scen = ScenarioManager.getInstance()

    init {
        pl.server.pluginManager.registerEvents(this,pl)
    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val p = pl.pm.playerJoinsTheGame(e.player)
        e.joinMessage = Utils.chat(pl.lang.getConfig().getString("player-join")!!.replace("%player%", p.name))
        pl.boards[p.name] = FastBoard(p.getPlayer()!!)
        p.getPlayer()!!.maxHealth = 20.0
        when (game.getState()) {
            GameState.STARTING -> {
                Utils.delay(1) {
                    p.getPlayer()!!.level = 0
                    p.getPlayer()!!.exp = 0f
                    p.getPlayer()!!.maxHealth = 20.0
                    p.getPlayer()!!.foodLevel = 20
                    p.getPlayer()!!.inventory.clear()
                    p.getPlayer()!!.absorptionAmount = 0.0
                    p.getPlayer()!!.activePotionEffects.forEach{ep -> p.getPlayer()!!.removePotionEffect(ep.type)}
                    scatter.scatter(p)
                }
            }
            GameState.WAITING -> {
                Utils.delay(1) {
                    p.getPlayer()!!.level = 0
                    p.getPlayer()!!.exp = 0f
                    p.getPlayer()!!.maxHealth = 20.0
                    p.getPlayer()!!.foodLevel = 20
                    p.getPlayer()!!.inventory.clear()
                    p.getPlayer()!!.gameMode = GameMode.SURVIVAL
                    p.getPlayer()!!.bedSpawnLocation = world.getLobbyLocation()
                    p.getPlayer()!!.teleport(world.getLobbyLocation())
                    p.getPlayer()!!.activePotionEffects.forEach {ep -> p.getPlayer()!!.removePotionEffect(ep.type)}
                    lobbyItems(p.getPlayer()!!)
                }
                if (pl.pm.getPlayerList().stream().filter(GamePlayer::isOnline).toList().size >= pl.config.getConfig().getInt("players-to-start"))
                    game.start()
            }

            GameState.FINALIZED, GameState.STARTED -> {
                Utils.delay(1) {
                    p.getPlayer()!!.level = 0
                    p.getPlayer()!!.exp = 0f
                    p.getPlayer()!!.foodLevel = 20
                    p.getPlayer()!!.inventory.clear()
                    p.getPlayer()!!.bedSpawnLocation = world.getLobbyLocation()
                    p.getPlayer()!!.teleport(Location(world.getMeetupWorld(), 0.0, 100.0, 0.0))
                    p.getPlayer()!!.gameMode = GameMode.SPECTATOR
                    p.setState(PlayerState.SPECTATING)
                    p.getPlayer()!!.activePotionEffects.forEach{ep -> p.getPlayer()!!.removePotionEffect(ep.type)}
                }
            }
        }
    }

    @EventHandler
    fun onRespawn(e: PlayerRespawnEvent) {
        val p = pl.pm.getUhcPlayer(e.player)
        Utils.delay(5) {
            p.getPlayer()!!.level = 0
            p.getPlayer()!!.exp = 0f
            p.getPlayer()!!.foodLevel = 20
            p.getPlayer()!!.inventory.clear()
            p.getPlayer()!!.bedSpawnLocation = world.getLobbyLocation()
            p.getPlayer()!!.teleport(Location(world.getMeetupWorld(), 0.0, 100.0, 0.0))
            p.getPlayer()!!.gameMode = GameMode.SPECTATOR
            p.setState(PlayerState.SPECTATING)
            p.getPlayer()!!.activePotionEffects.forEach{ep -> p.getPlayer()!!.removePotionEffect(ep.type)}
        }
    }

    @EventHandler
    fun onQuit(e: PlayerQuitEvent) {
        val player = e.player
        val p = pl.pm.getUhcPlayer(player)
        var board = pl.boards.remove(p.name)
        player.eject()
        if (board != null) board.delete()
        e.quitMessage = Utils.chat(pl.lang.getConfig().getString("player-quit")!!.replace("%player%", p.name))
        if (game.getState() == GameState.STARTED) {
            if (p.isPlaying()) {
                p.setState(PlayerState.SPECTATING)
                player.inventory.contents.filter(Objects::nonNull).filter{a -> a.type != Material.AIR}
                    .forEach{a -> player.world.dropItemNaturally(player.location, a)}
                player.inventory.armorContents.filter(Objects::nonNull).filter {a -> a.type != Material.AIR}
                    .forEach{a -> player.world.dropItemNaturally(player.location, a)}
                val offHand = player.inventory.itemInOffHand
                if (offHand.type != Material.AIR) player.world.dropItemNaturally(player.location, offHand)
            }
            pl.pm.checkIfRemainingPlayers()
        }
    }

    @EventHandler
    fun onGamemodeChange(e: PlayerGameModeChangeEvent) {
        val p = pl.pm.getUhcPlayer(e.player)
        if (game.getState() == GameState.STARTED && p.isPlaying() && e.newGameMode == GameMode.SPECTATOR) p.setState(PlayerState.SPECTATING)
    }

    @EventHandler
    fun onBreak(e: BlockBreakEvent) {
        when (game.getState()) {
            GameState.WAITING, GameState.STARTING, GameState.FINALIZED -> {
                if (!e.player.hasPermission("meetup.admin")) e.isCancelled = true
            }
            GameState.STARTED -> {
                if (e.player.world == world.getLobby() && !e.player.hasPermission("meetup.admin")) e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onPlace(e: BlockPlaceEvent) {
        when (game.getState()) {
            GameState.WAITING, GameState.STARTING -> {
                if (!e.player.hasPermission("meetup.admin")) e.isCancelled = true
            }

            GameState.STARTED -> {
                if (e.player.world == world.getLobby() && !e.player.hasPermission("meetup.admin")) e.isCancelled = true
            }

            else -> {}
        }
    }

    @EventHandler
    fun onBucket(e: PlayerBucketEmptyEvent) {
        when (game.getState()) {
            GameState.WAITING, GameState.STARTING -> {
                if (!e.player.hasPermission("meetup.admin")) e.isCancelled = true
            }
            GameState.STARTED -> {
                if (e.player.world == world.getLobby() && !e.player.hasPermission("meetup.admin")) e.isCancelled = true
            } else -> {}
        }
    }

    @EventHandler
    fun onHunger(e: FoodLevelChangeEvent) {
        when (game.getState()) {
            GameState.WAITING, GameState.STARTING, GameState.FINALIZED -> {
                e.foodLevel = 20
                e.isCancelled = true
            } else -> {}
        }
    }

    @EventHandler
    fun onDamage(e: EntityDamageEvent) {
        when (game.getState()) {
            GameState.STARTING, GameState.WAITING, GameState.FINALIZED -> e.isCancelled = true
            GameState.STARTED -> if (e.entity.world == world.getLobby()) e.isCancelled = true
        }
    }

    private fun lobbyItems(p: Player) {
        if (p.hasPermission("meetup.admin")) p.inventory.setItem(8, ItemBuilder(Material.ENCHANTED_BOOK)
            .name(Utils.chat("&cEdit Scenarios")).build())

        p.inventory.addItem(ItemBuilder(Material.BOOK).name(Utils.chat("&aEnabled Scenarios")).build())
        p.inventory.setItem(4, ItemBuilder(Material.DIAMOND).name(Utils.chat("&bStats")).build())
    }
}