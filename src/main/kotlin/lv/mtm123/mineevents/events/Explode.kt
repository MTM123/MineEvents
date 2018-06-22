package lv.mtm123.mineevents.events

import org.bukkit.event.block.BlockBreakEvent

class Explode(var power: Float, chance: Double) : MEvent(chance) {

    override fun BlockBreakEvent.mine() {
        block.world.createExplosion(block.location, power)
    }

}