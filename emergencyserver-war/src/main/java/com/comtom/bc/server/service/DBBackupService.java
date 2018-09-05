package com.comtom.bc.server.service;

public interface DBBackupService {

    public boolean backup( String fileName);

    public  boolean createDatabase(String database);

    public  boolean restore(String filePath);

}
