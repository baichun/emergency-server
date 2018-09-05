package com.comtom.bc.server.service.impl;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.utils.DateUtil;
import com.comtom.bc.server.service.DBBackupService;
import com.comtom.bc.server.service.ParamService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class DBBackupServiceImpl implements DBBackupService {
    private  String hostIp;

    private  String user;

    private  String password;

    private  String savePath;

    private  String databaseName;

    private  String cmdPath;

    @Autowired
    private ParamService paramService;

    @Override
    public boolean backup(String fileName) {
        hostIp = paramService.findValueByKey(Constants.BACKUP_HOST_KEY);
        user = paramService.findValueByKey(Constants.BACKUP_USER_KEY);
        password = paramService.findValueByKey(Constants.BACKUP_PASSWORD_KEY);
        savePath = paramService.findValueByKey(Constants.BACKUP_FILE_PATH_KEY);
        databaseName = paramService.findValueByKey(Constants.BACKUP_DATABASE_NAME_KEY);
        cmdPath = paramService.findValueByKey(Constants.BACKUP_CMD_PATH_KEY);
        
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {// 如果目录不存在
            saveFile.mkdirs();// 创建文件夹
        }
       /* if (!savePath.endsWith(File.separator)) {
            savePath = savePath + File.separator;
        }*/
        if(StringUtils.isBlank(fileName)){
            fileName = "dbBackup_"+DateUtil.getDate()+".sql";
        }else {
            fileName += ".sql";
        }
        StringBuilder stringBuilder = new StringBuilder(cmdPath);
        stringBuilder.append("mysqldump");
        stringBuilder.append(" --host=");
        stringBuilder.append(hostIp);
        stringBuilder.append(" --user=").append(user);
        stringBuilder.append(" --password=").append(password);
        stringBuilder.append(" --opt");
        // stringBuilder.append(" --lock-all-tables=true");
        stringBuilder.append(" --default-character-set=utf8 ");
        stringBuilder.append(" "+databaseName+">");
        stringBuilder.append(" "+savePath+"\""+fileName+"\"");
        System.out.println(stringBuilder.toString());
        try {
            Process process = Runtime.getRuntime().exec(new String[] { "sh", "-c", stringBuilder.toString() });

            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            String line = null;
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            int exitVal = process.waitFor();
            System.out.println("Exited with error code " + exitVal);

            if (process.waitFor() == 0) {// 0 表示线程正常终止。
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean createDatabase( String database) {
        hostIp = paramService.findValueByKey(Constants.BACKUP_HOST_KEY);
        user = paramService.findValueByKey(Constants.BACKUP_USER_KEY);
        password = paramService.findValueByKey(Constants.BACKUP_PASSWORD_KEY);
        savePath = paramService.findValueByKey(Constants.BACKUP_FILE_PATH_KEY);
        databaseName = paramService.findValueByKey(Constants.BACKUP_DATABASE_NAME_KEY);
        cmdPath = paramService.findValueByKey(Constants.BACKUP_CMD_PATH_KEY);
        
        StringBuffer stringBuffer = new StringBuffer(cmdPath);
        stringBuffer.append("mysqladmin");
        stringBuffer.append(" -h"+hostIp);
        stringBuffer.append(" -u"+user);
        stringBuffer.append(" -p"+password);
        stringBuffer.append(" create ");
        stringBuffer.append(database);
        stringBuffer.append(" --default-character-set=utf8 ");
        try {
          //  Process process = Runtime.getRuntime().exec(stringBuffer.toString());
            Process process = Runtime.getRuntime().exec(new String[] { "sh", "-c", stringBuffer.toString() });
            if(process.waitFor() == 0){
                return true;
            }
            /*BufferedReader input2 = new BufferedReader(new InputStreamReader(process2.getInputStream(), "UTF-8"));
            String line2 = null;
            while ((line2 = input2.readLine()) != null) {
                System.out.println(line2);
            }
            int exitVal2 =  process2.waitFor();
            System.out.println("Exited with error code " + exitVal2);*/

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public boolean restore(String filePath) {
        try {
            hostIp = paramService.findValueByKey(Constants.BACKUP_HOST_KEY);
            user = paramService.findValueByKey(Constants.BACKUP_USER_KEY);
            password = paramService.findValueByKey(Constants.BACKUP_PASSWORD_KEY);
            savePath = paramService.findValueByKey(Constants.BACKUP_FILE_PATH_KEY);
            databaseName = paramService.findValueByKey(Constants.BACKUP_DATABASE_NAME_KEY);
            cmdPath = paramService.findValueByKey(Constants.BACKUP_CMD_PATH_KEY);
            
            StringBuffer stringBuffer = new StringBuffer(cmdPath);
            stringBuffer.append("mysql");
            stringBuffer.append(" -h"+hostIp);
            stringBuffer.append(" -u"+user);
            stringBuffer.append(" -p"+password);
            stringBuffer.append(" --default-character-set=utf8 ");
            stringBuffer.append(databaseName);
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(new String[] { "sh", "-c", stringBuffer.toString() });
            OutputStream outputStream = process.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream(filePath), "UTF-8"));

            String str = null;
            StringBuffer sb = new StringBuffer();
            while ((str = br.readLine()) != null) {
                sb.append(str + "\r\n");
            }
            str = sb.toString();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream,"utf-8");
            writer.write(str);
            writer.flush();
            outputStream.close();
            br.close();
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
