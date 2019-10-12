package com.vitorblog.mineshop.model.event

import com.vitorblog.mineshop.model.shop.ShopItem
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PlayerBuyOnShopEvent(val player:Player, val shopItem: ShopItem) : Event() {

    private val handlers = HandlerList()

    override fun getHandlers(): HandlerList {
        return handlers
    }

    fun getHandlerList(): HandlerList {
        return handlers
    }

}