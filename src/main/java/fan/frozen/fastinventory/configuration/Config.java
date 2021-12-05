package fan.frozen.fastinventory.configuration;

import fan.frozen.fastinventory.api.FileConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Objects;

public class Config implements FileConfig {
    private FileConfiguration configuration;
    public Config(){
        initialize();//initialize config file
    }
    public void initialize(){
        try {
            fileCheck();//check if file is absent
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void fileCheck() throws Exception{
        if (!configurationFile.exists()){
                boolean newFile = configurationFile.createNewFile();//if it can't detect exist config, create a new config file and fill it with default parameter
                if (!newFile){
                    throw new Exception("can't create new configuration file");
                }else {
                    System.out.println("can't find config file, and have created a new one.");
                    configuration = YamlConfiguration.loadConfiguration(configurationFile);
                    setDefaultValue();//set default parameter to new config file
                    reloadConfig();//reload config file from native file
                }
        }else {
            configuration = YamlConfiguration.loadConfiguration(configurationFile);//if it detected an existed config file, it will direct load it from native.
        }
    }
    public void setDefaultValue() throws Exception{
        configuration.set("userDataLocation",userDataLocation.getAbsolutePath());
        configuration.set("pluginVersion",pluginVersion);
        configuration.save(configurationFile);
    }
    public File getUserDataLocationFromConfig(){
        return new File(Objects.requireNonNull(configuration.getString("userDataLocation")));//get config info
    }
    public void reloadConfig(){
        try {
            if (configurationFile.exists()){
                configuration = YamlConfiguration.loadConfiguration(configurationFile);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
