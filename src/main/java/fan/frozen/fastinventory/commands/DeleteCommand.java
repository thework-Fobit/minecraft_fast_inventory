package fan.frozen.fastinventory.commands;

import fan.frozen.fastinventory.interactive.ChatInteractive;
import fan.frozen.fastinventory.user.UserData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        UserData userData = new UserData();
          if (sender instanceof Player){
              Player player = (Player)sender;
              HashMap<String, HashMap<Integer, ItemStack>> inventoryData = userData.getInventoryData(sender.getName());
              if (args.length>=1){
                  if (inventoryData!=null){
                      //check for deleting inventory existence, if it existed then delete it, if not warn the player
                      //later may add a function as a trash can, admin can find deleted inventory in the trash can and reset it
                      //later will add a function remind player to confirm when they are trying to delete an inventory
                      HashMap<Integer, ItemStack> integerItemStackHashMap = inventoryData.get(args[0]);
                      if (integerItemStackHashMap!=null){
                          inventoryData.remove(args[0],integerItemStackHashMap);
                          try {
                              userData.save(player,inventoryData);
                              ChatInteractive.sendMsgToPlayer(player,"§a success in deleting inventory "+args[0]);
                          }catch (Exception e){
                              e.printStackTrace();
                          }
                      }else {
                          ChatInteractive.sendWarningMsgToPlayer(player," can't find inventory named "+args[0]+" please check your command");
                      }
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
