package fan.frozen.fastinventory.api;


import fan.frozen.fastinventory.FastInventory;

import java.io.File;

public interface FileConfig {
    File rootPath = fan.frozen.fastinventory.FastInventory.getProvidingPlugin(FastInventory.class).getDataFolder();
    File configurationFile = new File(rootPath,"fastInventory.yml");
    File userDataLocation = new File(rootPath,"userData.yml");
    String pluginVersion = "1.0-alpha";
    void initialize();
    void fileCheck() throws Exception;
    void setDefaultValue() throws Exception;
}
