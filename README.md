## MineShop Fork
Tava doidão;

### API

#### Eventos

Chamado quando um item é enviado.
```java
@EventHandler
public void onItemDeliveryEvent(ItemDeliveryEvent itemDeliveryEvent){
    QueueItem queueItem = itemDeliveryEvent.getQueueItem();
    //Your code;
}
```
