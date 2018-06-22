package lv.mtm123.mineevents.events

import org.bukkit.event.block.BlockBreakEvent

abstract class MEvent(var chance : Double){

    abstract fun BlockBreakEvent.mine()

}