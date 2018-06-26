package lv.mtm123.mineevents.events

import org.bukkit.event.block.BlockBreakEvent

class Explode(chance: Double, private val power: Float) : MEvent(chance) {

    override fun mine(event: BlockBreakEvent) {
        event.block.world.createExplosion(event.block.location, power)
    }

}