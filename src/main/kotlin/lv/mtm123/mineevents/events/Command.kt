package lv.mtm123.mineevents.events

import org.bukkit.Bukkit
import org.bukkit.event.block.BlockBreakEvent

class Command(private val cmd : String, chance: Double) : MEvent(chance) {

    override fun BlockBreakEvent.mine() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", player.name))
    }

}