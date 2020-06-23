package com.github.eokasta.backup;

import com.github.eokasta.backup.utils.Helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class BackUp {

    private final BackUpPlugin plugin;

    private boolean inBackup;

    public BackUp(BackUpPlugin plugin) {
        this.plugin = plugin;
        plugin.setBackUpFileName("%s_%s");
        plugin.setMasterFolder(plugin.getDataFolder().getAbsoluteFile().getParentFile().getParentFile());

        final File file = new File(plugin.getMasterFolder().getAbsolutePath() + "/cache");
        file.mkdir();

        plugin.setBackupFolder(file);

    }

    public File backupServer() {
        if (inBackup) {
            plugin.getLogger().log(Level.INFO, "A backup is already being made, please wait.");
            return null;
        }

        plugin.getLogger().log(Level.INFO, "Backup is starting...");
        setInBackup(true);
        try {
            final String archiveName = String.format(
                    plugin.getBackUpFileName() + ".zip", plugin.getServer().getIp().replaceAll("\\.", ","), Helper.getDate(new Date())
            );
            final FileOutputStream fos = new FileOutputStream(plugin.getBackupFolder().getAbsolutePath() + "/" + archiveName);

            final ZipOutputStream zipOut = new ZipOutputStream(fos);
            final File fileToZip = new File(plugin.getMasterFolder().getAbsolutePath());

            zipFile(fileToZip, fileToZip.getName(), zipOut);
            zipOut.close();
            fos.close();

            plugin.getLogger().log(Level.INFO, String.format("A new Backup was created. (%s)", archiveName));
            return new File(plugin.getBackupFolder().getAbsolutePath() + "/" + archiveName);
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "A error occurred while backing up.");
            e.printStackTrace();
            return null;
        } finally {
            setInBackup(false);
        }
    }

    private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            plugin.getLogger().info(fileToZip.getName());
            if (fileToZip.getName().equals("cache")) return;

            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            final File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        final FileInputStream fis = new FileInputStream(fileToZip);
        final ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

    public void setInBackup(boolean b) {
        inBackup = b;
    }

    public boolean isInBackup() { return inBackup; }

}
