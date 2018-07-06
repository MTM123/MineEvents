package lv.mtm123.mineevents.events

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Creature
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import java.util.concurrent.ThreadLocalRandom

class Spawn(chance : Double, private val mobs : Set<Mob>) : MEvent(chance){

    class Mob(var weight : Double, val type: EntityType)

    override fun mine(event: BlockBreakEvent) {
        val type = rollRandomMob()
        val en = event.block.world.spawn(getSpawnableLocation(event.player, event.block.location, type), type.entityClass)
        if(en is Creature){
            en.target = event.player
        }
    }

    private fun rollRandomMob() : EntityType{

        val p = ThreadLocalRandom.current().nextDouble()
        var cumulativeWeight = 0.0
        for(m in mobs){
            cumulativeWeight += m.weight
            if(p <= cumulativeWeight){
                return m.type
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

        var cloc : Location = loc.block.getRelative(-5, -5, -5).location

        for(x in -5..5){
            for(y in -5..5){
                for(z in -5..5){

                    val rel = loc.block.getRelative(x, y, z).location

                    player.sendBlockChange(rel, Material.STAINED_GLASS, 0)

                    if(cloc.distanceSquared(player.location) <= rel.distanceSquared(player.location))
                        continue

                    if(!rel.block.type.isSolid)
                        continue

                    if(isValidSpawnLoc(rel, width, height)){
                        cloc = rel
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