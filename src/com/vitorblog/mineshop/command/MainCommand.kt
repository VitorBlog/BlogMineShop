package com.vitorblog.mineshop.command

import com.vitorblog.mineshop.Main
import com.vitorblog.mineshop.model.Command
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class MainCommand : Command{
    val main = Main.instance!!
    val msdk = main.msdk

    override
    fun execute(sender: CommandSender, cmd: org.bukkit.command.Command, a: Array<String>) {
        if (!sender.hasPermission("*")){return}

        if (a.isNotEmpty()){
            val token = a[0].trim().toLowerCase()

            msdk.setCredentials(token)
            main.getConfig().set("token", token)
            main.saveConfig()

            sender.sendMessage(String.format(
                    "%sPronto! Se o token informado estiver correto, este servidor irá sincronizar com sua loja logo após reiniciar o servidor",
                    ChatColor.GREEN
            ))
            return
        }

        sender.sendMessage("/mineshop <token>")
    }

    override
    fun getTabs(a: Int): ArrayList<String> {
        return arrayListOf()
    }

}