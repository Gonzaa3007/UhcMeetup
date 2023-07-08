package com.github.gonzq.uhcmeetup.Managers

import com.github.gonzq.uhcmeetup.Enums.GameState
import com.github.gonzq.uhcmeetup.Exceptions.GamePlayerDoesNotExistException
import com.github.gonzq.uhcmeetup.Players.GamePlayer
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*
import java.util.stream.Collectors

class PlayerManager {

    private var players: MutableList<GamePlayer> = mutableListOf()

    fun doesPlayerExist(p: Player): Boolean {
        try {
            getUhcPlayer(p.uniqueId)
            return true
        } catch (e: GamePlayerDoesNotExistException) {
            throw RuntimeException(e)
        }
    }

    fun getUhcPlayer(p: Player): GamePlayer {
        try {
            return getUhcPlayer(p.uniqueId)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Suppress("UNREACHABLE_CODE")
    @Throws(GamePlayerDoesNotExistException::class)
    fun getUhcPlayer(name: String): GamePlayer {
        return players.filter { p -> p.name == name }[0]
        throw GamePlayerDoesNotExistException(name)
    }

    @Suppress("UNREACHABLE_CODE")
    @Throws(GamePlayerDoesNotExistException::class)
    fun getUhcPlayer(uid: UUID): GamePlayer {
        return players.filter { p -> p.uid == uid }[0]
        throw GamePlayerDoesNotExistException(uid.toString())
    }

    fun getOrCreateGamePlayer(p: Player): GamePlayer {
        if (doesPlayerExist(p)) return getUhcPlayer(p)
        return newUhcPlayer(p)
    }

    @Synchronized
    fun newUhcPlayer(p: Player): GamePlayer {
        return newUhcPlayer(p.uniqueId, p.name)
    }

    @Synchronized
    fun newUhcPlayer(uid: UUID, name: String): GamePlayer {
        val newPlayer: GamePlayer = GamePlayer(uid,name)
        players.add(newPlayer)
        return newPlayer
    }

    @Synchronized
    fun getPlayerList(): MutableList<GamePlayer> {
        return players
    }

    fun getPlayingPlayers(): Set<GamePlayer> {
        return players.stream()
            .filter(GamePlayer::isPlaying)
            .collect(Collectors.toSet())
    }

    fun setAllPlayersEndGame() {
        var manager = GameManager.getInstance()
        val winner = getWinner()
        val pl = UhcMeetup.pl
        Bukkit.broadcastMessage(Utils.chat(pl.prefix + pl.lang.getConfig().getString("win-solo")).replace("%player%", winner.name).replace("%kills%", "${winner.getKills()}"))
        StatsManager.getInstance().add(winner, StatsManager.Stats.WINS)
        manager.setState(GameState.FINALIZED)
        pl.boards.values.forEach{f -> f.updateLines(mutableListOf())}
        Bukkit.broadcastMessage(Utils.chat(pl.prefix + pl.lang.getConfig().getString("server-restart")!!
            .replace("%time%", "${pl.config.getConfig().getInt("restart-countdown")}")))
        Utils.delay(pl.config.getConfig().getInt("restart-countdown") * 20L) {
            pl.server.spigot().restart()
        }
    }

    fun getWinner(): GamePlayer {
        return players.filter(GamePlayer::isPlaying)[0]
    }

    fun playerJoinsTheGame(p: Player): GamePlayer {
        if (doesPlayerExist(p)) return getUhcPlayer(p)
        return newUhcPlayer(p)
    }

    fun checkIfRemainingPlayers() {
        var playingPlayers = 0
        for (p in getPlayerList()) {
            if (p.isPlaying()) playingPlayers++
        }
        if (playingPlayers <= 1 ) setAllPlayersEndGame()
    }
}