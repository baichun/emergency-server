package com.comtom.bc.server.service;

public interface ResIDGeneratorService {
  /**
   * 根据资源类型，资源子类型和区域码生成资源ID
   * 
   * @param ebrType
   * @param ebrSubType
   * @param areaCode
   * @return
   */
  public String generateResourceID(String ebrType, String ebrSubType, String areaCode);
  
  /**
   * 资源信息删除，删除资源ID信息
   * 
   * @param resourceIds
   */
  public void delResourceIdInfo(String[] resourceIds);
  
  /**
   * 按照被使用的资源ID修订ID使用记录
   * 
   * @param resourceIds
   */
  public void updateResourceIdInfo(String[] resourceIds);
}
