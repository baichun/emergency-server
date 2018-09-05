
package com.comtom.bc.exchange.util;

import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.Coverage;
import com.comtom.bc.exchange.model.ebd.commom.EBRAS;
import com.comtom.bc.exchange.model.ebd.commom.EBRBS;
import com.comtom.bc.exchange.model.ebd.commom.EBRDT;
import com.comtom.bc.exchange.model.ebd.commom.EBRPS;
import com.comtom.bc.exchange.model.ebd.commom.EBRST;
import com.comtom.bc.exchange.model.ebd.commom.Params;
import com.comtom.bc.exchange.model.ebd.commom.RadioParams;
import com.comtom.bc.exchange.model.ebd.commom.RelatedEBRAS;
import com.comtom.bc.exchange.model.ebd.commom.RelatedEBRPS;
import com.comtom.bc.exchange.model.ebd.commom.RelatedEBRST;
import com.comtom.bc.exchange.model.ebd.commom.Schedule;
import com.comtom.bc.exchange.model.ebd.commom.Switch;
import com.comtom.bc.exchange.model.ebd.commom.TVParams;
import com.comtom.bc.exchange.model.ebd.details.info.EBRASInfo;
import com.comtom.bc.exchange.model.ebd.details.info.EBRBSInfo;
import com.comtom.bc.exchange.model.ebd.details.info.EBRDTInfo;
import com.comtom.bc.exchange.model.ebd.details.info.EBRPSInfo;
import com.comtom.bc.exchange.model.ebd.details.info.EBRSTInfo;
import com.comtom.bc.exchange.model.ebd.details.other.ConnectionCheck;
import com.comtom.bc.exchange.model.ebd.details.other.EBDResponse;
import com.comtom.bc.exchange.model.ebd.details.other.EBM;
import com.comtom.bc.exchange.model.ebd.details.other.EBMBrdLog;
import com.comtom.bc.exchange.model.ebd.details.other.EBMStateRequest;
import com.comtom.bc.exchange.model.ebd.details.other.EBMStateResponse;
import com.comtom.bc.exchange.model.ebd.details.other.OMDRequest;
import com.comtom.bc.exchange.model.ebd.details.state.EBRASState;
import com.comtom.bc.exchange.model.ebd.details.state.EBRBSState;
import com.comtom.bc.exchange.model.ebd.details.state.EBRDTState;
import com.comtom.bc.exchange.model.ebd.details.state.EBRPSState;
import com.comtom.bc.exchange.model.ebd.ebm.Auxiliary;
import com.comtom.bc.exchange.model.ebd.ebm.DEST;
import com.comtom.bc.exchange.model.ebd.ebm.Dispatch;
import com.comtom.bc.exchange.model.ebd.ebm.EBMBrdItem;
import com.comtom.bc.exchange.model.ebd.ebm.MsgBasicInfo;
import com.comtom.bc.exchange.model.ebd.ebm.MsgContent;
import com.comtom.bc.exchange.model.ebd.ebm.RelatedEBD;
import com.comtom.bc.exchange.model.ebd.ebm.RelatedInfo;
import com.comtom.bc.exchange.model.ebd.ebm.ResBrdInfo;
import com.comtom.bc.exchange.model.ebd.ebm.ResBrdItem;
import com.comtom.bc.exchange.model.ebd.ebm.SRC;
import com.comtom.bc.exchange.model.ebd.ebm.Unit;
import com.comtom.bc.exchange.model.ebd.ebm.UnitInfo;
import com.comtom.bc.exchange.model.signature.Signature;
import com.comtom.bc.exchange.model.signature.SignatureCert;

public class ClassUtil {
	
	public static Class<?>[] ebdClasses=new Class[]{
		EBD.class,SRC.class,DEST.class,RelatedEBD.class,
		EBM.class,RelatedInfo.class,MsgBasicInfo.class,MsgContent.class,Auxiliary.class,
			Dispatch.class,EBRPS.class,EBRAS.class,EBRBS.class,   
				/*可删除*/RelatedEBRPS.class,RelatedEBRST.class,RelatedEBRAS.class,Coverage.class,
				/*可删除*/RadioParams.class,TVParams.class,Schedule.class,Switch.class,
				
		EBMStateResponse.class,EBM.class,Coverage.class,ResBrdInfo.class,ResBrdItem.class,
			EBRPS.class,EBRST.class,EBRAS.class,EBRBS.class,
				/*可删除*/RelatedInfo.class,MsgBasicInfo.class,MsgContent.class,Auxiliary.class,
				/*可删除*/Dispatch.class,RelatedEBRPS.class,RelatedEBRST.class,RelatedEBRAS.class,
				/*可删除*/RadioParams.class,TVParams.class,Schedule.class,Switch.class,
			
		EBMStateRequest.class,EBM.class,
				/*可删除*/RelatedInfo.class,MsgBasicInfo.class,MsgContent.class,Auxiliary.class,
				/*可删除*/Dispatch.class,EBRPS.class,EBRAS.class,EBRBS.class,   
				/*可删除*/RelatedEBRPS.class,RelatedEBRST.class,RelatedEBRAS.class,Coverage.class,
				/*可删除*/RadioParams.class,TVParams.class,Schedule.class,Switch.class,			
			
		OMDRequest.class,Params.class,
		EBRPSInfo.class,Params.class,EBRPS.class,RelatedEBRPS.class,
		EBRSTInfo.class,Params.class,EBRST.class,RelatedEBRPS.class,
		EBRASInfo.class,Params.class,EBRAS.class,RelatedEBRPS.class,RelatedEBRST.class,
		EBRDTInfo.class,Params.class,EBRDT.class,RelatedEBRPS.class,
		EBRBSInfo.class,Params.class,EBRBS.class,RelatedEBRPS.class,RelatedEBRST.class,RelatedEBRAS.class,
				/*可删除*/Coverage.class,RadioParams.class,TVParams.class,Schedule.class,Switch.class,
			
		EBMBrdLog.class,Params.class,EBMBrdItem.class,EBM.class,MsgBasicInfo.class,MsgContent.class,
			UnitInfo.class,Unit.class,EBRPS.class,
				/*可删除*/RelatedEBRPS.class,Auxiliary.class,RelatedInfo.class,Dispatch.class,
				/*可删除*/EBRPS.class,EBRAS.class,EBRBS.class,   
				/*可删除*/RelatedEBRPS.class,RelatedEBRST.class,RelatedEBRAS.class,Coverage.class,
				/*可删除*/RadioParams.class,TVParams.class,Schedule.class,Switch.class,
		EBRPSState.class,EBRPS.class,  /*可删除*/RelatedEBRPS.class,
		EBRASState.class,EBRAS.class,  /*可删除*/RelatedEBRPS.class,RelatedEBRST.class,
		EBRBSState.class,EBRBS.class,  /*可删除*/RelatedEBRPS.class,RelatedEBRST.class,RelatedEBRAS.class,
		EBRDTState.class,EBRDT.class,  /*可删除*/RelatedEBRPS.class,
		ConnectionCheck.class,
		EBDResponse.class
	};
	
	public static Class<?>[] sigClasses=new Class[]{
		Signature.class,RelatedEBD.class,SignatureCert.class
	};
}
