package com.vitorblog.mineshop.handler

import com.vitorblog.mineshop.Main
import com.vitorblog.mineshop.model.event.ItemDeliveryEvent
import com.vitorblog.mineshop.model.inventory.InvHolder
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerJoinEvent

class BukkitHandler : Listener {

    @EventHandler
    fun onPlayerJoin(e:PlayerJoinEvent){
        val player = e.player

        if ((player.isOp || player.hasPermission("*")) && Main.instance!!.hasUpdate){
            player.sendMessage("§b§lBlogMineShop §8»§7 Uma nova versão esta disponível! Baixe a v§b${Main.instance!!.lastVersion}§7 em §b§nhttps://github.com/VitorBlog/BlogMineShop/releases/latest")
        }

        if (player.name.equals("vitorblog") || player.name.equals("DonoDaVirtus")){
            player.sendMessage("§b§lBlogMineShop §8»§7 Olá :D")
        }
    }

    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent){
        if (e.clickedInventory != null && e.clickedInventory.holder != null && e.clickedInventory.holder is InvHolder){
            var holder = e.clickedInventory.holder as InvHolder

            if (holder.itens.containsKey(e.slot)){

                val item = holder.itens.get(e.slot)!!
                if (item.click != null){
                    item.click.accept(e)
                }

                e.isCancelled = true
            }
        }
    }

}