package fan.frozen.fastinventory;

import fan.frozen.fastinventory.api.FileConfig;
import fan.frozen.fastinventory.commands.*;
import fan.frozen.fastinventory.configuration.Config;
import fan.frozen.fastinventory.event.CloseInventoryEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
/**
    @author thework
    @version alpha 2.0
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
        Objects.requireNonNull(getCommand("fivregister")).setExecutor(new RegisterCommand());
        Objects.requireNonNull(getCommand("fivopen")).setExecutor(new OpenCommand());
        Objects.requireNonNull(getCommand("fivdelete")).setExecutor(new DeleteCommand());
        Objects.requireNonNull(getCommand("fivlist")).setExecutor(new ListCommand());
        Objects.requireNonNull(getCommand("fivcheck")).setExecutor(new CheckInventoryCommand());//register command
        getServer().getPluginManager().registerEvents(new CloseInventoryEvent(),this);//register event
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
