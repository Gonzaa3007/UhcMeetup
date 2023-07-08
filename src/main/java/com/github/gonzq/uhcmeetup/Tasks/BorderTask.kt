package com.github.gonzq.uhcmeetup.Tasks

import com.github.gonzq.uhcmeetup.Managers.GameManager
import com.github.gonzq.uhcmeetup.Managers.WorldManager
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class BorderTask: BukkitRunnable() {
    private var pl = UhcMeetup.pl
    private var time = 60
    private var wtime = pl.config.getConfig().getInt("worldborder-time")
    private  var game = GameManager.getInstance()


    override fun run() {
        if (time == 60) Bukkit.broadcastMessage(Utils.chat(pl.borderPrefix + pl.lang.getConfig().getString("border-shrink-one-minute")))
        if (time == 0) {
            Bukkit.broadcastMessage(Utils.chat(pl.borderPrefix + pl.lang.getConfig().getString("border-shrink")))
            WorldManager.getInstance().getMeetupWorld()!!.worldBorder.setSize(200.0, wtime.toLong())
            BorderActionTask().runTaskTimer(pl,0,20)
            cancel()
        }
        game.setTimeElapsed(game.getTimeElapsed() + 1)
        time--
    }
}