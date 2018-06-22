package lv.mtm123.mineevents.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import org.bukkit.command.CommandSender

@CommandAlias("mineevents|me|mevents")
@CommandPermission("mineevents.admin")
class CoreCommand : BaseCommand(){

    @Default
    @CommandAlias("")
    fun reload(sender : CommandSender){

    }

}