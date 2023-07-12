package com.github.gonzq.uhcmeetup.Managers

import com.github.gonzq.uhcmeetup.Enums.GameState
import com.github.gonzq.uhcmeetup.Players.GamePlayer
import com.github.gonzq.uhcmeetup.Tasks.CountdownTask
import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils

open class GameManager {
    private var pl: UhcMeetup = UhcMeetup.pl
    private var voteScenarios: VoteManager? = null


    companion object {
        private val instance = GameManager()
        fun getInstance(): GameManager {
            return instance
        }
    }

    fun getVoteScenarios(): VoteManager {
        if (voteScenarios == null) voteScenarios = VoteManager()
        return voteScenarios!!
    }

    private var countdown: Int = pl.config.getConfig().getInt("countdown-time")
    fun getCountdown(): Int {
        return countdown
    }
    fun restCountdown() {
        countdown--
    }

    private var state: GameState = GameState.WAITING
    fun setState(state: GameState) {
        this.state = state
    }
    fun getState(): GameState {
        return state
    }
    private var elapsed: Int = 0
    fun setTimeElapsed(i: Int) {
        elapsed = i
    }
    fun getTimeElapsed(): Int {
        return elapsed
    }

    fun start() {
        pl.pm.getPlayerList().stream().filter(GamePlayer::isOnline).forEach{p ->
            Utils.delay(5) {
                ScatterManager.getInstance().scatter(p)
                p.getPlayer().isInvulnerable = true
            }
        }

        setState(GameState.STARTING)
        CountdownTask().runTaskTimer(pl,0,20)
        if (pl.config.getConfig().getBoolean("vote-system.scenario-system")) {
            getVoteScenarios().setCanVote(true)
        }
    }
}