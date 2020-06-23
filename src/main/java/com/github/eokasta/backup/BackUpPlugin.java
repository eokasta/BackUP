package com.github.eokasta.backup;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public class BackUpPlugin extends JavaPlugin {

    private File masterFolder, backupFolder;
    private String backUpFileName;

    private BackUp backUp;

    @Override
    public void onEnable() {
        this.backUp = new BackUp(this);

        /*
         Test
         */
        getLogger().log(Level.INFO, "Starting Backup in 20 seconds.");
        backUp.backupServer();
    }

    @Override
    public void onDisable() { }

    public File getMasterFolder() {
        return masterFolder;
    }

    public void setMasterFolder(File masterFolder) {
        this.masterFolder = masterFolder;
    }

    public File getBackupFolder() {
        return backupFolder;
    }

    public void setBackupFolder(File backupFolder) {
        this.backupFolder = backupFolder;
    }

    public String getBackUpFileName() {
        return backUpFileName;
    }

    public void setBackUpFileName(String backUpFileName) {
        this.backUpFileName = backUpFileName;
    }

    public BackUp getBackUp() {
        return backUp;
    }
}
