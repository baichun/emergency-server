package com.comtom.bc.server.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.comtom.bc.common.utils.ResUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.comtom.bc.common.Constants;
import com.comtom.bc.exchange.commom.RSStateEnum;
import com.comtom.bc.server.repository.dao.EbrAdaptorDAO;
import com.comtom.bc.server.repository.dao.EbrBsDAO;
import com.comtom.bc.server.repository.dao.EbrCheckDAO;
import com.comtom.bc.server.repository.dao.EbrPlatformDAO;
import com.comtom.bc.server.repository.dao.EbrStationDAO;
import com.comtom.bc.server.repository.dao.EbrTerminalDAO;
import com.comtom.bc.server.repository.entity.EbrAdaptor;
import com.comtom.bc.server.repository.entity.EbrBroadcast;
import com.comtom.bc.server.repository.entity.EbrCheck;
import com.comtom.bc.server.repository.entity.EbrPlatform;
import com.comtom.bc.server.repository.entity.EbrTerminal;

/**
 * 资源实时状态维护定时任务
 * 
 * @author kidsoul
 *
 */
//@Component
//@Configurable
//@EnableScheduling
public class EbrSyncStateTask {
	@Autowired
	EbrCheckDAO heartBeatDAO;
	@Autowired
	EbrPlatformDAO platformDAO;
	@Autowired
	EbrAdaptorDAO adaptorDAO;
	@Autowired
	EbrBsDAO broadcastDAO;
	@Autowired
	EbrStationDAO stationDAO;
	@Autowired
	EbrTerminalDAO terminalDAO;

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(EbrSyncStateTask.class);

	/**
	 * 根据心跳时间设定资源实时状态
	 */
	@Scheduled(cron = "${task.state-sync.cron}")
	public void notifyState() {
		List<EbrCheck> hBeatList = heartBeatDAO
				.findAll(new Sort(Direction.DESC, "rptTime", "ebrId"));
		if (null == hBeatList) {
			hBeatList = new ArrayList<EbrCheck>();
		}

		long offTimePoint = new Date().getTime() - (Constants.EBR_KEEP_ALIVE_SECOND * 1000);
		List<EbrCheck> aliveChecks = new ArrayList<EbrCheck>();
		List<EbrCheck> timeoutChecks = new ArrayList<EbrCheck>();
		for (EbrCheck hb : hBeatList) {
			if (hb.getRptTime().getTime() > offTimePoint) {
				aliveChecks.add(hb);
			} else {
				timeoutChecks.add(hb);
			}
		}

		updateAdaptorState(aliveChecks, timeoutChecks);
		updatePlatformState(aliveChecks, timeoutChecks);
		updateBroadcastState(aliveChecks, timeoutChecks);
		updateTerminalState(aliveChecks, timeoutChecks);

		if (logger.isDebugEnabled()) {
			logger.debug("ResourceStateTask.notifyState adaptor, platform, broadcast and terminal state alive or timeout updated.");
		}

	}

	private final List<String> findEbrIds(String ebrType,String resSubType, List<EbrCheck> checks) {
		List<String> ebrIdsFound = new ArrayList<String>();
		for (EbrCheck chk : checks) {
			if (chk.getEbrId().startsWith(ebrType,Constants.RESTYPE_TOFFSET)  && ResUtil.getResSubType(chk.getEbrId()).equals(resSubType) ) {
				ebrIdsFound.add(chk.getEbrId());
			}
		}

		return ebrIdsFound;
	}

	private void updateAdaptorState(List<EbrCheck> aliveChecks, List<EbrCheck> timeoutChecks) {
		List<EbrAdaptor> adaptors2Save = new ArrayList<EbrAdaptor>();

		List<String> aliveAdaptorIds = findEbrIds(Constants.EBR_TYPE_STATION,Constants.EBR_TYPE_ADAPTOR, aliveChecks);
		List<EbrAdaptor> aliveAdaptors = adaptorDAO.findAll(aliveAdaptorIds);
		if (null != aliveAdaptors) {
			for (EbrAdaptor as : aliveAdaptors) {
				as.setAsState(RSStateEnum.run.getCode());
				adaptors2Save.add(as);
			}
		}

		List<String> timeoutAdaptorIds = findEbrIds(Constants.EBR_TYPE_STATION,Constants.EBR_TYPE_ADAPTOR, timeoutChecks);
		List<EbrAdaptor> timeoutAdaptors = adaptorDAO.findAll(timeoutAdaptorIds);
		if (null != timeoutAdaptors) {
			for (EbrAdaptor as : timeoutAdaptors) {
				if (RSStateEnum.run.getCode().equals(as.getAsState())) {
					as.setAsState(RSStateEnum.stop.getCode());
					adaptors2Save.add(as);
				}
			}
		}

		adaptorDAO.save(adaptors2Save);

		logger.info("ResourceStateTask.updateAdaptorState alive or timeout updated.");
	}

	private void updatePlatformState(List<EbrCheck> aliveChecks, List<EbrCheck> timeoutChecks) {
		List<EbrPlatform> platforms2Save = new ArrayList<EbrPlatform>();

		List<String> alivePlatformIds = findEbrIds(Constants.EBR_TYPE_PLATFORM,Constants.EBR_THIS_LEVEL, aliveChecks);
		List<EbrPlatform> alivePlatforms = platformDAO.findAll(alivePlatformIds);
		if (null != alivePlatforms) {
			for (EbrPlatform ps : alivePlatforms) {
				ps.setPsState(RSStateEnum.run.getCode());
				platforms2Save.add(ps);
			}
		}
		List<String> timeoutPlatformIds = findEbrIds(Constants.EBR_TYPE_PLATFORM,Constants.EBR_THIS_LEVEL, timeoutChecks);
		List<EbrPlatform> timeoutPlatforms = platformDAO.findAll(timeoutPlatformIds);
		if (null != timeoutPlatforms) {
			for (EbrPlatform ps : timeoutPlatforms) {
				if (RSStateEnum.run.getCode().equals(ps.getPsState())) {
					ps.setPsState(RSStateEnum.stop.getCode());
					platforms2Save.add(ps);
				}
			}
		}

		platformDAO.save(platforms2Save);

		logger.info("ResourceStateTask.updatePlatformState alive or timeout updated.");
	}

	private void updateBroadcastState(List<EbrCheck> aliveChecks, List<EbrCheck> timeoutChecks) {
		List<EbrBroadcast> broadcasts2Save = new ArrayList<EbrBroadcast>();

		List<String> aliveBroadcastIds = findEbrIds(Constants.EBR_TYPE_STATION,Constants.EBR_TYPE_BROADCAST, aliveChecks);
		List<EbrBroadcast> alivebroadcasts = broadcastDAO.findAll(aliveBroadcastIds);
		if (null != alivebroadcasts) {
			for (EbrBroadcast bs : alivebroadcasts) {
				bs.setBsState(RSStateEnum.run.getCode());
				broadcasts2Save.add(bs);
			}
		}
		List<String> timeoutBroadcastIds = findEbrIds(Constants.EBR_TYPE_STATION,Constants.EBR_TYPE_BROADCAST, timeoutChecks);
		List<EbrBroadcast> timeoutBroadcasts = broadcastDAO.findAll(timeoutBroadcastIds);
		if (null != timeoutBroadcasts) {
			for (EbrBroadcast bs : timeoutBroadcasts) {
				if (RSStateEnum.run.getCode().equals(bs.getBsState())) {
					bs.setBsState(RSStateEnum.stop.getCode());
					broadcasts2Save.add(bs);
				}
			}
		}

		broadcastDAO.save(broadcasts2Save);

		logger.info("ResourceStateTask.updateBroadcastState alive or timeout updated.");
	}

	private void updateTerminalState(List<EbrCheck> aliveChecks, List<EbrCheck> timeoutChecks) {
		List<EbrTerminal> terminals2Save = new ArrayList<EbrTerminal>();

		List<String> aliveTerminalIds = findEbrIds(Constants.EBR_TYPE_STATION,Constants.EBR_TYPE_TERMINAL, aliveChecks);
		List<EbrTerminal> aliveTerminals = terminalDAO.findAll(aliveTerminalIds);
		if (null != aliveTerminals) {
			for (EbrTerminal trm : aliveTerminals) {
				trm.setTerminalState(RSStateEnum.run.getCode());
				terminals2Save.add(trm);
			}
		}
		List<String> timeoutTerminalIds = findEbrIds(Constants.EBR_TYPE_STATION,Constants.EBR_TYPE_TERMINAL, timeoutChecks);
		List<EbrTerminal> timeouttTerminals = terminalDAO.findAll(timeoutTerminalIds);
		if (null != timeouttTerminals) {
			for (EbrTerminal trm : timeouttTerminals) {
				if (RSStateEnum.run.getCode().equals(trm.getTerminalState())) {
					trm.setTerminalState(RSStateEnum.stop.getCode());
					terminals2Save.add(trm);
				}
			}
		}

		terminalDAO.save(terminals2Save);

		logger.info("ResourceStateTask.updateTerminalState alive or timeout updated.");
	}
}
