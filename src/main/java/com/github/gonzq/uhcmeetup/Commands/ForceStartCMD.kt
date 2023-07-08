package com.github.gonzq.uhcmeetup.Commands

import com.github.gonzq.uhcmeetup.Managers.GameManager
import com.github.gonzq.uhcmeetup.UhcMeetup
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class ForceStartCMD : CommandExecutor {
    private var pl: UhcMeetup = UhcMeetup.pl;
    init {
        pl.getCommand("forcestart")?.setExecutor(this)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender.hasPermission("meetup.forcestart")) GameManager.getInstance().start()
        return false
    }
}