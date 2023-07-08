package com.github.gonzq.uhcmeetup.Commands

import com.github.gonzq.uhcmeetup.Guis.ScenGui
import com.github.gonzq.uhcmeetup.UhcMeetup
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ScenarioCMD: CommandExecutor {
    private val pl = UhcMeetup.pl

    init {
        pl.getCommand("scenarios")!!.setExecutor(this)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender is Player) {
            ScenGui().open(sender)
        }
        return false
    }
}