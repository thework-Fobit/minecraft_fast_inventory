package fan.frozen.fastinventory.interactive;

import org.bukkit.entity.Player;

public class ChatInteractive {
    public static void sendMsgToPlayer(Player player,String context){
        player.sendMessage("§9[fastInventory]:"+context);
    }
    public static void sendWarningMsgToPlayer(Player player,String context){
        player.sendMessage("§9[fastInventory]:§c"+context);
    }
}
