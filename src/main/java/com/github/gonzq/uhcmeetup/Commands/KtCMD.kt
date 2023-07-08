package com.github.gonzq.uhcmeetup.Commands

import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class KtCMD: CommandExecutor {
    private val pl = UhcMeetup.pl

    init {
        pl.getCommand("kt")!!.setExecutor(this)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        val hash = mutableMapOf<String, Int>()
        pl.pm.getPlayerList().forEach { p -> hash[p.name] = p.getKills() }
        val entry = hash.entries.stream().sorted(java.util.Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .limit(10).toList()
        sender.sendMessage(Utils.chat("${pl.prefix}Top Kills:"))
        entry.forEach { e -> sender.sendMessage(Utils.chat(pl.lang.getConfig().getString("kt-msg"))
            .replace("%player%", e.key).replace("%kills%", "${e.value}")) }
        return false
    }
}