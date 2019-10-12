package com.vitorblog.mineshop.model.inventory

import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import java.util.function.Consumer

class InvHolder(size:Int, title:String) : InventoryHolder {

    val inv = Bukkit.createInventory(this, size, title)
    val itens = hashMapOf<Int, InvItem>()

    fun setItem(slot:Int, itemStack: ItemStack){
        inv.setItem(slot, itemStack)
        itens.put(slot, InvItem(slot, itemStack, null))
    }

    fun setItem(slot:Int, itemStack: ItemStack, click:Consumer<InventoryInteractEvent>?){
        inv.setItem(slot, itemStack)
        itens.put(slot, InvItem(slot, itemStack, click))
    }

    override fun getInventory(): Inventory { return inv }
}