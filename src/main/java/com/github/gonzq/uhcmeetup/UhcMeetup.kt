package com.github.gonzq.uhcmeetup

import com.github.gonzq.uhcmeetup.File.CFiles
import com.github.gonzq.uhcmeetup.Managers.PlayerManager
import com.github.gonzq.uhcmeetup.Managers.WorldManager
import com.github.gonzq.uhcmeetup.Tasks.ScoreboardTask
import com.github.gonzq.uhcmeetup.Utils.Placeholders.Placeholders
import com.github.gonzq.uhcmeetup.Utils.Utils
import com.jeff_media.updatechecker.UpdateCheckSource
import com.jeff_media.updatechecker.UpdateChecker
import com.jeff_media.updatechecker.UserAgentBuilder
import fr.mrmicky.fastboard.FastBoard
import fr.mrmicky.fastinv.FastInvManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.Objective

class UhcMeetup : JavaPlugin() {
    companion object {
        lateinit var pl: UhcMeetup
    }
    lateinit var config: CFiles
    lateinit var board: CFiles
    lateinit var kit: CFiles
    lateinit var lang: CFiles

    lateinit var pm: PlayerManager

    lateinit var prefix: String
    lateinit var borderPrefix: String

    var boards = mutableMapOf<String, FastBoard>()

    var papi = false

    override fun onEnable() {
        // Plugin startup logic
        pl = this
        config = CFiles(this, "config.yml")
        board = CFiles(this, "scoreboard.yml")
        kit = CFiles(this, "kit.yml")
        lang = CFiles(this, "lang.yml")

        prefix = Utils.chat(lang.getConfig().getString("prefix"))
        borderPrefix = Utils.chat(lang.getConfig().getString("border-prefix"))
        pm = PlayerManager()
        FastInvManager.register(this)
        WorldManager.getInstance().deleteWorld()
        WorldManager.getInstance().createWorlds()
        UpdateChecker(this, UpdateCheckSource.SPIGOT, "107429")
            .setDownloadLink(107429)
            .setDonationLink("https://paypal.me/gonza3007")
            .setChangelogLink(107429)
            .setNotifyOpsOnJoin(true)
            .setUserAgent(UserAgentBuilder().addPluginNameAndVersion())
            .checkEveryXHours(0.5).checkNow()

        ScoreboardTask().runTaskTimer(this,0,20)

        if (server.pluginManager.getPlugin("PlaceholderAPI") == null) {
            papi = false
        } else {
            papi = true
            Placeholders().register()
        }
        Utils.setObjectives()
        Utils.registerAll()
    }

    override fun onDisable() {
        // Plugin shutdown logic
        Bukkit.getScoreboardManager()!!.mainScoreboard.objectives.forEach(Objective::unregister)
        WorldManager.getInstance().deleteWorld()
    }
}