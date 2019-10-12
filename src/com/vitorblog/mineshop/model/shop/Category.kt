package com.vitorblog.mineshop.model.shop

import com.vitorblog.mineshop.model.inventory.InvHolder
import org.bukkit.inventory.ItemStack

class Category(val id:String, val name:String, val itemStack: ItemStack, val inv:InvHolder, val items:ArrayList<ShopItem>)