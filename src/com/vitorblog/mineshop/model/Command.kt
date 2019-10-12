package com.vitorblog.mineshop.model

import org.bukkit.command.Command
import org.bukkit.command.CommandSender

interface Command {

    fun execute(sender: CommandSender, cmd: Command, a: Array<String>)
    fun getTabs(a:Int):ArrayList<String>

}