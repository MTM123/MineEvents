package lv.mtm123.mineevents

import lv.mtm123.mineevents.events.*
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import java.util.logging.Level
import kotlin.collections.HashMap

class BlockListener(cfg : FileConfiguration) : Listener{

    private val mineEvents = loadEvents(cfg)

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun BlockBreakEvent.onBlockBreak(){
        if(!mineEvents.containsKey(block.type))
            return

        if(!mineEvents.containsKey(block.type))
            return

        val mEventManager = mineEvents[block.type]

        val chance = mEventManager?.chance ?: 0.0
        println(chance)
        if(ThreadLocalRandom.current().nextDouble() <= chance){
            mEventManager?.rollRandomEvent()?.mine(this)
        }
    }

    private fun loadEvents(cfg : FileConfiguration) : Map<Material, MEventManager>{
        val eventManagers = HashMap<Material, MEventManager>()

        for(k in cfg.getConfigurationSection("blocks").getKeys(false)){
            val mat = Material.matchMaterial(k)
            if(mat == null){
                Bukkit.getLogger().log(Level.WARNING, "Incorrect material '$k' !")
                continue
            }

            val events = parseEvents(cfg.getStringList("blocks.$k.events"))
            val chance = cfg.getDouble("blocks.$k.chance")

            eventManagers[mat] = MEventManager(chance, events)

        }

        return eventManagers
    }

    private fun parseEvents(list : List<String>) : Set<MEvent>{
        val events = HashSet<MEvent>()

        for(li in list){
            val data = li.split(" ")
            if(data.size <= 2)
                continue

            var weight: Double
            try {
                weight = data[0].toLowerCase().toDouble()
            }catch (e: NumberFormatException){
                Bukkit.getLogger().log(Level.WARNING, "'${data[0]}' is not a number! It must be a number!")
                continue
            }

            when(data[1].toLowerCase()){
                "cmd" -> {
                    val cmd = Arrays.copyOfRange(data.toTypedArray(),2, data.size).joinToString(" ")
                    events.add(Command(weight, cmd))
                }
                "explode" -> {
                    try {
                        val power = data[2].toFloat()
                        events.add(Explode(weight, power))
                    }catch (e: NumberFormatException){
                        Bukkit.getLogger().log(Level.WARNING, "'${data[2]}' is not a number! It must be a number!")
                    }

                }
                "spawn" -> {
                    val mobs = HashSet<Spawn.Mob>()

                    var sum = 0.0

                    data[2].split(",").forEach{
                        val md = it.split(":")
                        if(md.size == 2){
                            try {
                                val w = md[0].toDouble()
                                sum += w

                                val type = EntityType.valueOf(md[1].toUpperCase().replace("-", "_"))
                                mobs.add(Spawn.Mob(w, type))
                            }catch (e: NumberFormatException){
                                Bukkit.getLogger().log(Level.WARNING, "'${md[0]}' is not a number! It must be a number!")
                            }

                        }else{
                            Bukkit.getLogger().log(Level.WARNING, "'$it' is incorrectly formatted! Expected format: WEIGHT:ENTITY_TYPE")
                        }
                    }

                    mobs.forEach { m -> m.weight /= sum }

                    events.add(Spawn(weight, mobs))
                }
            }
        }

        return events

    }

}