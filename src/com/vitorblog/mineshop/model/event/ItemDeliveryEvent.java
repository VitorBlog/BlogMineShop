package com.vitorblog.mineshop.model.event;

import br.com.mineshop.msdk.webservice.endpoints.v1.QueueItem;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class ItemDeliveryEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private QueueItem queueItem;

    public ItemDeliveryEvent(QueueItem queueItem) {
        this.queueItem = queueItem;
    }

    public QueueItem getQueueItem() {
        return queueItem;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
