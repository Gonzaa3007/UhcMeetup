package com.github.gonzq.uhcmeetup.Commands

import com.github.gonzq.uhcmeetup.Managers.WorldManager
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class MeetupCMD: CommandExecutor, TabExecutor {
    private val pl = UhcMeetup.pl

    init {
        pl.getCommand("uhcmeetup")!!.setExecutor(this)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (args!!.isEmpty()) {
            sender.sendMessage(Utils.chat("&aUHC Meetup"));
            sender.sendMessage(Utils.chat("&9Discord/Author: &b! Gonzq#4451"));
            if (sender.hasPermission("meetup.admin"))
                sender.sendMessage(Utils.chat("Usage: &9/uhcmeetup help"))
            return true;
        }
        if (sender.hasPermission("meetup.admin")) {
            when (args[0]) {
                "help" -> {
                    sender.sendMessage(Utils.chat("&8- &9/uhcmeetup setlobby &8| &7Sets the Lobby"));
                    sender.sendMessage(Utils.chat("&8- &9/uhcmeetup reload &8| &7Reload all configs"))
                }
                "setlobby" -> {
                    if (sender is Player) run {
                        WorldManager.getInstance().setLobbyLocation(sender.location)
                        sender.sendMessage(Utils.chat("${pl.prefix}&aLobby has been setted."))
                    }
                }

                "reload" -> {
                    pl.config.reloadConfig()
                    pl.board.reloadConfig()
                    pl.lang.reloadConfig()
                    pl.kit.reloadConfig()
                    sender.sendMessage(Utils.chat("&aAll configs has been reloaded"))
                }
            }
        }
        return false
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>?): MutableList<String>? {
        val list = mutableListOf<String>()
        if (args!!.size == 1 && sender.hasPermission("meetup.admin")) {
            list.add("setlobby")
            list.add("reload")
            return list
        }
        return null
    }
}
