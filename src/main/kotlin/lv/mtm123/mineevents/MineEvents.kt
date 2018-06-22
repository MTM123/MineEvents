package lv.mtm123.mineevents

import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin

class MineEvents : JavaPlugin(){

    override fun onEnable() {
        super.onEnable()
    }

    override fun onDisable() {

    }

    private fun loadPlugin(reload : Boolean){

        if(reload){
            HandlerList.unregisterAll(this)
        }


    }
}