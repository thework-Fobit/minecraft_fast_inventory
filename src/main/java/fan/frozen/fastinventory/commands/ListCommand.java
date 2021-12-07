package fan.frozen.fastinventory.commands;

import fan.frozen.fastinventory.interactive.ChatInteractive;
import fan.frozen.fastinventory.user.UserData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class ListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        UserData userData = new UserData();
            if (sender instanceof Player){
                Player player = (Player)sender;
                    HashMap<String, HashMap<Integer, ItemStack>> inventoryData = userData.getInventoryData(sender.getName());
                    if (inventoryData!=null){
                        //pull the native data and iterate the data map, get all the inventories' names and count them it then output to the player
                        ArrayList<String> context = userData.getAllInventoryList(sender.getName());
                        ChatInteractive.sendMsgToPlayer(player,"§a you have registered§5 "+context.size()+"§a inventory");
                        ChatInteractive.sendWarpLineMsgToPlayer(player,context.toArray(new String[0]));
                    }else {
                        ChatInteractive.sendWarningMsgToPlayer(player," you haven't registered any inventory, you can use command /fivregister <name> to register a new one");
                    }
            }
        return false;
    }
}
