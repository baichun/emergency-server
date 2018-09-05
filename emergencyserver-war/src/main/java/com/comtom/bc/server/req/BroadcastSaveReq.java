package com.comtom.bc.server.req;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.comtom.bc.server.repository.entity.Worktime;

public class BroadcastSaveReq extends BaseReq{
	private String bsEbrId;
	private String bsName;
	private String bsUrl;
	private String bsType;
	private String longitude;
	private String latitude;
	private String square;
	private String areaCode;
	private BigDecimal population;
	private String languageCode;
	private String equipRoom;
	private String radioChannelName;
	private BigDecimal radioFreq;
	private Integer radioPower;
	private Integer backup;
	private Integer autoSwitch;
	private Integer remoteControl;
	private Integer experiment;
	private String tvChannelName;
	private Integer tvFreq;
	private String programNum;
	private String tvChannelNum;
	private Integer bsState;
	private String relatedPsEbrId;
	private String relatedRsEbrId;
	private String relatedAsEbrId;
	private Date createTime;
	private Date updateTime;
	private Integer syncFlag;
	
	/**
	 * 运行图
	 */
	private List<Worktime> workTimeSwitch;
	
	public String getBsEbrId() {
		return bsEbrId;
	}
	public void setBsEbrId(String bsEbrId) {
		this.bsEbrId = bsEbrId;
	}
	public String getBsName() {
		return bsName;
	}
	public void setBsName(String bsName) {
		this.bsName = bsName;
	}
	public String getBsUrl() {
		return bsUrl;
	}
	public void setBsUrl(String bsUrl) {
		this.bsUrl = bsUrl;
	}
	public String getBsType() {
		return bsType;
	}
	public void setBsType(String bsType) {
		this.bsType = bsType;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getSquare() {
		return square;
	}
	public void setSquare(String square) {
		this.square = square;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public BigDecimal getPopulation() {
		return population;
	}
	public void setPopulation(BigDecimal population) {
		this.population = population;
	}
	public String getLanguageCode() {
		return languageCode;
	}
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	public String getEquipRoom() {
		return equipRoom;
	}
	public void setEquipRoom(String equipRoom) {
		this.equipRoom = equipRoom;
	}
	public String getRadioChannelName() {
		return radioChannelName;
	}
	public void setRadioChannelName(String radioChannelName) {
		this.radioChannelName = radioChannelName;
	}
	public BigDecimal getRadioFreq() {
		return radioFreq;
	}
	public void setRadioFreq(BigDecimal radioFreq) {
		this.radioFreq = radioFreq;
	}
	public Integer getRadioPower() {
		return radioPower;
	}
	public void setRadioPower(Integer radioPower) {
		this.radioPower = radioPower;
	}
	public Integer getBackup() {
		return backup;
	}
	public void setBackup(Integer backup) {
		this.backup = backup;
	}
	public Integer getAutoSwitch() {
		return autoSwitch;
	}
	public void setAutoSwitch(Integer autoSwitch) {
		this.autoSwitch = autoSwitch;
	}
	public Integer getRemoteControl() {
		return remoteControl;
	}
	public void setRemoteControl(Integer remoteControl) {
		this.remoteControl = remoteControl;
	}
	public Integer getExperiment() {
		return experiment;
	}
	public void setExperiment(Integer experiment) {
		this.experiment = experiment;
	}
	public String getTvChannelName() {
		return tvChannelName;
	}
	public void setTvChannelName(String tvChannelName) {
		this.tvChannelName = tvChannelName;
	}
	public Integer getTvFreq() {
		return tvFreq;
	}
	public void setTvFreq(Integer tvFreq) {
		this.tvFreq = tvFreq;
	}
	public String getProgramNum() {
		return programNum;
	}
	public void setProgramNum(String programNum) {
		this.programNum = programNum;
	}
	public String getTvChannelNum() {
		return tvChannelNum;
	}
	public void setTvChannelNum(String tvChannelNum) {
		this.tvChannelNum = tvChannelNum;
	}
	public Integer getBsState() {
		return bsState;
	}
	public void setBsState(Integer bsState) {
		this.bsState = bsState;
	}
	public String getRelatedPsEbrId() {
		return relatedPsEbrId;
	}
	public void setRelatedPsEbrId(String relatedPsEbrId) {
		this.relatedPsEbrId = relatedPsEbrId;
	}
	public String getRelatedRsEbrId() {
		return relatedRsEbrId;
	}
	public void setRelatedRsEbrId(String relatedRsEbrId) {
		this.relatedRsEbrId = relatedRsEbrId;
	}
	public String getRelatedAsEbrId() {
		return relatedAsEbrId;
	}
	public void setRelatedAsEbrId(String relatedAsEbrId) {
		this.relatedAsEbrId = relatedAsEbrId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getSyncFlag() {
		return syncFlag;
	}
	public void setSyncFlag(Integer syncFlag) {
		this.syncFlag = syncFlag;
	}
	public List<Worktime> getWorkTimeSwitch() {
		return workTimeSwitch;
	}
	public void setWorkTimeSwitch(List<Worktime> workTimeSwitch) {
		this.workTimeSwitch = workTimeSwitch;
	}
}
