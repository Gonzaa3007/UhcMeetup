package com.github.gonzq.uhcmeetup.Tasks

import com.github.gonzq.uhcmeetup.Enums.GameState
import com.github.gonzq.uhcmeetup.Managers.GameManager
import com.github.gonzq.uhcmeetup.Managers.ScatterManager
import com.github.gonzq.uhcmeetup.Managers.ScenarioManager
import com.github.gonzq.uhcmeetup.Players.GamePlayer
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

class CountdownTask: BukkitRunnable() {
    private var pl = UhcMeetup.pl
    private var msg = pl.lang.getConfig().getString("countdown-msg")!!
    private var votemsg = pl.lang.getConfig().getString("vote-msg")!!
    private var vote = pl.lang.getConfig().getBoolean("vote-system.scenario-system")
    private var g = GameManager.getInstance()

    override fun run() {
        if (g.getCountdown() == 60) {
            Bukkit.broadcastMessage(Utils.chat(pl.prefix + msg.replace("%time%", "60")))
            if (vote) pl.pm.getPlayerList().stream().filter(GamePlayer::isOnline).filter { p -> !p.isVotedScen()}
                .filter {p -> p.getPlayer().hasPermission("meetup.vote")}.forEach {p ->
                    p.getPlayer().sendMessage(Utils.chat(pl.prefix + votemsg))}

            if (g.getCountdown() == 30) {
                Bukkit.broadcastMessage(Utils.chat(pl.prefix + msg.replace("%time%", "60")))
                if (vote) pl.pm.getPlayerList().stream().filter(GamePlayer::isOnline).filter { p -> !p.isVotedScen()}
                    .filter {p -> p.getPlayer().hasPermission("meetup.vote")}.forEach {p ->
                        p.getPlayer().sendMessage(Utils.chat(pl.prefix + votemsg))}
            }
            if (g.getCountdown() == 10) {
                Bukkit.broadcastMessage(Utils.chat(pl.prefix + msg.replace("%time%", "60")))
                if (vote) pl.pm.getPlayerList().stream().filter(GamePlayer::isOnline).filter { p -> !p.isVotedScen()}
                    .filter {p -> p.getPlayer().hasPermission("meetup.vote")}.forEach {p ->
                        p.getPlayer().sendMessage(Utils.chat(pl.prefix + votemsg))}
            }
            if (g.getCountdown() == 5) {
                Bukkit.broadcastMessage(Utils.chat(pl.prefix + msg.replace("%time%", "60")))
                if (vote) pl.pm.getPlayerList().stream().filter(GamePlayer::isOnline).filter { p -> !p.isVotedScen()}
                    .filter {p -> p.getPlayer().hasPermission("meetup.vote")}.forEach {p ->
                        p.getPlayer().sendMessage(Utils.chat(pl.prefix + votemsg))}
            }
            if (g.getCountdown() == 4) {
                Bukkit.broadcastMessage(Utils.chat(pl.prefix + msg.replace("%time%", "60")))
                if (vote) pl.pm.getPlayerList().stream().filter(GamePlayer::isOnline).filter { p -> !p.isVotedScen()}
                    .filter {p -> p.getPlayer().hasPermission("meetup.vote")}.forEach {p ->
                        p.getPlayer().sendMessage(Utils.chat(pl.prefix + votemsg))}
            }
            if (g.getCountdown() == 3) {
                Bukkit.broadcastMessage(Utils.chat(pl.prefix + msg.replace("%time%", "60")))
                if (vote) pl.pm.getPlayerList().stream().filter(GamePlayer::isOnline).filter { p -> !p.isVotedScen()}
                    .filter {p -> p.getPlayer().hasPermission("meetup.vote")}.forEach {p ->
                        p.getPlayer().sendMessage(Utils.chat(pl.prefix + votemsg))}
            }
            if (g.getCountdown() == 2) {
                Bukkit.broadcastMessage(Utils.chat(pl.prefix + msg.replace("%time%", "60")))
                if (vote) pl.pm.getPlayerList().stream().filter(GamePlayer::isOnline).filter { p -> !p.isVotedScen()}
                    .filter {p -> p.getPlayer().hasPermission("meetup.vote")}.forEach {p ->
                        p.getPlayer().sendMessage(Utils.chat(pl.prefix + votemsg))}
            }
            if (g.getCountdown() == 1) {
                Bukkit.broadcastMessage(Utils.chat(pl.prefix + msg.replace("%time%", "60")))
                if (vote) pl.pm.getPlayerList().stream().filter(GamePlayer::isOnline).filter { p -> !p.isVotedScen()}
                    .filter {p -> p.getPlayer().hasPermission("meetup.vote")}.forEach {p ->
                        p.getPlayer().sendMessage(Utils.chat(pl.prefix + votemsg))}
            }

            if (g.getCountdown() == 0) {
                pl.pm.getPlayerList().stream().filter(GamePlayer::isOnline).forEach(ScatterManager.getInstance()::postScatter)
                if (vote) g.getVoteScenarios().endVotes()
                if (ScenarioManager.getInstance().getScenario("HeavyPockets")!!.isEnabled())
                    pl.pm.getPlayerList().stream().filter(GamePlayer::isOnline).forEach {p -> p.getPlayer().inventory
                        .addItem(ItemStack(Material.SMITHING_TABLE))}
                g.setState(GameState.STARTED)
                BorderTask().runTaskTimer(pl,0,20)
                cancel()
            }
            g.restCountdown()
        }
    }

}