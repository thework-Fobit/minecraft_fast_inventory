package fan.frozen.fastinventory.event;

import fan.frozen.fastinventory.user.UserData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class CloseInventoryEvent implements Listener {
    @EventHandler
    public void listenCloseInventory(InventoryCloseEvent event){
        //detect player close inventory, so it can save inventory
        UserData data = new UserData();
        HashMap<String, HashMap<Integer, ItemStack>> inventoryData = data.getInventoryData(event.getPlayer().getName());
        //find from native if the player was closing an existing fastInventory
        if (inventoryData!=null){
            if (inventoryData.containsKey(event.getView().getTitle())) {
                //compare the closing inventory's title with native saved one
                InventoryHolder holder = event.getInventory().getHolder();
                //get closing inventory's owner, if the player used /fastInventory open command to open this inventory, the owner shell be the player
                Player player = (Player)holder;
                assert player != null;
                if (player.getName().equals(event.getPlayer().getName())){
                    //if it confirmed that inventory's owner is the player who closed this inventory, then save this inventory into native user's data file
                    try {
                        data.saveInventory((Player) event.getPlayer(),event.getInventory(),event.getView().getTitle());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
