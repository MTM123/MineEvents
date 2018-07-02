package lv.mtm123.mineevents

import co.aikar.commands.BukkitCommandManager
import lv.mtm123.mineevents.commands.CoreCommand
import lv.mtm123.spigotutils.ConfigManager
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin

class MineEvents : JavaPlugin(){

    private lateinit var commandManager : BukkitCommandManager

    override fun onEnable() {

        ConfigManager.initialize(this)

        loadPlugin()

    }

    fun loadPlugin(reload : Boolean = false){

        if(reload){
            HandlerList.unregisterAll(this)
            commandManager.unregisterCommands()
        }

        commandManager = BukkitCommandManager(this)

        val cfg = ConfigManager.load("config.yml")

        server.pluginManager.registerEvents(BlockListener(cfg), this)

        commandManager.registerCommand(CoreCommand())

    }
}