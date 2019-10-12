package com.vitorblog.mineshop.threads

import br.com.mineshop.msdk.exceptions.MsdkException
import br.com.mineshop.msdk.exceptions.WebServiceException
import br.com.mineshop.msdk.webservice.endpoints.v1.QueueItem
import com.vitorblog.mineshop.Main
import com.vitorblog.mineshop.model.event.ItemDeliveryEvent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.scheduler.BukkitRunnable

class QueueItemsThread(val timerDelay:Int) : BukkitRunnable() {

    var msdk = Main.instance!!.msdk

    override fun run() {
        var queueItems: Array<QueueItem>? = null

        try {
            queueItems = msdk.queueItems
        } catch (e: WebServiceException) {
            Bukkit.getLogger().warning(String.format("§b§lBlogMineShop §8»§7 %s", Main.instance!!.description.name, e.message))
        } catch (e: MsdkException) {
            Bukkit.getLogger().warning(String.format("§b§lBlogMineShop §8»§7 %s", Main.instance!!.description.name, e.message))
        }

        if (queueItems == null) {
            return
        }

        for (queueItem in queueItems) {
            object : BukkitRunnable() {
                override fun run() {
                    if (queueItem.type.equals("online", ignoreCase = true)) {
                        val player = Bukkit.getPlayerExact(queueItem.nickname) ?: return

                        var emptySlots = 0

                        for (item in player.inventory.contents) {
                            if (item == null) {
                                emptySlots++
                            }
                        }

                        if (queueItem.slotsNeeded > emptySlots) {
                            player.sendMessage(String.format(
                                    "%sNão pudemos entregar todos os itens que você comprou em nossa loja porque seu " +
                                            "inventário não tem espaço suficiente. O restante dos itens serão entregues em %s segundo(s). " +
                                            "Para recebê-los, por favor, esvazie seu inventário.",
                                    ChatColor.LIGHT_PURPLE,
                                    Integer.toString(timerDelay)
                            ))

                            return
                        }
                    }

                    try {
                        msdk.hasBeenDelivered(queueItem.nickname, queueItem.uuid)
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), queueItem.command)
                        Bukkit.getPluginManager().callEvent(ItemDeliveryEvent(queueItem))
                    } catch (e: WebServiceException) {
                        Bukkit.getLogger().warning(String.format("§b§lBlogMineShop §8»§7 %s", Main.instance!!.description.name, e.message))
                    } catch (e: MsdkException) {
                        Bukkit.getLogger().warning(String.format("§b§lBlogMineShop §8»§7 %s", Main.instance!!.description.name, e.message))
                    }

                }
            }.runTask(Main.instance)
        }
    }

}
