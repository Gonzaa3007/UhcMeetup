package com.github.gonzq.uhcmeetup.Tasks

import com.github.gonzq.uhcmeetup.Enums.GameState
import com.github.gonzq.uhcmeetup.Managers.GameManager
import com.github.gonzq.uhcmeetup.Managers.WorldManager
import com.github.gonzq.uhcmeetup.Players.GamePlayer
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.scheduler.BukkitRunnable

class BorderActionTask: BukkitRunnable() {
    private var pl = UhcMeetup.pl
    private var msg = pl.lang.getConfig().getString("border-actionbar")!!
    private var game = GameManager.getInstance()


    override fun run() {
        game.setTimeElapsed(game.getTimeElapsed() + 1)
        pl.pm.getPlayerList().stream().filter(GamePlayer::isOnline).forEach { p ->
            p.getPlayer()!!.spigot().sendMessage(ChatMessageType.ACTION_BAR,
            TextComponent(Utils.chat(msg.replace("%prefix%", pl.borderPrefix)
                .replace("%size%", "${WorldManager.getInstance().getBorder()}"))))
            if (game.getState() == GameState.FINALIZED) cancel()
        }
    }
}