package fan.frozen.fastinventory;

import fan.frozen.fastinventory.api.FileConfig;
import fan.frozen.fastinventory.commands.FastInventoryCommand;
import fan.frozen.fastinventory.configuration.Config;
import fan.frozen.fastinventory.event.CloseInventoryEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
/**
    @author thework
    @version alpha 1.1
 */

public final class FastInventory extends JavaPlugin {
    public static Config config;
    @Override
    public void onEnable() {
        // Plugin startup logic
        config = new Config();
        System.out.println("[fastInventory]:initializing plugin....");
        System.out.println("[fastInventory]:initialize complete");
        System.out.println("[fastInventory]:plugin version:"+ FileConfig.pluginVersion);
        Objects.requireNonNull(getCommand("fastInventory")).setExecutor(new FastInventoryCommand());//register command
        getServer().getPluginManager().registerEvents(new CloseInventoryEvent(),this);//register event
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
