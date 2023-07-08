package com.github.gonzq.uhcmeetup.Scenarios

import com.github.gonzq.uhcmeetup.UhcMeetup
import com.github.gonzq.uhcmeetup.Utils.Utils
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack

abstract class Scenario protected constructor (private val name: String, private val icon: ItemStack, vararg desc: String?) {
    private val pl = UhcMeetup.pl
    private var description = mutableListOf<String>()

    init {
        for (s in desc) {
            description.add(Utils.chat(s))
        }
    }

    fun getName(): String {
        return name
    }
    fun getDescription(): MutableList<String> {
        return description
    }
    fun getIcon(): ItemStack {
        return icon
    }

    fun enable() {
        if (this is Listener) {
            Bukkit.getServer().pluginManager.registerEvents(this as Listener, pl)
        }
        setEnabled(true)
    }
    fun disable() {
        if (this is Listener) {
            HandlerList.unregisterAll(this as Listener)
        }
        setEnabled(false)
    }

    protected abstract fun setEnabled(enable: Boolean)
    public abstract fun isEnabled(): Boolean
}