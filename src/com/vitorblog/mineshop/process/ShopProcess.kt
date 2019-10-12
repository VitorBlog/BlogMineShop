package com.vitorblog.mineshop.process

import com.vitorblog.mineshop.Main
import com.vitorblog.mineshop.dao.CategoryDao
import com.vitorblog.mineshop.model.inventory.InvHolder
import com.vitorblog.mineshop.model.shop.Category
import com.vitorblog.mineshop.model.shop.ShopItem
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.function.Consumer

class ShopProcess {

    fun load(config:FileConfiguration){
        if (!config.getBoolean("shop.activate")){
            Bukkit.getConsoleSender().sendMessage("§b§lBlogMineShop §8»§7 Shop e pontos desativados...")
            return
        }

        //Shop
        val inv = InvHolder(config.getInt("shop.menuRows")*9, config.getString("shop.menuTitle").replace("&", "§"))

        //Categories
        for (section in config.getConfigurationSection("shop.categories").getKeys(false)){

            val itemName = config.getString("shop.categories.$section.name").replace("&", "§")
            val lore = config.getStringList("shop.categories.$section.lore")
            lore.replaceAll({it.replace("&", "§")})

            val itemStack = ItemStack(Material.getMaterial(config.getInt("shop.categories.$section.ID")))
            val itemMeta = itemStack.itemMeta

            itemMeta.displayName = itemName
            itemMeta.lore = lore

            itemStack.itemMeta = itemMeta

            val inv2 = InvHolder(config.getInt("shop.categories.$section.menuRows")*9, config.getString("shop.categories.$section.menuTitle").replace("&", "§"))
            val items = arrayListOf<ShopItem>()

            //Items
            for (section2 in config.getConfigurationSection("shop.categories.$section.items").getKeys(false)){
                val itemName2 = config.getString("shop.categories.$section.items.$section2.name").replace("&", "§")
                val lore2 = config.getStringList("shop.categories.$section.items.$section2.lore")
                lore2.replaceAll({it.replace("&", "§")})

                val itemStack2 = ItemStack(Material.getMaterial(config.getInt("shop.categories.$section.items.$section2.ID")))
                val itemMeta2 = itemStack2.itemMeta

                itemMeta2.displayName = itemName2
                itemMeta2.lore = lore2

                itemStack2.itemMeta = itemMeta2

                val shopItem = ShopItem(section2, itemName2, config.getInt("shop.categories.$section.items.$section2.price"), itemStack2, section, config.getStringList("shop.categories.$section.items.$section2.commands"))
                inv2.setItem(config.getInt("shop.categories.$section.items.$section2.slot"), itemStack2, Consumer{
                    shopItem.buy(it.whoClicked as Player)
                })
                items.add(shopItem)
            }
            val category = Category(section, itemName, itemStack, inv2, items)
            inv.setItem(config.getInt("shop.categories.$section.slot"), itemStack, Consumer{
                it.whoClicked.openInventory(category.inv.inventory)
            })
            CategoryDao.add(category)
        }

        Main.instance!!.shop = inv
    }

}