package com.vitorblog.mineshop.command

import com.vitorblog.mineshop.Main
import com.vitorblog.mineshop.dao.PointDao
import com.vitorblog.mineshop.model.CONFIG
import com.vitorblog.mineshop.model.Command
import com.vitorblog.mineshop.model.MySQL
import com.vitorblog.mineshop.process.PointProcess
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PointCommand : Command{
    val main = Main.instance!!

    override
    fun execute(sender: CommandSender, cmd: org.bukkit.command.Command, a: Array<String>) {
        if (main.shop == null){return}

        if (a.isEmpty() && sender is Player){
            var points = PointDao.get(sender.name)
            if (points == null){
                points = 0
            }

            sender.sendMessage(CONFIG.POINTS.message.replace("%points%", points.toString()))
            return
        }

        if (!sender.hasPermission("*")){return}

        if (a.size != 3){
            sendHelp(sender)
            return
        }

        when (a[0]){
            "give" -> {
                var points = PointDao.get(a[1])
                if (points == null){
                    points = 0
                }

                PointDao.add(a[1], points+a[2].toInt())
                PointProcess().updateSpecificPlayer(a[1])
                sender.sendMessage("§b§lBlogMineShop §8»§7 ${a[1]} agora possui ${points+a[2].toInt()} pontos!")
            }
            "remove" -> {
                var points = PointDao.get(a[1])
                if (points == null){
                    points = 0
                }

                PointDao.add(a[1], points-a[2].toInt())
                PointProcess().updateSpecificPlayer(a[1])
                sender.sendMessage("§b§lBlogMineShop §8»§7 ${a[1]} agora possui ${points-a[2].toInt()} pontos!")
            }
            "set" -> {
                PointDao.add(a[1], a[2].toInt())
                PointProcess().updateSpecificPlayer(a[1])
                sender.sendMessage("§b§lBlogMineShop §8»§7 ${a[1]} agora possui ${a[2]} pontos!")
            }
            else -> {
                sendHelp(sender)
            }
        }
    }

    fun sendHelp(sender:CommandSender){
        sender.sendMessage("§b§lBlogMineShop §8»§7 Comandos:\n" +
                " §8*§7/pontos give <player> <quantidade>\n" +
                " §8*§7/pontos remove <player> <quantidade>\n" +
                " §8*§7/pontos set <player> <quantidade>\n")
    }

    override
    fun getTabs(a: Int): ArrayList<String> {
        return arrayListOf()
    }

}