package com.github.gonzq.uhcmeetup.Guis

import com.github.gonzq.uhcmeetup.Managers.ScenarioManager
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import fr.mrmicky.fastinv.FastInv
import fr.mrmicky.fastinv.ItemBuilder

class ScenGui: FastInv(18, "Scenarios") {

    init {
        ScenarioManager.getInstance().getEnabledScenarios().forEach {s ->
            addItem(ItemBuilder(s.getIcon()).name(Utils.chat(UhcMeetup.pl.config.getConfig().getString("scenarios-gui.scenarios-name")!!
                .replace("%name%", s.getName()))).lore(s.getDescription()).build())
        }
    }
}