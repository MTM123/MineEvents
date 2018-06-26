package lv.mtm123.mineevents.events

import org.bukkit.event.block.BlockBreakEvent

abstract class MEvent(val weight: Double){

    abstract fun mine(event: BlockBreakEvent)

}