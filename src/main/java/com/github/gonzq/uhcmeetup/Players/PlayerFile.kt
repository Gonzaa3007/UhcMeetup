package com.github.gonzq.uhcmeetup.Players

import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.UUID

class PlayerFile(uid: UUID) {
    var config: FileConfiguration
    var file: File

    companion object {
        fun get(uid: UUID): PlayerFile {
            return PlayerFile(uid)
        }
    }

    init {
        val pl = UhcMeetup.pl
        val folder = File("" + pl.dataFolder + File.separator + "players" + File.separator)
        if (!folder.exists()) folder.mkdir()
        file = File(folder, "$uid.yml")
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: Exception) {
                pl.logger.severe(Utils.chat("Could not create $uid.yml!"))
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
            UhcMeetup.pl.logger.severe(Utils.chat("&cCould not save ${file.name}!"))
        }
    }
}