package com.vitorblog.mineshop.command

import com.vitorblog.mineshop.Main
import com.vitorblog.mineshop.model.Command
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ShopCommand : Command{
    val main = Main.instance!!

    override
    fun execute(sender: CommandSender, cmd: org.bukkit.command.Command, a: Array<String>) {
        if (sender is Player && main.shop != null){
            sender.openInventory(main.shop!!.inv)
        }
    }

    override
    fun getTabs(a: Int): ArrayList<String> {
        return arrayListOf()
    }

}