package com.comtom.bc.common.utils;

import com.comtom.bc.common.Constants;

/**
 * @author wjd
 * @create 2018/5/22 0022
 * @desc 应急广播系统资源工具类
 **/
public class ResUtil {

    private static final  String RES_TYPE="0314";

    /**
     * 根据资源id判断是否为播出系统
     * @param bsEbrId
     * @return
     */
    public static boolean isEbrBS(String bsEbrId){
        if(CommonUtil.isNotEmpty(bsEbrId)){
            String resType = bsEbrId.substring(13, 17); //资源类型码 如： 0314
            String subType = bsEbrId.substring(19, 21); //资源子类型码 如：03
            if(RES_TYPE.equals(resType) && Constants.EBR_TYPE_BROADCAST.equals(subType)){
                return true;
            }
        }
        return false;
    }


    /**
     * 获取资源子类型码
     * @param bsEbrId
     * @return
     */
    public static String getResSubType(String bsEbrId) {
        if (CommonUtil.isNotEmpty(bsEbrId)) {
            return bsEbrId.substring(19, 21); //资源子类型码 如：01
        }
        return null;
    }
}
