## MineShop Fork
Tava doidão;

### API

#### Eventos

ItemDeliveryEvent » Chamado quando um item é enviado.
```java
@EventHandler
public void onItemDeliveryEvent(ItemDeliveryEvent itemDeliveryEvent){
    QueueItem queueItem = itemDeliveryEvent.getQueueItem();
    //Your code;
}
```

PlayerBuyOnShopEvent » Chamado quando um item é comprado no shop.
```java
@EventHandler
public void onPlayerBuyOnShopEvent(PlayerBuyOnShopEvent playerBuyOnShopEvent){
    Player player = playerBuyOnShopEvent.getPlayer();
    ShopItem shopItem = playerBuyOnShopEvent.getShopItem();
    //Your code;
}
```
