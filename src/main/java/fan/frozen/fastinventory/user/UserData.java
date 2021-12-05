package fan.frozen.fastinventory.user;

import fan.frozen.fastinventory.FastInventory;
import fan.frozen.fastinventory.api.FileConfig;
import fan.frozen.fastinventory.configuration.Config;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class UserData implements FileConfig {
    private final Config config;
    private FileConfiguration userData;
    public UserData(){
        //load config from static object
        config = FastInventory.config;
        //the rest part has same working process as config part, you can read config part's comment to get information
        initialize();
    }

    @Override
    public void initialize() {
        try {
            fileCheck();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void fileCheck() throws Exception {
        if (!config.getUserDataLocationFromConfig().exists()){
            boolean success = config.getUserDataLocationFromConfig().createNewFile();
            if (!success){
                throw new Exception("can't create new userdata File");
            }else {
                System.out.println("can't find userdata file, and have created a new one.");
                userData = YamlConfiguration.loadConfiguration(config.getUserDataLocationFromConfig());
                setDefaultValue();
                reloadUserData();
            }
        }else {
            userData = YamlConfiguration.loadConfiguration(config.getUserDataLocationFromConfig());
        }
    }

    @Override
    public void setDefaultValue() throws Exception {
        userData.set("allUserData"," ");
        userData.save(config.getUserDataLocationFromConfig());
    }
    public void reloadUserData(){
        try {
            userData = YamlConfiguration.loadConfiguration(config.getUserDataLocationFromConfig());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public HashMap<String, HashMap<Integer,ItemStack>> getInventoryData(String userName){
        @SuppressWarnings("all")
        ArrayList<HashMap<String,HashMap<Integer,ItemStack>>> data = (ArrayList<HashMap<String,HashMap<Integer,ItemStack>>>)userData.getList("allUserData"+"."+userName);
        //cast List direct to hashmap with ArrayList (could do this step with reflection, but seems java reflect can't recognize bukkit ItemStack?)
        if (data==null){
            return null;
            //if it can't get user data from native just return null
        }
        return data.get(0);
    }
    public void registerNewInventory(Player player,String inventoryName){
        Inventory inventory = Bukkit.createInventory(null,6*9,inventoryName);
        //register a new inventory with no owner
        try {
            saveInventory(player,inventory,inventoryName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void saveInventory(Player player,Inventory inventory,String inventoryName) throws Exception{
        HashMap<Integer,ItemStack> partDownInventory = new HashMap<>();
        HashMap<String, HashMap<Integer, ItemStack>> inventoryData = getInventoryData(inventoryName);
        //save inventory method, first treat inventory apart to a hashmap with the item as the value, and item's location as the key
        if (inventoryData == null) {
            inventoryData = new HashMap<>();
        }
        for (int i = 0; i < inventory.getSize(); i++) {
            partDownInventory.put(i,inventory.getItem(i));
        }
        //then put the previous hashmap into another map, with inventory name as the key
        inventoryData.put(inventoryName,partDownInventory);
        ArrayList<HashMap<String,HashMap<Integer,ItemStack>>> data = new ArrayList<>();
        //at last, put HashMap<InventoryName,InventoryHashMap> into an arraylist and output it into yml file
        //this place we use list<HashMap<?,?>> because when bukkit read yml file, it can only read list or MapList, and why we don't use get object then reflect it into HashMap<?,?>, just read the previous comment
        data.add(inventoryData);
        userData.set("allUserData"+"."+player.getName(),data);
        userData.save(config.getUserDataLocationFromConfig());
        reloadUserData();
    }
}
