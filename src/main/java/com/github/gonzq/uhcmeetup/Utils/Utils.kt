package com.github.gonzq.uhcmeetup.Utils

import com.github.gonzq.uhcmeetup.Commands.*
import com.github.gonzq.uhcmeetup.Listeners.*
import com.github.gonzq.uhcmeetup.Managers.ScenarioManager
import com.github.gonzq.uhcmeetup.UhcMeetup
import fr.mrmicky.fastinv.ItemBuilder
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.RenderType
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

open class Utils {

    companion object {
        fun chat(s: String?): String {
            var s = s
            val pattern: Pattern = Pattern.compile("#[a-fA-F0-9]{6}")
            s = s!!.replace("&#", "#")
            var match: Matcher = pattern.matcher(s)
            while (match.find()) {
                val color: String = s!!.substring(match.start(), match.end())
                s = s!!.replace(color, "${ChatColor.of(color)}")
                match = pattern.matcher(s)
            }
            return org.bukkit.ChatColor.translateAlternateColorCodes('&', s!!)
        }

        fun delay(ticks: Long, run: Runnable) {
            Bukkit.getScheduler().runTaskLater(UhcMeetup.pl, run, ticks)
        }

        fun getRandomInt(i: Int): Int {
            return Random().nextInt(i) + 1
        }

        fun getRandomInt(min: Int, max: Int): Int {
            return Random().nextInt(max - min + 1) + min
        }

        fun timeConvert(t: Int): String {
            var hours: Int = t / 3600
            var minutes: Int = (t % 3600) / 60
            var seconds: Int = t % 60
            return if (hours > 0) String.format("%02d:%02d:%02d", hours, minutes, seconds) else String.format(
                "%02d:%02d",
                minutes,
                seconds
            )
        }

        fun goldenHead(): ItemStack {
            return ItemBuilder(Material.GOLDEN_APPLE).name(chat("&6&lGolden Head")).build()
        }

        fun setObjectives() {
            var board = Bukkit.getScoreboardManager()!!.mainScoreboard
            var tab = board.getObjective("health_tab")
            var name = board.getObjective("health_name")
            if (UhcMeetup.pl.board.getConfig().getBoolean("scoreboard.tab-health")) {
                if (tab == null) {
                    tab = board.registerNewObjective("health_tab", "health", ".", RenderType.HEARTS)
                    tab.displaySlot = DisplaySlot.PLAYER_LIST
                }
            }

            if (UhcMeetup.pl.board.getConfig().getBoolean("scoreboard.name-health")) {
                if (name == null) {
                    name = board.registerNewObjective("health_name", "name")
                    name.displaySlot = DisplaySlot.BELOW_NAME
                    name.displayName = chat("&c❤")
                }
            }
        }

        fun registerAll() {
            val scen = ScenarioManager.getInstance()
            scen.setup()
            UhcMeetup.pl.config.getConfig().getStringList("default-scenarios-enabled").forEach { s ->
                scen.getScenario(s)?.enable()
            }

            // Listeners
            ConsumeListener()
            DeathListener()
            FightsListener()
            HubListener()
            InteractListener()

            // Commands
            MeetupCMD()
            KtCMD()
            ScenarioCMD()
            StatsCMD()
            VoteCMD()
            ForceStartCMD()
        }
    }
}