package fan.frozen.fastinventory.commands;

import fan.frozen.fastinventory.interactive.ChatInteractive;
import fan.frozen.fastinventory.user.UserData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        UserData userData = new UserData();
            if (sender instanceof Player){
                Player player = (Player)sender;
                if (args.length>=1){
                    HashMap<String, HashMap<Integer, ItemStack>> inventoryData = userData.getInventoryData(sender.getName());
                    HashMap<Integer, ItemStack> integerItemStackHashMap = inventoryData.get(args[0]);
                    //pull the data from the native user's profile
                    if (integerItemStackHashMap!=null){
                        //use native data to reconstruct a new inventory
                        Inventory inventory = Bukkit.createInventory(player,6*9,args[0]);
                        for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
                            inventory.setItem(integerItemStackEntry.getKey(),integerItemStackEntry.getValue());
                        }
                        player.openInventory(inventory);
                    }else {
                        //if it can't find the user's data then remind the command sender
                        ChatInteractive.sendWarningMsgToPlayer(player," can't find inventory named "+'"'+args[0]+'"'+" you can use command "+'"'+"/FIVRegister "+args[0]+'"'+" to create one");
                    }
                }else {
                    ChatInteractive.sendWarningMsgToPlayer(player," null parameter, please check your command");
                }
            }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length>=1){
            return new UserData().getAllInventoryList(sender.getName());
        }
        return null;
    }
}
