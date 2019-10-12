package com.vitorblog.mineshop.process

import com.vitorblog.mineshop.Main
import com.vitorblog.mineshop.dao.CategoryDao
import com.vitorblog.mineshop.dao.PointDao
import com.vitorblog.mineshop.model.MySQL
import com.vitorblog.mineshop.model.inventory.InvHolder
import com.vitorblog.mineshop.model.shop.Category
import com.vitorblog.mineshop.model.shop.ShopItem
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import java.lang.Exception
import java.util.function.Consumer

class PointProcess {

    fun load(){
        object : BukkitRunnable() {
            override fun run() {
                try{
                    MySQL.connect()
                    MySQL.createDatabase()

                    val rs = MySQL.query("SELECT * FROM BlogPoints")
                    while (rs.next()){
                        PointDao.add(rs.getString(1), rs.getInt(2))
                    }
                    MySQL.close()
                } catch (e:Exception){
                    println("§b§lBlogMineShop §8»§7 Não foi possível carregar os pontos;")
                    e.printStackTrace()
                    MySQL.close()
                    Bukkit.getPluginManager().disablePlugin(Main.instance!!)
                }
            }
        }.runTaskAsynchronously(Main.instance!!)
    }

    fun save(){
        try {
            MySQL.connect()
            MySQL.createDatabase()

            for ((player, points) in PointDao.POINTS){
                val rs = MySQL.query("SELECT * FROM BlogPoints WHERE player='$player'")
                if (rs.next()){
                    MySQL.update("UPDATE BlogPoints SET points='$points' WHERE player='$player'")
                } else {
                    MySQL.update("INSERT INTO BlogPoints(player, points) VALUES ('$player', '$points')")
                }
            }
            MySQL.close()
        } catch (e:Exception){
            MySQL.close()
            println("§b§lBlogMineShop §8»§7 Não foi possível salvar os pontos;")
            e.printStackTrace()
        }
    }

    fun updateSpecificPlayer(player:String){
        val points = PointDao.get(player)

        object : BukkitRunnable() {
            override fun run() {
                try{
                    MySQL.connect()
                    MySQL.createDatabase()

                    val rs = MySQL.query("SELECT * FROM BlogPoints WHERE player='$player'")
                    if (rs.next()){
                        MySQL.update("UPDATE BlogPoints SET points='$points' WHERE player='$player'")
                    } else {
                        MySQL.update("INSERT INTO BlogPoints(player, points) VALUES ('$player', '$points')")
                    }

                    MySQL.close()
                } catch (e:Exception){
                    MySQL.close()
                    println("§b§lBlogMineShop §8»§7 Não foi possível salvar os pontos de '$player';")
                    e.printStackTrace()
                }
            }
        }.runTaskAsynchronously(Main.instance!!)
    }

}