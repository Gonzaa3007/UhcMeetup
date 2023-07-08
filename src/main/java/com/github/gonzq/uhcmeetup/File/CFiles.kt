package com.github.gonzq.uhcmeetup.File

import com.github.gonzq.uhcmeetup.UhcMeetup
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.logging.Level

class CFiles(var pl: UhcMeetup, var name: String) {
    private var fileConfig: FileConfiguration? = null
    private var configFile: File? = null

    init {
        saveDefaultConfig()
    }

    public fun reloadConfig() {
        if (configFile == null)  {
            configFile = File(pl.dataFolder, name)
        }
        fileConfig = YamlConfiguration.loadConfiguration(configFile!!)
        val defaultStream: InputStream = pl.getResource(name)!!
        val defaultConfig: YamlConfiguration = YamlConfiguration.loadConfiguration(InputStreamReader(defaultStream))
        (fileConfig as YamlConfiguration).setDefaults(defaultConfig)
    }

    public fun getConfig(): FileConfiguration {
        if (fileConfig == null) reloadConfig()
        return fileConfig!!
    }

    public fun getFile(): File? {
        if (configFile == null) reloadConfig()
        return configFile
    }

    public fun saveConfig() {
        if (fileConfig == null || configFile == null) return
        try {
            getConfig().save(configFile!!)
        } catch (e: IOException) {
            pl.logger.log(Level.SEVERE, "Could not save config to $configFile",e)
        }
    }

    public fun saveDefaultConfig() {
        if (configFile == null) configFile = File(pl.dataFolder, name)
        if (!configFile!!.exists()) pl.saveResource(name, false)
    }
}