package com.github.gonzq.uhcmeetup.Utils

import com.github.gonzq.uhcmeetup.UhcMeetup
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

open class FileUtils {

    companion object {
        fun getConfigs(): List<FileConfiguration> {
            val l: ArrayList<FileConfiguration> = ArrayList()
            File(UhcMeetup.pl.dataFolder.absolutePath + File.separator + "players").listFiles()!!.forEach { file ->
                l.add(YamlConfiguration.loadConfiguration(file))
            }
            return l
        }
    }
}