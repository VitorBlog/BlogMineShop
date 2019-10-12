package com.vitorblog.mineshop.handler

import com.vitorblog.mineshop.model.event.ItemDeliveryEvent
import com.vitorblog.mineshop.model.inventory.InvHolder
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class BukkitHandler : Listener {

    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent){
        if (e.clickedInventory != null && e.clickedInventory.holder != null && e.clickedInventory.holder is InvHolder){
            var holder = e.clickedInventory.holder as InvHolder

            if (holder.itens.containsKey(e.slot)){

                val item = holder.itens.get(e.slot)!!
                if (e.isLeftClick && item.leftClick != null){
                    item.leftClick.accept(e)
                }
                if (e.isRightClick && item.rightClick != null){
                    item.rightClick.accept(e)
                }

                e.isCancelled = true
            }
        }
    }

}