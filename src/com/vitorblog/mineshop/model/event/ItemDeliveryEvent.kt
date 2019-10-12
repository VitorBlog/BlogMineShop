package com.vitorblog.mineshop.model.event

import br.com.mineshop.msdk.webservice.endpoints.v1.QueueItem
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class ItemDeliveryEvent(val queueItem:QueueItem) : Event() {

    private val handlers = HandlerList()

    override fun getHandlers(): HandlerList {
        return handlers
    }

    fun getHandlerList(): HandlerList {
        return handlers
    }

}