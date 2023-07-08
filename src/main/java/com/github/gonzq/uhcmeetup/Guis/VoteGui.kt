package com.github.gonzq.uhcmeetup.Guis

import com.github.gonzq.uhcmeetup.Managers.GameManager
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import fr.mrmicky.fastinv.FastInv
import fr.mrmicky.fastinv.ItemBuilder
import org.bukkit.entity.Player

class VoteGui: FastInv(18, Utils.chat(UhcMeetup.pl.lang.getConfig().getString("vote-gui-title"))) {
    private val pl = UhcMeetup.pl
    private val game = GameManager.getInstance()

    init {
        game.getVoteScenarios().scensToVote.keys.forEach{scen ->
            addItem(ItemBuilder(scen.getIcon()).name(Utils.chat("&9${scen.getName()}"))
                .lore(Utils.chat("&7Votes: &b${game.getVoteScenarios().getVotes(scen)}")).build()) {e ->
                e.isCancelled = true
                val p = pl.pm.getUhcPlayer(e.whoClicked as Player)
                game.getVoteScenarios().setVote(p, scen)
                p.getPlayer().openInventory.close()
            }
        }

        addOpenHandler{e -> e.player.sendMessage(Utils.chat("${pl.prefix}Opening Vote Menu..."))}
    }
}