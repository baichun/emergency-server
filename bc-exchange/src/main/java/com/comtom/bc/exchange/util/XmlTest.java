package com.comtom.bc.exchange.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.comtom.bc.exchange.model.ebd.commom.Coverage;
import com.comtom.bc.exchange.model.ebd.commom.EBRAS;
import com.comtom.bc.exchange.model.ebd.commom.EBRBS;
import com.comtom.bc.exchange.model.ebd.commom.EBRDT;
import com.comtom.bc.exchange.model.ebd.commom.EBRPS;
import com.comtom.bc.exchange.model.ebd.commom.EBRST;
import com.comtom.bc.exchange.model.ebd.commom.Params;
import com.comtom.bc.exchange.model.ebd.details.other.EBM;
import com.comtom.bc.exchange.model.ebd.details.other.EBMBrdLog;
import com.comtom.bc.exchange.model.ebd.details.state.EBRDTState;
import com.comtom.bc.exchange.model.ebd.ebm.EBMBrdItem;
import com.comtom.bc.exchange.model.ebd.ebm.MsgBasicInfo;
import com.comtom.bc.exchange.model.ebd.ebm.MsgContent;
import com.comtom.bc.exchange.model.ebd.ebm.RelatedEBD;
import com.comtom.bc.exchange.model.ebd.ebm.ResBrdInfo;
import com.comtom.bc.exchange.model.ebd.ebm.ResBrdItem;
import com.comtom.bc.exchange.model.ebd.ebm.Unit;
import com.comtom.bc.exchange.model.ebd.ebm.UnitInfo;
import com.comtom.bc.exchange.model.signature.Signature;
import com.comtom.bc.exchange.model.signature.SignatureCert;
import com.thoughtworks.xstream.XStream;

public class XmlTest {
	public static void main(String[] args) {
//        testList();
//        testObject();
        testObject2();
	}
	
	
	private static void testList(){
		XStream xstream = new XStream ();
		Class<?>[] classes=new Class[]{EBRDTState.class,EBRDT.class};
		for (Class < ? > class1 : classes)
        {
            xstream.alias (class1.getSimpleName (), class1);
        }
        for (Class < ? > class1 : classes)
        {
            List <String> fields = getClassField (class1,xstream);
            setFieldesAlias (xstream, class1, fields, true);
        }		
		
		
		EBRDTState ebrdtState=new EBRDTState();
		List<EBRDT> eBRDTList=new ArrayList<EBRDT>();
		ebrdtState.setDataList(eBRDTList);
		
		EBRDT ebrdt=new EBRDT();
		ebrdt.setEBRID("eBRID");
		ebrdt.setRptTime("rptTime");
		ebrdt.setStateCode(1);
		ebrdt.setStateDesc("stateDesc");
		eBRDTList.add(ebrdt);
		
		ebrdt=new EBRDT();
		ebrdt.setEBRID("eBRID");
		ebrdt.setRptTime("rptTime");
		ebrdt.setStateCode(1);
		ebrdt.setStateDesc("stateDesc");
		eBRDTList.add(ebrdt);
		
		ebrdt=new EBRDT();
		ebrdt.setEBRID("eBRID");
		ebrdt.setRptTime("rptTime");
		ebrdt.setStateCode(1);
		ebrdt.setStateDesc("stateDesc");
		eBRDTList.add(ebrdt);
		
		String xml=xstream.toXML(ebrdtState);
		System.err.println(xml);		

	}
	
	private static void testObject(){
		XStream xstream = new XStream ();
		Class<?>[] classes=new Class[]{Signature.class,RelatedEBD.class,SignatureCert.class};
		for (Class < ? > class1 : classes)
        {
            xstream.alias (class1.getSimpleName (), class1);
        }
        for (Class < ? > class1 : classes)
        {
            List <String> fields = getClassField (class1,xstream);
            setFieldesAlias (xstream, class1, fields, true);
        }		
		
        
		Signature signature=new Signature();
		signature.setDigestAlgorithm("SM3");
		RelatedEBD relatedEBD=new RelatedEBD();
		relatedEBD.setEBDID("00432345454");
		signature.setRelatedEBD(relatedEBD);
		signature.setSignatureAlgorithm("SM2");
		SignatureCert signatureCert=new SignatureCert();
		signatureCert.setCertSN("certSN");
		signatureCert.setCertType("certType");
		signatureCert.setIssuerID("issuerID");
		signature.setSignatureCert(signatureCert);
		signature.setSignatureTime("3A4F98");
		signature.setSignatureValue("AAAABBBBHHHH");
		
		String xml=xstream.toXML(signature);
		System.err.println(xml);
	}
	
	
	private static void testObject2(){
		XStream xstream = new XStream ();
		Class<?>[] classes=new Class[]{
				EBMBrdLog.class,EBMBrdItem.class,EBM.class,EBRBS.class,
				MsgContent.class,MsgBasicInfo.class,Coverage.class,
				ResBrdInfo.class,ResBrdItem.class,EBRPS.class};
		for (Class < ? > class1 : classes)
        {
            xstream.alias (class1.getSimpleName (), class1);
        }
        for (Class < ? > class1 : classes)
        {
            List <String> fields = getClassField (class1,xstream);
            setFieldesAlias (xstream, class1, fields, true);
        }		
		
        ResBrdItem brdItem=new ResBrdItem();
        EBRAS ebras=new EBRAS();
        ebras.setEBRID("ebras");
		brdItem.setEBRAS(ebras);
		
        EBRBS ebrbs=new EBRBS();
        ebrbs.setBrdStateCode(1);
        ebrbs.setBrdStateDesc("brdStateDesc");
        ebrbs.setBrdSysInfo("brdSysInfo");
      //  ebrbs.setBrdSysType("brdSysType");
        ebrbs.setEndTime("endTime");
        ebrbs.setFileURL("fileURL");
        ebrbs.setRptTime("rptTime");
        ebrbs.setStartTime("startTime");
		List<EBRBS> ebrbsList=new ArrayList<EBRBS>();
		ebrbsList.add(ebrbs);
		brdItem.setDataList(ebrbsList);

		EBRPS ebrps=new EBRPS();
        ebrps.setEBRID("eBRID");
		brdItem.setEBRPS(ebrps);
		
        EBRST ebrst=new EBRST();
        ebrst.setEBRID("eBRID");
		brdItem.setEBRST(ebrst);
        
		ResBrdInfo brdInfo=new ResBrdInfo();
		List<ResBrdItem> dataList=new ArrayList<ResBrdItem>();
		dataList.add(brdItem);
		brdInfo.setDataList(dataList);
		
		
		EBMBrdLog ebmBrdLog=new EBMBrdLog();
		List<EBMBrdItem> dataList0=new ArrayList<EBMBrdItem>();
		ebmBrdLog.setDataList(dataList0);
		Params params=new Params();
		params.setRptEndTime("rptEndTime");
		params.setRptStartTime("rptStartTime");
		ebmBrdLog.setParams(params);
		
		
		MsgContent msgContent=new MsgContent();
		msgContent.setAreaCode("areaCode");
		msgContent.setLanguageCode("languageCode");
		msgContent.setMsgDesc("msgDesc");
		msgContent.setMsgTitle("msgTitle");
		msgContent.setProgramNum(null);
		
		
		EBM eBM=new EBM();
		eBM.setEBMID("eBMID");
		MsgBasicInfo msgBasicInfo=new MsgBasicInfo();
		msgBasicInfo.setEndTime("endTime");
		msgBasicInfo.setEventType("eventType");
		msgBasicInfo.setMsgType(1);
		msgBasicInfo.setSenderCode("senderCode");
		msgBasicInfo.setSenderName("senderName");
		msgBasicInfo.setSeverity(3);
		msgBasicInfo.setStartTime("startTime");
		eBM.setMsgBasicInfo(msgBasicInfo);
		eBM.setMsgContent(msgContent);
		
		EBMBrdItem ebmBrdItem=new EBMBrdItem();
		ebmBrdItem.setBrdStateCode(1);
		ebmBrdItem.setBrdStateDesc("等待播发（未到播发时间）");
		
		Coverage coverage=new Coverage();
		coverage.setAreaCode("areaCode");
		coverage.setCoveragePercent(0.28);
		ebmBrdItem.setCoverage(coverage);
		ebmBrdItem.setEBM(eBM);

		ebmBrdItem.setResBrdInfo(brdInfo);
		
		UnitInfo unitInfo=new UnitInfo();
		List<Unit> dataList1=new ArrayList<Unit>();
		Unit unit=new Unit();
		unit.setEBRPS(ebrps);
		unit.setPersonID("persionID");
		unit.setPersonName("persionName");
		unit.setUnitId("unitId");
		unit.setUnitName("unitName");
		
		unitInfo.setDataList(dataList1);
		
		ebmBrdItem.setUnitInfo(unitInfo);
		dataList0.add(ebmBrdItem);

		
		
		String xml=xstream.toXML(ebmBrdLog);
		System.err.println(xml);  
		
		
		Object obj=xstream.fromXML(xml);
		System.err.println(obj);
	}
	
    /**
     * 获取类的属性名称结合
     * 
     * @param c
     * @return
     */
    private static List <String> getClassField (Class < ? > c,XStream xstream)
    {
        List <String> result = innerGetClassField(c,xstream);
        return result;
    }
    
    private static List <String> innerGetClassField (Class < ? > c,XStream xstream)
    {
    	Field[] fields = c.getDeclaredFields ();
        List <String> result = new ArrayList <String> ();
        if (ArrayUtils.isEmpty (fields))
        {
            return result;
        }
        for (int i = 0; i < fields.length; i++)
        {
            result.add (fields[i].getName ());
            Class<?> type=fields[i].getType();
            if(type.isAssignableFrom(List.class)){
            	xstream.addImplicitCollection(c, fields[i].getName ());
            }
        }
        Class < ? > superClass = c.getSuperclass ();
        if (!superClass.equals (Object.class))
        {
            result.addAll (innerGetClassField (superClass,xstream));
        }
        return result;
    }
    
    /**
     * 将属性的前缀去掉。例如Class A 有属性 m_name，前缀为m_,则替换A属性m_nane为name<br>
     * 
     * @param xstream
     * @param c
     * @param fieldes
     * @param fieldPre
     * @param initcap 是否首字母大写
     */
    private static void setFieldesAlias (XStream xstream, Class < ? > c, List <String> fields, boolean initcap)
    {
        if (fields == null || fields.isEmpty ())
        {
            return;
        }
        if (c == null)
        {
            return;
        }
        for (String fieldName : fields)
        {
            if (StringUtils.isBlank (fieldName))
            {
                continue;
            }
            String alias = fieldName;
            if (initcap)
            {
                alias = StringUtils.capitalize (alias);
            }
            xstream.aliasField (alias, c, fieldName);
        }
    }
}
