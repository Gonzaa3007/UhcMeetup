package com.github.gonzq.uhcmeetup.Commands

import com.github.gonzq.uhcmeetup.Guis.VoteGui
import com.github.gonzq.uhcmeetup.Listeners.ConsumeListener
import com.github.gonzq.uhcmeetup.Managers.GameManager
import com.github.gonzq.uhcmeetup.Managers.VoteManager
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class VoteCMD: CommandExecutor {
    private val pl = UhcMeetup.pl
    private var vote: VoteManager

    init {
        pl.getCommand("vote")!!.setExecutor(this)
        vote = GameManager.getInstance().getVoteScenarios()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender is Player && sender.hasPermission("meetup.vote") &&
                pl.config.getConfig().getBoolean("vote-system.scenario-system")) {
            if (vote.canVote()) VoteGui().open(sender)
            else sender.sendMessage(Utils.chat("&cVoting is not available now"))
        }
        return false
    }
}