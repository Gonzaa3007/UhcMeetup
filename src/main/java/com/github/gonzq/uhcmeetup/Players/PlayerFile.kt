package com.github.gonzq.uhcmeetup.Players

import com.github.gonzq.uhcmeetup.UhcMeetup
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.UUID

open class PlayerFile(uid: UUID) {
    private  var config: FileConfiguration
    private var file: File

    init {
        val pl: UhcMeetup = UhcMeetup.pl
        val folder = File("" + pl.dataFolder + File.separator + "players" + File.separator)
        if (!folder.exists()) folder.mkdir()
        file = File(folder, "$uid.yml")
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: Exception) {
                pl.logger.severe("Could not create $uid.yml!")
            }
        }
        config = YamlConfiguration.loadConfiguration(file)
        config.set("username", Bukkit.getOfflinePlayer(uid).name)
        save()
    }

    fun c(): FileConfiguration {
        return config
    }

    fun save() {
        try {
            config.save(file)
        } catch (e: Exception) {
            UhcMeetup.pl.logger.severe("${ChatColor.RED}Could not save ${file.name}!")
        }
    }
}