## MineShop Fork [![Codacy Badge](https://api.codacy.com/project/badge/Grade/b87784702a5c4ea89802ea645ef6aedc)]=
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
