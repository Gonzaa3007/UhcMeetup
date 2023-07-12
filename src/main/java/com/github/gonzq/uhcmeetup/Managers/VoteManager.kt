package com.github.gonzq.uhcmeetup.Managers

import com.github.gonzq.uhcmeetup.Players.GamePlayer
import com.github.gonzq.uhcmeetup.Scenarios.Scenario
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import org.bukkit.Bukkit
import kotlin.streams.toList

class VoteManager {
    val scensToVote = mutableMapOf<Scenario, Int>()
    private var canVote = false
    private val pl = UhcMeetup.pl
    private val scen = ScenarioManager.getInstance()

    init {
        pl.config.getConfig().getStringList("vote-system.scenarios-to-vote").forEach {s ->
            if (scen.getScenario(s) != null) {
                scensToVote[scen.getScenario(s)!!] = 0
            } else {
                pl.logger.warning("$s isn't a scenario. Check the config.yml")
            }
        }
    }

    fun setVote(p: GamePlayer, s: Scenario) {
        if (p.isVotedScen()) {
            if (p.isOnline()) {
                p.getPlayer().sendMessage(Utils.chat("${pl.prefix}&cYou already voted a Scenario."))
            }
        } else {
            scensToVote[s] = scensToVote.getOrPut(s){0} + 1
            p.setVoteScen(true)
            if (p.isOnline()) p.getPlayer().sendMessage(Utils.chat(pl.lang.getConfig().getString("vote-scen")!!.replace("%scenario%", s.getName())))
        }
    }

    fun canVote(): Boolean {
        return canVote
    }
    fun setCanVote(b: Boolean) {
        canVote = b
    }
    fun getVotes(s: Scenario): Int {
        return scensToVote.getOrPut(s){0}
    }

    fun endVotes() {
        val list = scensToVote.entries.stream().sorted(java.util.Map.Entry.comparingByValue(Comparator.reverseOrder())).toList()
        if (list[0].value > 0) {
            val s = list[0].key
            Bukkit.broadcastMessage(Utils.chat(pl.prefix + pl.lang.getConfig().getString("vote-end")!!
                .replace("%scenario%", s.getName()).replace("%votes%", "${list[0].value}")))
            s.enable()
        }
        setCanVote(false)
    }
}