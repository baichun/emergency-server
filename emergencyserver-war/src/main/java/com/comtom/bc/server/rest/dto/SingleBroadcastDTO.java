package com.comtom.bc.server.rest.dto;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 单次广播
 * @author comtom
 *
 */
@XmlRootElement( name = "broadcast" )
@XmlType(propOrder = { "platformId", "regions","playTime", "title","eventCategory","eventLevel","contentType","content","url"})  
public class SingleBroadcastDTO {

	/**
	 * 平台ID
	 */
	private String platformId;
	
	/**
	 * 播放区域
	 */
	private List<String> regions;
	
	/**
	 * 预警标题
	 */
	private String title;
	
	/**
	 * 事件级别(1:红,2:橙,3:黄,4:蓝)
	 */
	private Integer eventLevel;
	
	/**
	 * 事件类型
	 */
	private String eventCategory;
	
	/**
	 * 广播时间
	 */
	private Date playTime;
	
	/**
	 * 内容类型（text,textFile,audio）
	 */
	private String contentType;
	
	/**
	 * 文本内容
	 */
	private String content;
	
	/**
	 * 文本或音频文件的url
	 */
	private String url;

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	@XmlElementWrapper(name = "regions") 
	@XmlElement(name = "region")
	public List<String> getRegions() {
		return regions;
	}

	public void setRegions(List<String> regions) {
		this.regions = regions;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getEventLevel() {
		return eventLevel;
	}

	public void setEventLevel(Integer eventLevel) {
		this.eventLevel = eventLevel;
	}

	public String getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(String eventCategory) {
		this.eventCategory = eventCategory;
	}

	public Date getPlayTime() {
		return playTime;
	}

	public void setPlayTime(Date playTime) {
		this.playTime = playTime;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
