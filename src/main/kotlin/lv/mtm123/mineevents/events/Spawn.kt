package lv.mtm123.mineevents.events

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import java.util.concurrent.ThreadLocalRandom

class Spawn(private val mobs : Map<Double, EntityType>, chance : Double) : MEvent(chance){

    override fun BlockBreakEvent.mine() {
        val type = rollRandomMob()
        block.world.spawn(getSpawnableLocation(player, block.location, type), type.entityClass)
    }

    private fun rollRandomMob() : EntityType{

        val p = ThreadLocalRandom.current().nextDouble()
        var cumulativeWeight = 0.0
        for(e in mobs.entries){
            cumulativeWeight += e.key
            if(p <= cumulativeWeight){
                return e.value
            }
        }

        return EntityType.RABBIT
    }

    private fun getSpawnableLocation(player : Player, loc : Location, type : EntityType) : Location {

        val height : Int
        var width = 1
        when(type){
            EntityType.PIG, EntityType.RABBIT -> height = 1
            EntityType.ENDERMAN -> height = 3
            EntityType.IRON_GOLEM -> {
                height = 3
                width = 2
            }
            else -> height = 2
        }

        var cloc = loc;

        for(x in -5..5){
            for(y in -5..5){
                for(z in -5..5){

                    val rel = loc.block.getRelative(x, y - 1, z).location

                    if(cloc.distanceSquared(player.location) <= rel.distanceSquared(player.location))
                        continue

                    if(isValidSpawnLoc(rel, width, height)){
                        cloc = rel.add(0.0, 1.0, 0.0)
                    }

                }
            }
        }

        if(cloc.equals(loc)){
            cloc = player.location
        }

        return cloc
    }

    private fun isValidSpawnLoc(loc : Location, width : Int, height : Int) : Boolean{

        for(i in 1..height){
            for (j in 1..width){
                val boxLoc = loc.block.getRelative(0, i, j)
                if(boxLoc.type != Material.AIR)
                    return false
            }
        }

        return true
    }

}