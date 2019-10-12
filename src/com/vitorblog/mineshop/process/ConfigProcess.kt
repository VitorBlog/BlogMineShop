package com.vitorblog.mineshop.process

import com.vitorblog.mineshop.Main
import com.vitorblog.mineshop.dao.CategoryDao
import com.vitorblog.mineshop.model.CONFIG
import com.vitorblog.mineshop.model.inventory.InvHolder
import com.vitorblog.mineshop.model.shop.Category
import com.vitorblog.mineshop.model.shop.ShopItem
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.function.Consumer

class ConfigProcess {

    fun load(config:FileConfiguration){
        CONFIG.POINTS.message = config.getString("messages.points").replace("&", "§")
        CONFIG.NEED_POINTS.message = config.getString("messages.needPoints").replace("&", "§")
        CONFIG.BUY.message = config.getString("messages.buy").replace("&", "§")
        CONFIG.BROADCAST_BUY.message = config.getString("messages.broadcastBuy").replace("&", "§")

        CONFIG.MYSQL_USE.message = config.getBoolean("mysql.use").toString()
        CONFIG.MYSQL_IP.message = config.getString("mysql.ip")
        CONFIG.MYSQL_USER.message = config.getString("mysql.user")
        CONFIG.MYSQL_PASSWORD.message = config.getString("mysql.password")
        CONFIG.MYSQL_DATABASE.message = config.getString("mysql.database")
    }

}