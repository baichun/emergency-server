import com.comtom.bc.common.utils.DateUtil;
import com.comtom.bc.common.utils.MathUtil;
import com.comtom.bc.exchange.commom.EBDRespResultEnum;
import com.comtom.bc.exchange.commom.EBRInfoType;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.*;
import com.comtom.bc.exchange.model.ebd.details.info.*;
import com.comtom.bc.exchange.model.ebd.details.other.*;
import com.comtom.bc.exchange.model.ebd.details.state.EBRASState;
import com.comtom.bc.exchange.model.ebd.details.state.EBRBSState;
import com.comtom.bc.exchange.model.ebd.details.state.EBRDTState;
import com.comtom.bc.exchange.model.ebd.details.state.EBRPSState;
import com.comtom.bc.exchange.model.ebd.ebm.*;
import com.comtom.bc.exchange.model.signature.Signature;
import com.comtom.bc.exchange.util.*;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 测试类
 */
public class HTTPClientHelpTest {

//	public static final String srcURL = "http://10.131.58.100:8090/Emergency/receive/ebd.htm";
//	public static final String srcURL = "http://127.0.0.1:7070/ewbsserver/exchange";
//	public static final String srcURL = "http://61.191.100.226:7070/ewbsserver/exchange";
	public static final String srcURL = "http://localhost:8080/ts/exchange.do";

	// 接收文件保存地址
	public static final String filePathResponse = "/varSHENG/lib/client/receive";

	// 发送文件路径
	public static final String filePathSend = "/varSHENG/lib/client/send";

	public static final String eBRID = "43415230000000314010301"; //psEbrId

	public static final Date date = new Date();
	public static final String dateString = DateTimeUtil.dateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS);

	public static void main(String[] args) throws IOException {

		// testCheck(); //心跳检验
		// testResonse(); // 处理结果通用反馈
		testEBM();  // 应急平台播发请求
		//	testEBMState(); //应急广播播发状态查询
		//	 testEBMStateResponse(); //应急广播播发状态反馈
		//	 testPsInfo();  //应急广播平台信息上报
		// testStInfo(); //台站信息上报
//		 testASInfo(); // 消息接收设备信息上报
//		 testBSInfo(); // 播出系统消息上报
//		 testDTInfo(); //平台设备及终端信息上报
//		 testBrdLog(); //播发记录上报
//		 testPsState(); //应急广播平台状态上报
//		 testAsState(); //消息接收设备信息状态上报
//		 testBSState(); //播出系统上报
//		 testDTState(); //平台设备及终端状态上报
		//	 testOMDRequest(EBDType.EBMBrdLog.name()); //运维数据请求，根据类型不同，发送的请求不同
	}

	public static  void testOMDRequest(String name) {
		OMDRequest omdRequest = new OMDRequest();
		omdRequest.setOMDType(name);
		 Params params=new Params();
		 params.setRptStartTime("2018-06-02 07:08:28");
		params.setRptEndTime("2018-06-24 20:08:30");
		params.setRptType("Incremental");
		omdRequest.setParams(params);

		String ebdIndex = getRandomInteger();
		EBDResponse ebdResponse = new EBDResponse();
		ebdResponse.setResultCode(EBDRespResultEnum.receivevalid.getCode());
		ebdResponse.setResultDesc(EBDRespResultEnum.receivevalid.getEbdResponseResult());
		EBD ebd = EBDModelBuild.buildOMDRequest(eBRID, srcURL, ebdIndex, omdRequest);
		send(ebd);
	}

	public static void testPsInfo() {
		EBRPSInfo infoObject = new EBRPSInfo();
		List<EBRPS> dataList = new ArrayList<EBRPS>();
		EBRPS object = new EBRPS();
		object.setRptTime(dateString);
		object.setRptType(EBRInfoType.Sync.name());
		// ebrps.setRelatedEBRPS(relatedEBRPS);
		object.setEBRID("010234152300000001");
		object.setEBRName("安徽省舒城县应急广播仿真测试平台");
		object.setAddress("安徽省舒城县");
		object.setContact("小苏");
		object.setPhoneNumber("15899000000");
		object.setLatitude("116.950794");
		object.setLongitude("31.467462");
		object.setURL("http://10.131.53.103:7070/ewbsserver/exchange"); //TODO 这里需要更改
		dataList.add(object);
		infoObject.setDataList(dataList);
		String ebdIndex = getRandomInteger();
		EBD ebd = EBDModelBuild.buildEBRPSInfo(eBRID, srcURL, ebdIndex, infoObject,null);
		send(ebd);
	}

	public static void testStInfo() {
		EBRSTInfo infoObject = new EBRSTInfo();
		List<EBRST> dataList = new ArrayList<EBRST>();
		EBRST object = new EBRST();
		object.setRptTime(dateString);
		object.setRptType(EBRInfoType.Sync.name());
		// ebrps.setRelatedEBRPS(relatedEBRPS);
		object.setEBRID("010234152300000001");
		object.setEBRName("安徽省舒城县调频台站");
		object.setAddress("新沛路");
		object.setContact("张三");
		object.setPhoneNumber("15888086433");
		object.setLatitude("116.950794");
		object.setLongitude("31.467462");
		dataList.add(object);
		infoObject.setDataList(dataList);
		String ebdIndex = getRandomInteger();
		EBD ebd = EBDModelBuild.buildEBRSTInfo(eBRID, srcURL, ebdIndex, infoObject,null);
		send(ebd);
	}

	public static void testASInfo() {
		EBRASInfo infoObject = new EBRASInfo();
		List<EBRAS> dataList = new ArrayList<EBRAS>();
		EBRAS object = new EBRAS();
		object.setRptTime(dateString);
		object.setRptType(EBRInfoType.Sync.name());
		// object.setRelatedEBRPS(relatedEBRPS);
		// object.setRelatedEBRST(relatedEBRST);
		object.setEBRID("040134152300000001");
		object.setEBRName("安徽省舒城县有线仿真系统消息适配设备");
		object.setLatitude("116.950794");
		object.setLongitude("31.467462");
		object.setURL("http://10.131.53.103:7070/ewbsserver/exchange");


		EBRAS obj = new EBRAS();
		obj.setRptTime(dateString);
		obj.setRptType(EBRInfoType.Sync.name());
		obj.setEBRID("040234152300000001");
		obj.setEBRName("安徽省舒城县应急广播调频台站消息适配设备");
		obj.setLatitude("116.945269");
		obj.setLongitude("34.767418");
		obj.setURL("http://10.131.53.103:7070/ewbsserver/exchange");

		dataList.add(object);
		dataList.add(obj);
		infoObject.setDataList(dataList);
		String ebdIndex = getRandomInteger();
		EBD ebd = EBDModelBuild.buildEBRASInfo(eBRID, srcURL, ebdIndex, infoObject,null);
		send(ebd);
	}

	public static void testBSInfo() {
		EBRBSInfo infoObject = new EBRBSInfo();
		List<EBRBS> dataList = new ArrayList<EBRBS>();
		EBRBS object = new EBRBS();
		object.setRptTime(dateString);
		object.setRptType(EBRInfoType.Sync.name());

		// object.setRelatedEBRPS(relatedEBRPS);
		// object.setRelatedEBRST(relatedEBRST);
		// object.setRelatedEBRAS(relatedEBRAS);

		Coverage coverage = new Coverage();
		coverage.setAreaCode("341523000000");

		object.setEBRID("059934152300000001");
		object.setEBRName("舒城县大喇叭系统");
		object.setURL("http://10.131.53.103:7070/ewbsserver/exchange");
		object.setLatitude("116.950794");
		object.setLongitude("31.467462");
		object.setSquare("300");
//		object.setCoverage(coverage);
		object.setPopulation(0.5);
		object.setLanguageCode("zho");
		object.setEquipRoom("舒城县应急广播播控室");

		RadioParams radioParams = new RadioParams();
		radioParams.setAutoSwitch(1);
		radioParams.setBackup(1);
		radioParams.setChannelName("ts频道");
		radioParams.setExperiment(1);
		radioParams.setFreq(80);
		radioParams.setPower(100);
		radioParams.setRemoteControl(1);
		object.setRadioParams(radioParams);

//		TVParams tVParams = new TVParams();
//		tVParams.setChannelName("channelName");
//		tVParams.setChannelNum("channelNum");
//		tVParams.setFreq(1);
//		tVParams.setProgramNum("programNum");
//
//		object.settVParams(tVParams);

		Schedule schedule = new Schedule();
		List<Switch> dataList2 = new ArrayList<Switch>();
		Switch switch1 = new Switch();
		switch1.setEndTime(DateTimeUtil.dateToString(date, DateStyle.HH_MM_SS));
		switch1.setStartTime(DateTimeUtil.dateToString(date, DateStyle.HH_MM_SS));
		switch1.setWeekday(1);
		dataList2.add(switch1);
		schedule.setDataList(dataList2);
		object.setSchedule(schedule);

		dataList.add(object);
		infoObject.setDataList(dataList);
		String ebdIndex = getRandomInteger();
		EBD ebd = EBDModelBuild.buildEBRBSInfo(eBRID, srcURL, ebdIndex, infoObject,null);
		send(ebd);
	}

	public static void testDTInfo() {
		EBRDTInfo infoObject = new EBRDTInfo();
		List<EBRDT> dataList = new ArrayList<EBRDT>();
		EBRDT object = new EBRDT();
		object.setRptTime(dateString);
		object.setRptType(EBRInfoType.Sync.name());
		// object.setRelatedEBRPS(relatedEBRPS);
		// object.setRelatedEBRST(relatedEBRST);
		object.setEBRID("060532032200000004");
		object.setEBRName("安徽省舒城县地面数字电视终端2-2");
		object.setLatitude("116.950794");
		object.setLongitude("31.467462");
		dataList.add(object);
		infoObject.setDataList(dataList);
		String ebdIndex = getRandomInteger();
		EBD ebd = EBDModelBuild.buildEBRDTInfo(eBRID, srcURL, ebdIndex, infoObject,null);
		send(ebd);
	}

	public static void testBrdLog() {
		EBMBrdItem ebmBrdItem = new EBMBrdItem();

		EBM eBM = new EBM();
		eBM.setEBMID("010234152300000001201709042031");
		MsgBasicInfo msgBasicInfo = new MsgBasicInfo();
		msgBasicInfo.setMsgType(1);
		msgBasicInfo.setSenderName("安徽省调度控制平台");
		msgBasicInfo.setSenderCode("010234000000000001");
		msgBasicInfo.setSendTime(dateString);
		msgBasicInfo.setSeverity(1);
		msgBasicInfo.setEventType("0000");
		msgBasicInfo.setStartTime(dateString);
		msgBasicInfo.setEndTime(dateString);
		eBM.setMsgBasicInfo(msgBasicInfo);

		MsgContent msgContent = new MsgContent();
		msgContent.setLanguageCode("zho");
		msgContent.setMsgTitle("测试");
		msgContent.setMsgDesc("测试数据，联动测试");
		msgContent.setAreaCode("341523000000,341523100000,341523100223");
		msgContent.setProgramNum(1);
		eBM.setMsgContent(msgContent);

		ebmBrdItem.setEBM(eBM);

		UnitInfo unitInfo = new UnitInfo();
		List<Unit> dataList1 = new ArrayList<Unit>();
		Unit unit = new Unit();
		EBRPS ebrps2 = new EBRPS();
		ebrps2.setEBRID("050332032200000002");
		unit.setEBRPS(ebrps2);
		unit.setPersonID("00185854");
		unit.setPersonName("李四");
		unit.setUnitId("100000011");
		unit.setUnitName("广播部门");
		dataList1.add(unit);
		unitInfo.setDataList(dataList1);
		ebmBrdItem.setUnitInfo(unitInfo);

		ebmBrdItem.setBrdStateCode(1);
		ebmBrdItem.setBrdStateDesc("等待播发（未到播发时间）");

		Coverage coverage = new Coverage();
		coverage.setAreaCode("341523000000");
		coverage.setCoveragePercent(0.99);
		//	coverage.setCoverageRate(0.99f);
		ebmBrdItem.setCoverage(coverage);

		ResBrdItem brdItem = new ResBrdItem();
		EBRPS ebrps = new EBRPS();
		ebrps.setEBRID("050332032200000002");
		brdItem.setEBRPS(ebrps);

		EBRST ebrst = new EBRST();
		ebrst.setEBRID("010232032200000001");
		brdItem.setEBRST(ebrst);

		EBRAS ebras = new EBRAS();
		ebras.setEBRID("010232032200000001");
		brdItem.setEBRAS(ebras);

		EBRBS ebrbs = new EBRBS();
		ebrbs.setRptTime(dateString);
//		ebrbs.setBrdSysType("2");
		ebrbs.setBrdSysInfo("1338MHZ");
		ebrbs.setStartTime(dateString);
		ebrbs.setEndTime(dateString);
		ebrbs.setFileURL("http://192.168.10.129/test.mp3");
		ebrbs.setBrdStateCode(1);
		ebrbs.setBrdStateDesc("正在播发");
		List<EBRBS> ebrbsList = new ArrayList<EBRBS>();
		ebrbsList.add(ebrbs);
		brdItem.setDataList(ebrbsList);

		ResBrdInfo brdInfo = new ResBrdInfo();
		List<ResBrdItem> dataList = new ArrayList<ResBrdItem>();
		dataList.add(brdItem);
		brdInfo.setDataList(dataList);
		ebmBrdItem.setResBrdInfo(brdInfo);

		EBMBrdLog ebmBrdLog = new EBMBrdLog();
		List<EBMBrdItem> dataList0 = new ArrayList<EBMBrdItem>();
		dataList0.add(ebmBrdItem);
		ebmBrdLog.setDataList(dataList0);

		String ebdIndex = getRandomInteger();
		EBD ebd = EBDModelBuild.buildBrdLog(eBRID, srcURL, ebdIndex, ebmBrdLog,null);
		send(ebd);
	}

	public static void testPsState() {
		EBRPSState infoObject = new EBRPSState();
		List<EBRPS> dataList = new ArrayList<EBRPS>();
		EBRPS object = new EBRPS();
		object.setRptTime(dateString);
		object.setEBRID("010232032200000001");
		object.setStateCode(2);
		object.setStateDesc("关机/停止运行");
		dataList.add(object);
		infoObject.setDataList(dataList);
		String ebdIndex = getRandomInteger();
		EBD ebd = EBDModelBuild.buildPSState(eBRID, srcURL, ebdIndex, infoObject,null);
		send(ebd);
	}

	public static void testAsState() {
		EBRASState infoObject = new EBRASState();
		List<EBRAS> dataList = new ArrayList<EBRAS>();
		EBRAS object = new EBRAS();
		object.setRptTime(dateString);
		object.setEBRID("010232032200000001");
		object.setStateCode(1);
		object.setStateDesc("开机/运行正常");
		dataList.add(object);
		infoObject.setDataList(dataList);
		String ebdIndex = getRandomInteger();
		EBD ebd = EBDModelBuild.buildASState(eBRID, srcURL, ebdIndex, infoObject,null);
		send(ebd);
	}

	public static void testBSState() {
		EBRBSState infoObject = new EBRBSState();
		List<EBRBS> dataList = new ArrayList<EBRBS>();
		EBRBS object = new EBRBS();
		object.setRptTime(dateString);
		object.setEBRID("050332032200000002");
		object.setStateCode(1);
		object.setStateDesc("开机/运行正常");
		dataList.add(object);
		infoObject.setDataList(dataList);
		String ebdIndex = getRandomInteger();
		EBD ebd = EBDModelBuild.buildBSState(eBRID, srcURL, ebdIndex, infoObject,null);
		send(ebd);
	}

	public static void testDTState() {
		EBRDTState infoObject = new EBRDTState();
		List<EBRDT> dataList = new ArrayList<EBRDT>();
		EBRDT object = new EBRDT();
		object.setRptTime(dateString);
		object.setEBRID("060532032200000004");
		object.setStateCode(1);
		object.setStateDesc("开机/运行正常");
		dataList.add(object);
		infoObject.setDataList(dataList);
		String ebdIndex = getRandomInteger();
		EBD ebd = EBDModelBuild.buildDTState(eBRID, srcURL, ebdIndex, infoObject,null);
		send(ebd);
	}

	public static void testResonse() {
		String ebdIndex = getRandomInteger();
		EBDResponse ebdResponse = new EBDResponse();
		ebdResponse.setResultCode(EBDRespResultEnum.receivevalid.getCode());
		ebdResponse.setResultDesc(EBDRespResultEnum.receivevalid.getEbdResponseResult());
		EBD ebd = EBDModelBuild.buildResponse(eBRID, srcURL, ebdIndex, ebdResponse);
		send(ebd);
	}

	public static void testCheck() {
		ConnectionCheck connectionCheck = new ConnectionCheck();
		Date date = new Date();
		String rptTime = DateTimeUtil.dateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS);
		connectionCheck.setRptTime(rptTime);
		String ebdIndex = getRandomInteger();
		EBD ebd = EBDModelBuild.buildConnectionCheck(eBRID, srcURL, "0000000000000000", connectionCheck);
		send(ebd);
	}

	public static void testEBMState() {

		EBMStateRequest ebmStateRequest = new EBMStateRequest();
		EBM eBM = new EBM();
		eBM.setEBMID("010234152300000001201709040001"); //应急广播消息ID
		ebmStateRequest.setEBM(eBM);

		String ebdIndex = getRandomInteger();
		EBD ebd = EBDModelBuild.buildStateRequest(eBRID, srcURL, ebdIndex, ebmStateRequest);
		send(ebd);
	}

	public static void testEBMStateResponse() {

		EBMStateResponse ebmStateResponse = new EBMStateResponse();
		ebmStateResponse.setBrdStateCode(2);
		ebmStateResponse.setBrdStateDesc("正在播发");
		Coverage coverage = new Coverage();
		coverage.setCoverageRate(0.99f);
		coverage.setAreaCode("341523000000");
		//coverage.setCoveragePercent(0.4);
		ebmStateResponse.setCoverage(coverage);
		EBM eBM = new EBM();
		eBM.setEBMID("010234152300000001201709043043");
		ebmStateResponse.setEBM(eBM);
		ebmStateResponse.setRptTime(dateString);

		ResBrdInfo resBrdInfo = new ResBrdInfo();

		ResBrdItem brdItem = new ResBrdItem();


		EBRPS ebrps = new EBRPS();
		ebrps.setEBRID("010234152300000001");
		brdItem.setEBRPS(ebrps);

		EBRST ebrst = new EBRST();
		ebrst.setEBRID("030134152300000001");
		brdItem.setEBRST(ebrst);

		EBRAS ebras = new EBRAS();
		ebras.setEBRID("040134152300000001");
		brdItem.setEBRAS(ebras);

		EBRBS ebrbs = new EBRBS();
		ebrbs.setBrdStateCode(1);
		ebrbs.setBrdStateDesc("播发成功");
		ebrbs.setBrdSysInfo("(050232032200000001,3,80)");
//		ebrbs.setBrdSysType("0502");
		ebrbs.setEndTime(dateString);
		ebrbs.setFileURL("http://192.168.12.1/test.mp3");
		ebrbs.setRptTime(dateString);
		ebrbs.setStartTime(dateString);
		List<EBRBS> ebrbsList = new ArrayList<EBRBS>();
		ebrbsList.add(ebrbs);
		brdItem.setDataList(ebrbsList);

		List<ResBrdItem> dataList = new ArrayList<ResBrdItem>();
		dataList.add(brdItem);

		resBrdInfo.setDataList(dataList);
		ebmStateResponse.setResBrdInfo(resBrdInfo);

		String ebdIndex = getRandomInteger();
		EBD ebd = EBDModelBuild.buildStateResponse(eBRID, srcURL, ebdIndex, ebmStateResponse);
		send(ebd);
	}

	public static void testEBM() {

		Date  startDate = DateUtil.addDate(new Date(), Calendar.MINUTE, 1);
		String startTime= DateTimeUtil.dateToString(startDate, DateStyle.YYYY_MM_DD_HH_MM_SS);

		Date  endDate = DateUtil.addDate(new Date(), Calendar.MINUTE, 5);
		String endTime= DateTimeUtil.dateToString(endDate, DateStyle.YYYY_MM_DD_HH_MM_SS);

		MsgContent msgContent = new MsgContent();
		msgContent.setAreaCode("341523100201");
		msgContent.setLanguageCode("zho");
		msgContent.setMsgDesc("今天（21日），强降雨带将分裂为东西两段，浙江、贵州、湖南将有大到暴雨与此同时，华北、黄淮北部和华南地区热力持续。其中，北方以晴热为主，北京、天津、济南将有高温现身"
				+ " 华南以闷热为主，最高气温多以33、34℃为主，但体感温度将超35℃，甚至逼近40℃，公众需注意日常补充水分，谨防中暑。");
		msgContent.setMsgTitle("舒城县主动发送播放信息");
		msgContent.setProgramNum(1);

//		Auxiliary auxiliary = new Auxiliary();
//		auxiliary.setAuxiliaryType(Constants.EBM_AUXILIARY_MP3);
//		auxiliary.setAuxiliaryDesc("test.mp3");
//		auxiliary.setSize(1024);
//		List<Auxiliary> auxiliaryList = new ArrayList();
//		auxiliaryList.add(auxiliary);
//		msgContent.setAuxiliaryList(auxiliaryList);

		MsgBasicInfo msgBasicInfo = new MsgBasicInfo();
		msgBasicInfo.setEventType("0000");
		msgBasicInfo.setSenderCode("43415230000000103010101");
		msgBasicInfo.setSenderName("安徽省舒城县平台");
		msgBasicInfo.setSeverity(5);
		msgBasicInfo.setStartTime(startTime);
		msgBasicInfo.setEndTime(endTime);
		msgBasicInfo.setSendTime(dateString);

		msgBasicInfo.setMsgType(1);


		EBM eBM = new EBM();
		eBM.setEBMID("43415230000000103010101201805"+RandomStringUtils.randomNumeric(6));
		eBM.setEBMVersion("1.0");


//		RelatedInfo relatedInfo = new RelatedInfo();
//		relatedInfo.setEBMID("010234152300000001201709040000");
//		eBM.setRelatedInfo(relatedInfo);

		eBM.setMsgBasicInfo(msgBasicInfo);
		eBM.setMsgContent(msgContent);

//		EBMBrdItem ebmBrdItem = new EBMBrdItem();
//		ebmBrdItem.setBrdStateCode(1);
//		ebmBrdItem.setBrdStateDesc("等待播发（未到播发时间）");
//
//		Coverage coverage = new Coverage();
//		coverage.setAreaCode("341523000000");
//		coverage.setCoveragePercent(0.28);
//		ebmBrdItem.setCoverage(coverage);
//		ebmBrdItem.setEBM(eBM);


		// List<Dispatch> dispatchList=new ArrayList<Dispatch>();
		//
		// Dispatch dispatch=new Dispatch();
		// dispatch.setEBRAS(eBRAS);
		// dispatch.setEBRBS(eBRBS);
		// dispatch.setEBRPS(eBRPS);
		// dispatch.setLanguageCode(languageCode);
		// eBM.setDispatchList(dispatchList);
		//eBM.setEBMID(ebmId());

		String ebdIndex = getRandomInteger();
		EBD ebd = EBDModelBuild.buildEBM(eBRID, srcURL, ebdIndex, eBM);
		send(ebd);
	}

	private static synchronized void send(EBD ebd) {

		File file = FileUtil.converFile(filePathSend, ebd);

		// 生成签名和签名文件
		Signature signature = EBDModelBuild.buildSignature(file, ebd.getEBDID());
		File signFile = FileUtil.converFile(filePathSend, signature);

		List<File> files = new ArrayList<File>();
		files.add(file);
		files.add(signFile);
//		if(ebd.getEBDType().equals(EBDType.EBM.name())){  //TODO 发送播发请求，音频文件设置；发送音频文件
//		File map3File = new File("G:\\EBDR_test.mp3");
//			files.add(map3File);
//		}		
		File tarfile = TarFileUtil.compressorsTar(ebd, files, filePathSend);
		EBD ebdReceive = HttpRequestUtil.sendFile(tarfile, srcURL, filePathResponse);
		String xml = XmlUtil.toXml(ebdReceive);
		System.err.println(xml);
	}

	private static synchronized String getRandomInteger() {
//		int temp = (int) (Math.random() * 9);
//	//	Date dttime = new Date();
////		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSS");
////		String currentTime = sdf.format(dttime);
//		String currentTime=DateFormatUtils.format(new Date(), "yyyyMMddHHmmSS");
//		String str = currentTime + String.valueOf(temp);
//		return addZeroForNum(str,16); // 不足16位，则在后面补0
		String str = StringUtils.leftPad(System.nanoTime()+"",16,"0");
		System.err.println( str);
		return str;
	}

	private static String ebmId() {
		int temp = (int) (Math.random() * 9999);
		Date dttime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currentTime = sdf.format(dttime);
		String str = eBRID + currentTime + String.valueOf(temp);
		return str;
	}

	/**
	 * 字符串不足补0
	 * @param str 字符串
	 * @param strLength 长度
	 * @return
	 */
	private static String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				// sb.append("0").append(str);// 左补0
				sb.append(str).append("0");//右补0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}
}
