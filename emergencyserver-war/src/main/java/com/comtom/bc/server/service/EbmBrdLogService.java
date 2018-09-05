package com.comtom.bc.server.service;

import com.comtom.bc.exchange.commom.BroadcastStateEnum;

public interface EbmBrdLogService {

	public BroadcastStateEnum getBrdState(final String ebmId);
}
