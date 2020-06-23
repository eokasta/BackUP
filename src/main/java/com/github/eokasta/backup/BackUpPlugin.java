package com.github.eokasta.backup;

import com.github.eokasta.backup.ftp.FTPManager;
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
        final File file = backUp.backupServer();
        if (file != null) {
            FTPManager ftp = new FTPManager(file,
                    "198.187.29.196",
                    21,
                    "lucastest@tchauhost.com",
                    "r7os2g6icd");
            ftp.upload();
        }
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
