package com.comtom.bc.common.enums;

/**
 * 策略类型
 * 
 * @author zhucanhui
 * 
 */
public enum StrategyType {

	onetime(1, "一次性"), day(1, "天"), week(3, "周");

	private Integer value;

	private String name;

	private StrategyType(Integer value, String name) {
		this.name = name;
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
