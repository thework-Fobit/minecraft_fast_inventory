package fan.frozen.fastinventory.commands;

import fan.frozen.fastinventory.FastInventory;
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

import java.util.*;

public class CheckInventoryCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        UserData userData = new UserData() ;
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (args.length>=2){
                if (player.hasPermission("fastInventory.permission.checkInventory")){
                    HashMap<String, HashMap<Integer, ItemStack>> inventoryData = userData.getInventoryData(args[0]);
                    Inventory inventory = Bukkit.createInventory(null,6*9,args[1]);
                    if (inventoryData!=null){
                        HashMap<Integer, ItemStack> integerItemStackHashMap = inventoryData.get(args[1]);
                        if (integerItemStackHashMap!=null){
                            for (Map.Entry<Integer,ItemStack> entry:integerItemStackHashMap.entrySet()){
                                inventory.setItem(entry.getKey(),entry.getValue());
                            }
                        }
                    }
                    player.openInventory(inventory);
                }else {
                    ChatInteractive.sendWarningMsgToPlayer(player," you don't have permission to use this command");
                }
            }else {
                ChatInteractive.sendWarningMsgToPlayer(player," null parameter, please check your command");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length==1){
            ArrayList<String> list = new ArrayList<>();
            Collection<? extends Player> onlinePlayers = FastInventory.getProvidingPlugin(FastInventory.class).getServer().getOnlinePlayers();
            Player[] players = onlinePlayers.toArray(new Player[0]);
            for (Player player : players) {
                list.add(player.getName());
            }
            return list;
        }
        if (args.length==2){
            ArrayList<String> list = new ArrayList<>();
            list.add("<inventoryName>");
            return list;
        }
        return null;
    }
}
