package com.github.eokasta.backup.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;

public class FTPManager implements FTPProvider {

    private String server;
    private int port;
    private String user;
    private String pass;
    private File fileToUpload;

    public FTPManager(File fileToUpload, String server, int port, String user, String pass) {

        this.fileToUpload = fileToUpload;
        this.server = server;
        this.port = port;
        this.user = user;
        this.pass = pass;

    }

    public void upload() {
        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // APPROACH #1: uploads first file using an InputStream

            String firstRemoteFile = fileToUpload.getName();
            InputStream inputStream = new FileInputStream(fileToUpload);

            System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The first file is uploaded successfully.");
            }

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (Exception e) {}
        }
    }


    @Override
    public String getServer() { return server; }

    @Override
    public int getPort() { return port; }

    @Override
    public String getUser() { return user; }

    @Override
    public String getPass() { return pass; }

    @Override
    public File getFileToUpload() { return fileToUpload; }
}
