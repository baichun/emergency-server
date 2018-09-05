package com.comtom.bc.server.service.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.comtom.bc.server.repository.dao.RegionAreaDAO;
import com.comtom.bc.server.repository.entity.RegionArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comtom.bc.server.repository.dao.EbrResIDRecordDAO;
import com.comtom.bc.server.repository.entity.EbrResourceIDRecord;
import com.comtom.bc.server.service.ResIDGeneratorService;
import com.comtom.bc.server.service.base.BaseService;

@Service
public class ResIDGeneratorServiceImpl extends BaseService implements ResIDGeneratorService {
    @Autowired
    EbrResIDRecordDAO residRecordDAO;

    @Autowired
	RegionAreaDAO regionAreaDao;
    
	private static final int MAX_EXTEND_ID_SPACE = 99;
    
	public String generateResourceID(String ebrType, String ebrSubType, String areaCode) {
		RegionArea regionArea=regionAreaDao.findSubAreaByCode(areaCode);
		if(regionArea == null){
			return null;
		}
		Integer areaLevel = regionArea.getAreaLevel() + 1;  // 区域级别

		StringBuffer residKeyBuf = new StringBuffer();
		residKeyBuf.append(areaLevel).append(fillAreaCode(areaCode));
		residKeyBuf.append(ebrType);

		String resType = residKeyBuf.toString();
		String subResType = residKeyBuf.toString() + ebrSubType;

		EbrResourceIDRecord found = residRecordDAO.findOne(resType);
		EbrResourceIDRecord subFound = residRecordDAO.findOne(subResType);

		String resTypeSeq = saveResRecord(found, resType);
		String resSubTypeSeq = saveResRecord(subFound, subResType);
		if (StringUtils.isEmpty(resTypeSeq) && StringUtils.isEmpty(resTypeSeq)) {
			return null;
		}
		return resType + resTypeSeq +  ebrSubType + resSubTypeSeq; ///资源级别+地区编码+资源类型码+资源子类型码
	}

	private String saveResRecord(EbrResourceIDRecord found, String type) {
		final int seq_size = 2;
		String seqStr = null;
		if (null == found) {
			EbrResourceIDRecord newResid = new EbrResourceIDRecord();
			newResid.setResourceType(type + "01");
			newResid.setIdrecord("01");
			residRecordDAO.save(newResid);
			return newResid.getIdrecord();
		} else {
			if (Integer.parseInt(found.getIdrecord()) < MAX_EXTEND_ID_SPACE) { //小于 99
				EbrResourceIDRecord newResid = new EbrResourceIDRecord();
				newResid.setResourceType(type);
				String idRecord = Integer.parseInt(found.getIdrecord()) + 1 + StringUtils.EMPTY;
				seqStr = new StringBuffer(StringUtils.repeat("0", seq_size - idRecord.length())).append(idRecord).toString();
				newResid.setIdrecord(seqStr);
				residRecordDAO.save(newResid);
			}
		}
		return seqStr;
	}
	
	/**
	 * 将区域编码补齐12位
	 * @param areaCode
	 * @return
	 */
	private final String fillAreaCode(String areaCode){
		int length=areaCode.length();
		if(length==12){
			return areaCode;
		}
		StringBuffer buffer=new StringBuffer(areaCode);
		for (int i = 0; i < 12-length; i++) {
			buffer.append("0");
		}
		return buffer.toString();
	}
	
	/**
	 * 将扩展码按照最大取值格式取固定长度， 左边补零
	 * @param extendId
	 * @return
	 */
	private final String fillExtendID(int extendId) {
		int targetLength = String.valueOf(MAX_EXTEND_ID_SPACE).length();	
	    NumberFormat nf = NumberFormat.getInstance();  
		nf.setGroupingUsed(false);  
		nf.setMaximumIntegerDigits(targetLength);  
		nf.setMinimumIntegerDigits(targetLength); 
		return nf.format(extendId);
	}

	public void delResourceIdInfo(String[] resourceIds) {
	    //找出（资源类型，资源子类型，区域码）不重复的组合集
	    List<String> residKeys = findResidKeys(resourceIds);
	    if(null == residKeys || residKeys.size() < 1) {
	    	return;
	    }
	    
	    //查出数据库中的资源ID使用记录
	    List<EbrResourceIDRecord> found = residRecordDAO.findAll(residKeys);
	    if(null == found || found.size() < 1) {
		   return;
	    }
	   
	    //已删除的资源ID使用记录置零
	   List<EbrResourceIDRecord> idRcd2Save = new ArrayList<EbrResourceIDRecord>();
	   for(EbrResourceIDRecord fnd : found) {
		   List<Integer> zeroPos = findPosition(fnd.getResourceType(), resourceIds);
		   char[] idRecordArr = fnd.getIdrecord().toCharArray();
		   for(Integer pos : zeroPos) {
				if(pos > 0 && pos <= idRecordArr.length) {
					idRecordArr[pos-1] = '0';
				}
		   }
		   fnd.setIdrecord(String.valueOf(idRecordArr));
		   idRcd2Save.add(fnd);
	   }
	   
	   residRecordDAO.save(idRcd2Save);
	   
	}
	
	public void updateResourceIdInfo(String[] resourceIds) {

		if(null == resourceIds || resourceIds.length < 1) {
			return ;
		}

		for(String resid : resourceIds) {
			if(null != resid) {
				String resType=resid.substring(0,17); //资源级别+地区编码+资源类型码
				String subType=resType+resid.substring(19,21); //资源级别+地区编码+资源类型码+资源子类型码
				EbrResourceIDRecord found = residRecordDAO.findOne(resType);
				EbrResourceIDRecord subFound = residRecordDAO.findOne(subType);
				String resTypeSeq = saveResRecord(found, resType);
				String resSubTypeSeq = saveResRecord(subFound, subType);

			}
		}


	}
	
	private final EbrResourceIDRecord getByKey(String residKey, List<EbrResourceIDRecord> exist) {
		EbrResourceIDRecord found = null;
		for(EbrResourceIDRecord idrcd : exist) {
			if(idrcd.getResourceType().equals(residKey)) {
				found = idrcd;
				break;
			}
		}
		return found;
	}
	
	/**
	 * 根据ID使用记录和当前被使用的序号列表制作新的资源ID<br/>
	 * 如果ID使用记录为空，则创建；否则在ID使用记录基础上修改
	 * 
	 * @param idRecord, ID使用记录
	 * @param occupyPos, 当前被使用的序号列表
	 * @return
	 */
	private final String makeIdrecord(String idRecord, List<Integer> occupyPos) {
		Integer maxPos = -1;
		for(Integer pos : occupyPos) {
			if(null != pos && (pos.compareTo(maxPos) > 0)) {
				maxPos = pos;
			}
		}
		
		if(maxPos < 1) {
			return idRecord;
		}
		
		char[] idRecordArr = null;
		if(null == idRecord || idRecord.length() < 1 || idRecord.length() < maxPos) {
			idRecordArr = new char[maxPos];
			for(int i = 0; i < idRecordArr.length; i++) {
				idRecordArr[i] = '0';
			}
			
			if(null != idRecord && idRecord.length() > 0 && idRecord.length() < maxPos) {
				char[] orgIdRecord = idRecord.toCharArray();
				System.arraycopy(orgIdRecord, 0, idRecordArr, 0, orgIdRecord.length);
			}
			
		} else {
			idRecordArr = idRecord.toCharArray();
		}
		
		for(Integer pos : occupyPos) {
			if(pos > 0) {
				idRecordArr[pos-1] = '1';
			}
		}

		return String.valueOf(idRecordArr);
	}
	
	/**
	 * 找出（资源类型，资源子类型，区域码）不重复的组合集
	 * 
	 * @param resourceIds
	 * @return
	 */
	private final List<String> findResidKeys(String[] resourceIds) {
	   List<String> residKeys = new ArrayList<String>();
	   if(null == resourceIds || resourceIds.length < 1) {
		   return residKeys;
	   }

	   for(String resid : resourceIds) {
		   if(null != resid && resid.length() > 17 && !residKeys.contains(resid.substring(0, 16))) {
                residKeys.add(resid.substring(0, 16)); 
		   }
	   }

	   return residKeys;
	}
	
	/**
	 * 根据资源ID前缀（资源类型，资源子类型，区域码）找出其被使用的序号
	 * 
	 * @param residKey, 资源ID前缀（资源类型，资源子类型，区域码）
	 * @param resourceIds
	 * @return
	 */
	private final List<Integer> findPosition(String residKey, String[] resourceIds) {
		List<Integer> position = new ArrayList<Integer>();
		for(String resid : resourceIds) {
			if(null != resid && resid.startsWith(residKey)) {
				position.add(Integer.valueOf(resid.substring(16)));
			}
		}
		return position;
	}

}
