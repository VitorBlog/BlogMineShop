package com.vitorblog.mineshop.model.event;

import com.vitorblog.mineshop.model.shop.ShopItem;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class PlayerBuyOnShopEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private ShopItem shopItem;

    public PlayerBuyOnShopEvent(Player player, ShopItem shopItem) {
        this.player = player;
        this.shopItem = shopItem;
    }

    public Player getPlayer() {
        return player;
    }

    public ShopItem getShopItem() {
        return shopItem;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
