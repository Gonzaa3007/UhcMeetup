package com.github.gonzq.uhcmeetup.Managers

import com.github.gonzq.uhcmeetup.Scenarios.Scenario
import com.github.gonzq.uhcmeetup.Scenarios.Types.*
import com.github.gonzq.uhcmeetup.UhcMeetup

class ScenarioManager {
    private val scenarios = mutableSetOf<Scenario>()

    companion object {
        private val manager = ScenarioManager()
        fun getInstance(): ScenarioManager {
            return manager
        }
    }

    fun setup() {
        val pl = UhcMeetup.pl
        scenarios.add(AbsortionLess())
        scenarios.add(BloodHunter())
        scenarios.add(BowLess())
        scenarios.add(CobwebLess())
        scenarios.add(FastGetaway())
        scenarios.add(GappleRoulette())
        scenarios.add(GoldenRetriever())
        scenarios.add(GraveRobbers())
        scenarios.add(HeavyPockets())
        scenarios.add(KillEffect())
        scenarios.add(NoClean(pl, pl.config.getConfig().getInt("noclean-time")))
        scenarios.add(TimeBomb())
        scenarios.add(WebCage())
        GameManager.getInstance().getVoteScenarios()
    }

    fun getScenario(name: String): Scenario? {
        for (scen in scenarios) {
            if (scen.getName() == name) return scen
        }
        return null
    }

    fun getScenarios(): MutableList<Scenario> {
        return scenarios.toMutableList()
    }

    fun getEnabledScenarios(): MutableList<Scenario> {
        return scenarios.stream().filter(Scenario::isEnabled).toList().toMutableList()
    }
    fun getDisabledScenarios(): MutableList<Scenario> {
        return scenarios.stream().filter {scen -> !scen.isEnabled()}.toList().toMutableList()
    }
}