package com.github.gonzq.uhcmeetup.Commands

import com.github.gonzq.uhcmeetup.Guis.StatsGui
import com.github.gonzq.uhcmeetup.Guis.TopStatsGui
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class StatsCMD: CommandExecutor {
    private val pl = UhcMeetup.pl

    init {
        pl.getCommand("topstats")!!.setExecutor(this)
        pl.getCommand("stats")!!.setExecutor(this)
    }

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>?): Boolean {
        if (sender is Player) {
            when (cmd.name) {
                "topstats" -> TopStatsGui(sender).open(sender)
                "stats" -> {
                    if (args!!.isEmpty()) StatsGui(sender).open(sender)
                    else {
                        val p = Bukkit.getPlayer(args[0])
                        if (p == null) {
                            sender.sendMessage(Utils.chat("${pl.prefix}&cThis player isn't online"))
                            return true
                        }
                        StatsGui(p).open(sender)
                    }
                }
            }
        }
        return false
    }
}