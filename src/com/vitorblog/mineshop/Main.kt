package com.vitorblog.mineshop

import be.maximvdw.placeholderapi.PlaceholderAPI
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent
import be.maximvdw.placeholderapi.PlaceholderReplacer
import br.com.mineshop.msdk.exceptions.MsdkException
import br.com.mineshop.msdk.webservice.endpoints.v1.QueueItem
import br.com.mineshop.msdk.exceptions.WebServiceException
import br.com.mineshop.msdk.MSDK
import com.vitorblog.mineshop.dao.CommandDao
import com.vitorblog.mineshop.dao.PointDao
import com.vitorblog.mineshop.handler.BukkitHandler
import com.vitorblog.mineshop.handler.CommandHandler
import com.vitorblog.mineshop.handler.PAPIHandler
import com.vitorblog.mineshop.model.inventory.InvHolder
import com.vitorblog.mineshop.process.ConfigProcess
import com.vitorblog.mineshop.process.PointProcess
import com.vitorblog.mineshop.process.ShopProcess
import com.vitorblog.mineshop.threads.QueueItemsThread
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.net.URL

class Main : JavaPlugin() {

    companion object {
        var instance:Main? = null
    }
    val msdk = MSDK()
    var shop:InvHolder? = null
    var hasUpdate = false
    var lastVersion = "0.0.0"

    override fun onEnable() {
        instance = this

        //Check updates
        checkUpdates()

        //Load commands
        CommandDao.COMMANDS.forEach({
            val command = getCommand(it.key)
            command.executor = CommandHandler()
            command.tabCompleter = CommandHandler()
        })

        //Load config
        this.saveDefaultConfig()

        ConfigProcess().load(config)
        ShopProcess().load(config)

        this.msdk.setCredentials(this.config.getString("token"))

        val timerAfterRestart = this.config.getInt("eventLoop.timer.afterRestart")
        val timerDelay = this.config.getInt("eventLoop.timer.delay")

        if (timerAfterRestart < 20) {
            Bukkit.getLogger().warning(String.format(
                    "§b§lBlogMineShop §8»§7 O event loop está configurado para ser executado em %s segundo(s) logo após a " + "reinicialização do servidor ou do plugin! Recomendamos um delay entre 20 e 300 segundos neste campo.",
                    this.description.name,
                    Integer.toString(timerAfterRestart)
            ))
        }

        if (timerDelay < 10) {
            Bukkit.getLogger().warning(String.format(
                    "§b§lBlogMineShop §8»§7 O event loop está configurado para ser executado a cada %s segundo(s)! Recomendamos um " + "delay entre 10 e 60 segundos neste campo.",
                    this.description.name,
                    Integer.toString(timerDelay)
            ))
        }

        //Load points
        if (shop != null){
            PointProcess().load()
        }

        //Load threads
        if (this.config.getString("token") != null) {
            QueueItemsThread(timerDelay).runTaskTimerAsynchronously(this, (timerAfterRestart * 20).toLong(), (timerDelay * 20).toLong())
        }

        //Handler register
        Bukkit.getPluginManager().registerEvents(BukkitHandler(), this)

        //Placeholder register
        if (Bukkit.getPluginManager().getPlugin("MVdWPlaceholderAPI") != null) {
            PlaceholderAPI.registerPlaceholder(this, "blogpoints_points", object : PlaceholderReplacer {
                override
                fun onPlaceholderReplace(e: PlaceholderReplaceEvent): String {
                    var points = PointDao.get(e.player.name)
                    if (points == null) {
                        points = 0
                    }

                    return points.toString()
                }
            })
            Bukkit.getConsoleSender().sendMessage("§b§lBlogMineShop §8»§7 Hook MVdWPlaceholderAPI ativado")
        }
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PAPIHandler().register()
            Bukkit.getConsoleSender().sendMessage("§b§lBlogMineShop §8»§7 Hook PlaceholderAPI ativado")
        }
    }

    override
    fun onDisable() {
        //Save points
        if (shop != null){
            PointProcess().save()
        }
    }

    fun checkUpdates(){
        object : BukkitRunnable(){
            override
            fun run() {

                val url = URL("https://api.github.com/repos/VitorBlog/BlogMineShop/releases/latest")
                val connection = url.openConnection()
                val json = JSONParser().parse(connection.getInputStream().reader().readText()) as JSONObject

                lastVersion = json.get("tag_name").toString()

                if (lastVersion != instance!!.description.version){
                    Bukkit.getConsoleSender().sendMessage("§b§lBlogMineShop §8»§7 Uma nova versão esta disponível! Baixe a v§b${lastVersion}§7 em §b§nhttps://github.com/VitorBlog/BlogMineShop/releases/latest")

                    for (player in Bukkit.getOnlinePlayers().filter { it.isOp || it.hasPermission("*") }){
                        player.sendMessage("§b§lBlogMineShop §8»§7 Uma nova versão esta disponível! Baixe a v§b${lastVersion}§7 em §b§nhttps://github.com/VitorBlog/BlogMineShop/releases/latest")
                    }

                    hasUpdate = true
                }

            }
        }.runTaskAsynchronously(this)
    }

}
