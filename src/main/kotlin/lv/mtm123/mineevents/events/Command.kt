package lv.mtm123.mineevents.events

import org.bukkit.Bukkit
import org.bukkit.event.block.BlockBreakEvent

class Command(chance: Double, private val cmd: String) : MEvent(chance) {

    override fun mine(event: BlockBreakEvent) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", event.player.name))
    }

}