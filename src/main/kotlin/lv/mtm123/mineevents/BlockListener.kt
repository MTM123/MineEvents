package lv.mtm123.mineevents

import lv.mtm123.mineevents.events.MEvent
import lv.mtm123.mineevents.events.MEventManager
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import java.util.logging.Level

class BlockListener(cfg : FileConfiguration) : Listener{

    private val mineEvents = loadEvents(cfg)

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun BlockBreakEvent.onBlockBreak(){

    }

    private fun loadEvents(cfg : FileConfiguration) : Map<Material, MEventManager>{
        val events = HashMap<Material, MEventManager>()

        for(k in cfg.getConfigurationSection("blocks").getKeys(false)){
            val mat = Material.matchMaterial(k)
            if(mat == null){
                Bukkit.getLogger().log(Level.WARNING, "Incorrect material '${k}' !")
                continue
            }



        }

        return events
    }

    private fun parseEvents(list : List<String>) : Set<MEvent>{
        val events = HashSet<MEvent>()

        for(li in list){
            val data = li.split(" ")
            if(data.size == 0)
                continue

            when(data[0].toLowerCase()){

            }
        }

        return events

    }

}