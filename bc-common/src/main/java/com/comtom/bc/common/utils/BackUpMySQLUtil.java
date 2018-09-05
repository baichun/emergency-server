package com.comtom.bc.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackUpMySQLUtil {

    private static final String BASE_PATH_LINUX = "/root/";
    private static final String BASE_PATH_WINDEWS = "E:\\";
    public void  backwindows() {
        try {
            String sqlname = BASE_PATH_WINDEWS + "shequ."
                    + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date())
                    + ".sql";
            String mysql = "mysqldump --user=root --password=jwl --opt shequ> "
                    + sqlname;
            Runtime.getRuntime().exec("cmd /c " + mysql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static  void backlinux() {
        try {
            String sqlname = BASE_PATH_LINUX + "mqney.sql";
            String mysql = "mysqldump --host=192.168.7.99 --user=comtom --password=123456 --opt ewbs_db> "
                    + sqlname;
            System.out.println(mysql);
            Process process = Runtime.getRuntime().exec(
                    new String[] { "sh", "-c", mysql });
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            String line = null;
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            int exitVal = process.waitFor();
            System.out.println("Exited with error code " + exitVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        // new BackUpMySQL().backwindows();
        System.out.println(System.getProperty("os.name"));
    }
}
