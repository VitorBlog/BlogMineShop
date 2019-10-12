package com.vitorblog.mineshop.model.shop

import com.vitorblog.mineshop.dao.PointDao
import com.vitorblog.mineshop.model.CONFIG
import com.vitorblog.mineshop.model.event.PlayerBuyOnShopEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ShopItem(val id:String, val name:String, val price:Int, val itemStack: ItemStack, val category:String, val commands:List<String>) {

    fun buy(player:Player){
        player.closeInventory()

        val points = PointDao.get(player.name)
        if (points == null || points < price){
            player.sendMessage(CONFIG.NEED_POINTS.message.replace("%price%", price.toString()).replace("%item%", name))
            return
        }

        PointDao.add(player.name, points-price)

        for (command in commands){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.name))
        }

        player.sendMessage(CONFIG.BUY.message.replace("%price%", price.toString()).replace("%item%", name))

        if (CONFIG.BROADCAST_BUY.message != ""){
            Bukkit.broadcastMessage(CONFIG.BROADCAST_BUY.message.replace("%price%", price.toString()).replace("%item%", name).replace("%player%", player.name))
        }

        Bukkit.getPluginManager().callEvent(PlayerBuyOnShopEvent(player, this))
    }

}