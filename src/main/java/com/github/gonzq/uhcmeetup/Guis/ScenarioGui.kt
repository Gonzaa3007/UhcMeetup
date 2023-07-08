package com.github.gonzq.uhcmeetup.Guis

import com.github.gonzq.uhcmeetup.Enums.GameState
import com.github.gonzq.uhcmeetup.Managers.GameManager
import com.github.gonzq.uhcmeetup.Managers.ScenarioManager
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import fr.mrmicky.fastinv.FastInv
import fr.mrmicky.fastinv.ItemBuilder
import org.bukkit.entity.Player

class ScenarioGui: FastInv(18, "Scenarios") {
    private val pl = UhcMeetup.pl

    init {
        ScenarioManager.getInstance().getScenarios().forEach { s ->
            addItem(
                ItemBuilder(s.getIcon())
                    .name(Utils.chat(
                            pl.config.getConfig().getString("scenarios-gui.scenarios-name")!!.replace("%name%", s.getName()))
                    ).lore(s.getDescription()).build()) { e ->
                val p = e.whoClicked as Player
                e.isCancelled = true
                if (p.hasPermission("meetup.scenarios") && GameManager.getInstance().getState() != GameState.STARTED &&
                        !GameManager.getInstance().getVoteScenarios().canVote()) {
                    if (s.isEnabled()) {
                        s.disable()
                        if (pl.config.getConfig().getStringList("vote-system.scenarios-to-vote").contains(s.getName())) {
                            GameManager.getInstance().getVoteScenarios().scensToVote[s] = 0
                        }
                    } else {
                        s.enable()
                        if (pl.config.getConfig().getStringList("vote-system.scenarios-to-vote").contains(s.getName())) {
                            GameManager.getInstance().getVoteScenarios().scensToVote.remove(s)
                        }
                    }
                }
            }
        }
    }
}