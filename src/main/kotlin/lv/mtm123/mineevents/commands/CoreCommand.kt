package lv.mtm123.mineevents.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import lv.mtm123.mineevents.MineEvents
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

@CommandAlias("mineevents|mev|mevents")
@CommandPermission("mineevents.admin")
class CoreCommand : BaseCommand(){

    @Dependency
    private lateinit var plugin : MineEvents

    @Default
    @Subcommand("r|reload")
    @CommandAlias("mer")
    fun reload(sender: CommandSender){
        plugin.loadPlugin(true)
        sender.sendMessage("${ChatColor.GREEN}Plugin reloaded!")
    }

}