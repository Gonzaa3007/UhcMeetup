package com.github.gonzq.uhcmeetup.Managers

import com.github.gonzq.uhcmeetup.UhcMeetup
import org.bukkit.*
import org.bukkit.configuration.file.FileConfiguration
import java.io.File
import java.util.Arrays
import java.util.Random

class WorldManager {
    private val pl = UhcMeetup.pl

    companion object{
       private val instance = WorldManager()
        fun getInstance(): WorldManager {
            return instance
        }
    }

    fun getMeetupWorld(): World? {
        return Bukkit.getWorld(pl.config.getConfig().getString("meetup-world")!!)
    }
    fun getLobby(): World? {
        return Bukkit.getWorld(pl.config.getConfig().getString("lobby.name")!!)
    }

    fun getLobbyLocation(): Location {
        val c = pl.config.getConfig();
        return Location(getLobby(), c.getInt("lobby.location.x",0).toDouble(), c.getInt("lobby.location.y", 50).toDouble(),
        c.getInt("lobby.location.z",0).toDouble(), c.getInt("lobby.location.yaw",0).toFloat(),
        c.getInt("lobby.location.pitch").toFloat());
    }

    fun setLobbyLocation(l: Location) {
        pl.config.getConfig().set("lobby.location.x", l.blockX);
        pl.config.getConfig().set("lobby.location.y", l.blockY);
        pl.config.getConfig().set("lobby.location.z", l.blockZ);
        pl.config.getConfig().set("lobby.location.yaw", l.yaw);
        pl.config.getConfig().set("lobby.location.pitch", l.pitch);
        pl.config.saveConfig()
    }

    fun getBorder(): Int {
        return (getMeetupWorld()!!.worldBorder.size / 2).toInt()
    }

    fun createWorlds() {
        if (getLobby() == null) {
            val creator = WorldCreator(pl.config.getConfig().getString("lobby.name")!!)
            creator.type(WorldType.FLAT)
            creator.generateStructures(true)
            val w = creator.createWorld()!!
            w.difficulty = Difficulty.PEACEFUL
            w.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS,false)
            w.setGameRule(GameRule.DO_WEATHER_CYCLE,false)
        }
        val creator = WorldCreator(pl.config.getConfig().getString("meetup-world")!!)
        creator.type(WorldType.NORMAL)
        creator.generateStructures(false)
        creator.generatorSettings()
        val total = pl.config.getConfig().getLongList("seed-list").size
        creator.seed(pl.config.getConfig().getLongList("seed-list")[Random().nextInt(total)])
        val w = creator.createWorld()!!

        w.time = 9000
        w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE,false)
        w.setGameRule(GameRule.DO_MOB_SPAWNING,false)
        w.setGameRule(GameRule.DO_INSOMNIA,false)
        w.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS,false)
        w.setGameRule(GameRule.NATURAL_REGENERATION,false)
        w.worldBorder.size = pl.config.getConfig().getInt("worldborder-size").toDouble()
        w.worldBorder.damageBuffer = 0.0
    }

    private fun deleteFile(path: File): Boolean{
        if (path.exists()) {
            var files = path.listFiles()
            if (files != null) {
                Arrays.stream(files).forEach(this::deleteFile)
            }
        }
        return path.delete()
    }

    fun deleteWorld() {
        if (getMeetupWorld() != null) run {
            val path = File(Bukkit.getWorldContainer(), pl.config.getConfig().getString("meetup-world")!!)
            deleteFile(path)
        }
    }
}