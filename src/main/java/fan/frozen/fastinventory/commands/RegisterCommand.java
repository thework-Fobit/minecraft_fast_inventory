package fan.frozen.fastinventory.commands;

import fan.frozen.fastinventory.FastInventory;
import fan.frozen.fastinventory.interactive.ChatInteractive;
import fan.frozen.fastinventory.user.UserData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RegisterCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        UserData userData = new UserData() ;
            if (sender instanceof Player){
                Player player = (Player) sender;
                if (args.length>=1){
                    if (!isUserHaveMaxCountInventory(player)||player.hasPermission("fastInventory.permission.unlimitedInventory")) {
                        if (userData.getInventoryData(sender.getName())!=null){
                            //check if the inventory already exist, if it already existed, stop register process and remind player
                            if (!userData.getInventoryData(sender.getName()).containsKey(args[0])){
                                //it didn't exist, register it.
                                userData.registerNewInventory(player ,args[0]);
                                ChatInteractive.sendMsgToPlayer(player,"§a"+" register inventory "+args[0]+" success");
                            }else {
                                ChatInteractive.sendWarningMsgToPlayer(player," you have already owned an inventory called "+'"'+args[0]+'"'+" you can use "+'"'+"/fivopen "+args[0]+'"'+" to open it");
                            }
                        }else {
                            //if it can't find the player's profile, then it will create a new one and set it with the register parameter
                            userData.registerNewInventory(player ,args[0]);
                            ChatInteractive.sendMsgToPlayer(player,"§a"+" register inventory "+args[0]+" success");
                        }
                    }else {
                        ChatInteractive.sendWarningMsgToPlayer(player," you have reach the maximize count of inventory, you can use command /FIVDelete <name> to delete some existed inventory");
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
            ArrayList<String> name = new ArrayList<>();
            name.add("<name>");
            return name;
        }
        return null;
    }
    public boolean isUserHaveMaxCountInventory(Player player){
        String name = player.getName();
        UserData data = new UserData();
        int size = 0;
        if (data.getInventoryData(name)!=null) {
            size = data.getInventoryData(name).keySet().size();
        }
        int maxSize = FastInventory.config.getMaxInventoryCount();
        if (maxSize<0){
            return false;
        }else {
            return size>maxSize;
        }
    }
}
