package com.github.eokasta.backup.ftp;

import java.io.File;

public interface FTPProvider {

    String getServer();
    int getPort();
    String getUser();
    String getPass();
    File getFileToUpload();

}
