package com.vitorblog.mineshop

import br.com.mineshop.msdk.exceptions.MsdkException
import br.com.mineshop.msdk.webservice.endpoints.v1.QueueItem
import br.com.mineshop.msdk.exceptions.WebServiceException
import br.com.mineshop.msdk.MSDK
import com.vitorblog.mineshop.dao.CommandDao
import com.vitorblog.mineshop.handler.CommandHandler
import com.vitorblog.mineshop.threads.QueueItemsThread
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class Main : JavaPlugin() {

    companion object {
        var instance:Main? = null
    }
    val msdk = MSDK()

    override fun onEnable() {
        instance = this

        //Load commands
        CommandDao.COMMANDS.forEach({
            val command = getCommand(it.key)
            command.executor = CommandHandler()
            command.tabCompleter = CommandHandler()
        })

        //Load config
        this.saveDefaultConfig()
        this.msdk.setCredentials(this.config.getString("token"))

        val timerAfterRestart = this.config.getInt("eventLoop.timer.afterRestart")
        val timerDelay = this.config.getInt("eventLoop.timer.delay")

        if (timerAfterRestart < 20) {
            Bukkit.getLogger().warning(String.format(
                    "[%s] O event loop está configurado para ser executado em %s segundo(s) logo após a " + "reinicialização do servidor ou do plugin! Recomendamos um delay entre 20 e 300 segundos neste campo.",
                    this.description.name,
                    Integer.toString(timerAfterRestart)
            ))
        }

        if (timerDelay < 10) {
            Bukkit.getLogger().warning(String.format(
                    "[%s] O event loop está configurado para ser executado a cada %s segundo(s)! Recomendamos um " + "delay entre 10 e 60 segundos neste campo.",
                    this.description.name,
                    Integer.toString(timerDelay)
            ))
        }

        //Load threads
        QueueItemsThread(timerDelay).runTaskTimerAsynchronously(this, (timerAfterRestart * 20).toLong(), (timerDelay * 20).toLong())
    }

}
