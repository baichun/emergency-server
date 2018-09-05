package com.comtom.bc.server.repository.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <b>播出系统资源[bc_ebr_broadcast]数据持久化对象</b>
 * 
 * @author zhucanhui
 */
@Entity
@Table(name = "bc_ebr_broadcast")
public class EbrBroadcast {

	/**
	 * 播出系统ID
	 */
	@Id
	private String bsEbrId;

	/**
	 * 播出系统类型
	 */
	private String bsName;

	/**
	 * 播出系统URL
	 */
	private String bsUrl;

	/**
	 * 播出系统类型
	 */
	private String bsType;

	/**
	 * 播出系统经度
	 */
	private String longitude;

	/**
	 * 播出系统纬度
	 */
	private String latitude;

	/**
	 * 播放系统覆盖面积
	 */
	private BigDecimal square;

	/**
	 * 覆盖区域
	 */
	private String areaCode;

	/**
	 * 覆盖区域名称
	 */
	@Transient
	private String areaName;

	/**
	 * 覆盖人口
	 */
	private BigDecimal population;

	/**
	 * 原播语种
	 */
	private String languageCode;

	/**
	 * 机房名称
	 */
	private String equipRoom;

	/**
	 * 电台频道名称
	 */
	private String radioChannelName;

	/**
	 * 电台频道频率
	 */
	private BigDecimal radioFreq;

	/**
	 * 电台发射功率
	 */
	private Integer radioPower;

	/**
	 * 是否是备机
	 */
	private Integer backup;

	/**
	 * 是否自动切换备机
	 */
	private Integer autoSwitch;

	/**
	 * 能否遥控开机
	 */
	private Integer remoteControl;

	/**
	 * 实验/覆盖发射
	 */
	private Integer experiment;

	/**
	 * 电视台频道名称
	 */
	private String tvChannelName;

	/**
	 * 电视台频道频率
	 */
	private Integer tvFreq;

	/**
	 * 节目号
	 */
	private String programNum;

	/**
	 * 频道号
	 */
	private String tvChannelNum;

	/**
	 * 播出系统状态
	 */
	private Integer bsState;

	/**
	 * 关联平台资源编码
	 */
	private String relatedPsEbrId;

	/**
	 * 关联台站资源编码
	 */
	private String relatedRsEbrId;

	/**
	 * 关联适配器资源编码
	 */
	private String relatedAsEbrId;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 是否已同步标识（1：未同步 2：已同步）
	 */
	private Integer syncFlag;

	/**
	 * 播出系统状态是否已同步标识（1：未同步 2：已同步）
	 */
	private Integer statusSyncFlag;

	/**
	 * 覆盖的区域详情列表
	 */
	@Transient
	private List<RegionArea> coveredAreas;

	/**
	 * 运行图
	 */
	@Transient
	private List<Worktime> workTimeSwitch;

	/**
	 * 播出系统ID
	 * 
	 * @return bsEbrId
	 */
	public String getBsEbrId() {
		return bsEbrId;
	}

	/**
	 * 播出系统类型
	 * 
	 * @return bsName
	 */
	public String getBsName() {
		return bsName;
	}

	/**
	 * 播出系统URL
	 * 
	 * @return bsUrl
	 */
	public String getBsUrl() {
		return bsUrl;
	}

	/**
	 * 播出系统类型
	 * 
	 * @return bsType
	 */
	public String getBsType() {
		return bsType;
	}

	/**
	 * 播出系统经度
	 * 
	 * @return longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * 播出系统纬度
	 * 
	 * @return latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * 覆盖区域
	 * 
	 * @return areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * 覆盖人口
	 * 
	 * @return population
	 */
	public BigDecimal getPopulation() {
		return population;
	}

	/**
	 * 原播语种
	 * 
	 * @return languageCode
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * 机房名称
	 * 
	 * @return equipRoom
	 */
	public String getEquipRoom() {
		return equipRoom;
	}

	/**
	 * 电台频道名称
	 * 
	 * @return radioChannelName
	 */
	public String getRadioChannelName() {
		return radioChannelName;
	}

	/**
	 * 电台频道频率
	 * 
	 * @return radioFreq
	 */
	public BigDecimal getRadioFreq() {
		return radioFreq;
	}

	/**
	 * 电台发射功率
	 * 
	 * @return radioPower
	 */
	public Integer getRadioPower() {
		return radioPower;
	}

	/**
	 * 是否是备机
	 * 
	 * @return backup
	 */
	public Integer getBackup() {
		return backup;
	}

	/**
	 * 是否自动切换备机
	 * 
	 * @return autoSwitch
	 */
	public Integer getAutoSwitch() {
		return autoSwitch;
	}

	/**
	 * 能否遥控开机
	 * 
	 * @return remoteControl
	 */
	public Integer getRemoteControl() {
		return remoteControl;
	}

	/**
	 * 实验/覆盖发射
	 * 
	 * @return experiment
	 */
	public Integer getExperiment() {
		return experiment;
	}

	/**
	 * 电视台频道名称
	 * 
	 * @return tvChannelName
	 */
	public String getTvChannelName() {
		return tvChannelName;
	}

	/**
	 * 电视台频道频率
	 * 
	 * @return tvFreq
	 */
	public Integer getTvFreq() {
		return tvFreq;
	}

	/**
	 * 节目号
	 * 
	 * @return programNum
	 */
	public String getProgramNum() {
		return programNum;
	}

	/**
	 * 频道号
	 * 
	 * @return tvChannelNum
	 */
	public String getTvChannelNum() {
		return tvChannelNum;
	}

	/**
	 * 播出系统状态
	 * 
	 * @return bsState
	 */
	public Integer getBsState() {
		return bsState;
	}

	/**
	 * 关联平台资源编码
	 * 
	 * @return relatedPsEbrId
	 */
	public String getRelatedPsEbrId() {
		return relatedPsEbrId;
	}

	/**
	 * 关联台站资源编码
	 * 
	 * @return relatedRsEbrId
	 */
	public String getRelatedRsEbrId() {
		return relatedRsEbrId;
	}

	/**
	 * 关联适配器资源编码
	 * 
	 * @return relatedAsEbrId
	 */
	public String getRelatedAsEbrId() {
		return relatedAsEbrId;
	}

	/**
	 * 创建时间
	 * 
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 更新时间
	 * 
	 * @return updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 是否已同步标识（1：未同步 2：已同步）
	 * 
	 * @return syncFlag
	 */
	public Integer getSyncFlag() {
		return syncFlag;
	}

	/**
	 * 播出系统ID
	 * 
	 * @param bsEbrId
	 */
	public void setBsEbrId(String bsEbrId) {
		this.bsEbrId = bsEbrId;
	}

	/**
	 * 播出系统类型
	 * 
	 * @param bsName
	 */
	public void setBsName(String bsName) {
		this.bsName = bsName;
	}

	/**
	 * 播出系统URL
	 * 
	 * @param bsUrl
	 */
	public void setBsUrl(String bsUrl) {
		this.bsUrl = bsUrl;
	}

	/**
	 * 资源类型
	 * 
	 * @param bsType
	 */
	public void setBsType(String bsType) {
		this.bsType = bsType;
	}

	/**
	 * 播出系统经度
	 * 
	 * @param longitude
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * 播出系统纬度
	 * 
	 * @param latitude
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the square
	 */
	public BigDecimal getSquare() {
		return square;
	}

	/**
	 * @param square
	 *            the square to set
	 */
	public void setSquare(BigDecimal square) {
		this.square = square;
	}

	/**
	 * 覆盖区域
	 * 
	 * @param areaCode
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * 覆盖人口
	 * 
	 * @param population
	 */
	public void setPopulation(BigDecimal population) {
		this.population = population;
	}

	/**
	 * 原播语种
	 * 
	 * @param languageCode
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * 机房名称
	 * 
	 * @param equipRoom
	 */
	public void setEquipRoom(String equipRoom) {
		this.equipRoom = equipRoom;
	}

	/**
	 * 电台频道名称
	 * 
	 * @param radioChannelName
	 */
	public void setRadioChannelName(String radioChannelName) {
		this.radioChannelName = radioChannelName;
	}

	/**
	 * 电台频道频率
	 * 
	 * @param radioFreq
	 */
	public void setRadioFreq(BigDecimal radioFreq) {
		this.radioFreq = radioFreq;
	}

	/**
	 * 电台发射功率
	 * 
	 * @param radioPower
	 */
	public void setRadioPower(Integer radioPower) {
		this.radioPower = radioPower;
	}

	/**
	 * 是否是备机
	 * 
	 * @param backup
	 */
	public void setBackup(Integer backup) {
		this.backup = backup;
	}

	/**
	 * 是否自动切换备机
	 * 
	 * @param autoSwitch
	 */
	public void setAutoSwitch(Integer autoSwitch) {
		this.autoSwitch = autoSwitch;
	}

	/**
	 * 能否遥控开机
	 * 
	 * @param remoteControl
	 */
	public void setRemoteControl(Integer remoteControl) {
		this.remoteControl = remoteControl;
	}

	/**
	 * 实验/覆盖发射
	 * 
	 * @param experiment
	 */
	public void setExperiment(Integer experiment) {
		this.experiment = experiment;
	}

	/**
	 * 电视台频道名称
	 * 
	 * @param tvChannelName
	 */
	public void setTvChannelName(String tvChannelName) {
		this.tvChannelName = tvChannelName;
	}

	/**
	 * 电视台频道频率
	 * 
	 * @param tvFreq
	 */
	public void setTvFreq(Integer tvFreq) {
		this.tvFreq = tvFreq;
	}

	/**
	 * 节目号
	 * 
	 * @param programNum
	 */
	public void setProgramNum(String programNum) {
		this.programNum = programNum;
	}

	/**
	 * 频道号
	 * 
	 * @param tvChannelNum
	 */
	public void setTvChannelNum(String tvChannelNum) {
		this.tvChannelNum = tvChannelNum;
	}

	/**
	 * 播出系统状态
	 * 
	 * @param bsState
	 */
	public void setBsState(Integer bsState) {
		this.bsState = bsState;
	}

	/**
	 * 关联平台资源编码
	 * 
	 * @param relatedPsEbrId
	 */
	public void setRelatedPsEbrId(String relatedPsEbrId) {
		this.relatedPsEbrId = relatedPsEbrId;
	}

	/**
	 * 关联台站资源编码
	 * 
	 * @param relatedRsEbrId
	 */
	public void setRelatedRsEbrId(String relatedRsEbrId) {
		this.relatedRsEbrId = relatedRsEbrId;
	}

	/**
	 * 关联适配器资源编码
	 * 
	 * @param relatedAsEbrId
	 */
	public void setRelatedAsEbrId(String relatedAsEbrId) {
		this.relatedAsEbrId = relatedAsEbrId;
	}

	/**
	 * 创建时间
	 * 
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 更新时间
	 * 
	 * @param updateTime
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 是否已同步标识（1：未同步 2：已同步）
	 * 
	 * @param syncFlag
	 */
	public void setSyncFlag(Integer syncFlag) {
		this.syncFlag = syncFlag;
	}

	public List<RegionArea> getCoveredAreas() {
		return coveredAreas;
	}

	public void setCoveredAreas(List<RegionArea> coveredAreas) {
		this.coveredAreas = coveredAreas;
	}

	public List<Worktime> getWorkTimeSwitch() {
		return workTimeSwitch;
	}

	public void setWorkTimeSwitch(List<Worktime> workTimeSwitch) {
		this.workTimeSwitch = workTimeSwitch;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName
	 *            the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getStatusSyncFlag() {
		return statusSyncFlag;
	}

	public void setStatusSyncFlag(Integer statusSyncFlag) {
		this.statusSyncFlag = statusSyncFlag;
	}
}