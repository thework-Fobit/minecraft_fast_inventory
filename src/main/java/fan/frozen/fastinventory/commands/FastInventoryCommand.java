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

public class FastInventoryCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;
        UserData userData = new UserData();
        if (args.length>=2){
            switch (args[0]){
                case "register": {
                    if (userData.getInventoryData(sender.getName())!=null){
                        //check if the inventory already exist, if it already existed, stop register process and remind player
                        if (!userData.getInventoryData(sender.getName()).containsKey(args[1])){
                            //it didn't exist, register it.
                            userData.registerNewInventory(player ,args[1]);
                            ChatInteractive.sendMsgToPlayer(player,"§a"+" register inventory "+args[1]+" success");
                        }else {
                            ChatInteractive.sendWarningMsgToPlayer(player," you have already owned an inventory called "+'"'+args[1]+'"'+" you can use "+'"'+"/fastInventory open "+args[1]+'"'+" to open it");
                        }
                    }else {
                        //if it can't find the player's profile, then it will create a new one and set it with the register parameter
                        userData.registerNewInventory(player ,args[1]);
                        ChatInteractive.sendMsgToPlayer(player,"§a"+" register inventory "+args[1]+" success");
                    }
                }
                break;
                case "open": {
                    HashMap<String, HashMap<Integer, ItemStack>> inventoryData = userData.getInventoryData(sender.getName());
                    HashMap<Integer, ItemStack> integerItemStackHashMap = inventoryData.get(args[1]);
                    //pull the data from the native user's profile
                    if (integerItemStackHashMap!=null){
                        //use native data to reconstruct a new inventory
                        Inventory inventory = Bukkit.createInventory(player,6*9,args[1]);
                        for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
                            inventory.setItem(integerItemStackEntry.getKey(),integerItemStackEntry.getValue());
                        }
                        player.openInventory(inventory);
                    }else {
                        //if it can't find the user's data then remind the command sender
                        ChatInteractive.sendWarningMsgToPlayer(player," can't find inventory named "+'"'+args[1]+'"'+" you can use command "+'"'+"/fastInventory register "+args[1]+'"'+" to create one");
                    }
                }
                break;
                case "delete":{
                    HashMap<String, HashMap<Integer, ItemStack>> inventoryData = userData.getInventoryData(sender.getName());
                    if (inventoryData!=null){
                        //check for deleting inventory existence, if it existed then delete it, if not warn the player
                        //later may add a function as a trash can, admin can find deleted inventory in the trash can and reset it
                        //later will add a function remind player to confirm when they are trying to delete an inventory
                        HashMap<Integer, ItemStack> integerItemStackHashMap = inventoryData.get(args[1]);
                        if (integerItemStackHashMap!=null){
                            inventoryData.remove(args[1],integerItemStackHashMap);
                            try {
                                userData.save(player,inventoryData);
                                ChatInteractive.sendMsgToPlayer(player,"§a success in deleting inventory "+args[1]);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }else {
                            ChatInteractive.sendWarningMsgToPlayer(player," can't find inventory named "+args[1]+" please check your command");
                        }
                    }
                }
            }
        }else if (args[0].equals("list")){
            HashMap<String, HashMap<Integer, ItemStack>> inventoryData = userData.getInventoryData(sender.getName());
            if (inventoryData!=null){
                //pull the native data and iterate the data map, get all the inventories' names and count them it then output to the player
                ArrayList<String> context = new ArrayList<>();
                for (Map.Entry<String,HashMap<Integer,ItemStack>> mapEntry:inventoryData.entrySet()){
                    context.add(mapEntry.getKey());
                }
                ChatInteractive.sendMsgToPlayer(player,"§a you have registered§5 "+context.size()+"§a inventory");
                ChatInteractive.sendWarpLineMsgToPlayer(player,context.toArray(new String[0]));
            }
        }else {
            //if users use other parameters, remind the user the parameter he uses was illegal
            ChatInteractive.sendWarningMsgToPlayer(player," Wrong parameter, please check you command!!!");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length==1){
            ArrayList<String> contexts = new ArrayList<>();
            contexts.add("register");
            contexts.add("open");
            contexts.add("delete");
            return contexts;
        }
        return null;
    }
}
