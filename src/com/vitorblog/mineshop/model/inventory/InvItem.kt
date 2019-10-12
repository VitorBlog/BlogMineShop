package com.vitorblog.mineshop.model.inventory

import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.inventory.ItemStack
import java.util.function.Consumer

class InvItem(val slot:Int, val itemStack:ItemStack, val click:Consumer<InventoryInteractEvent>?)