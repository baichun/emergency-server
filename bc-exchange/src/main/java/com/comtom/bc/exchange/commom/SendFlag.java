package com.comtom.bc.exchange.commom;

public enum SendFlag {

	receive(1, "接收"), send(2, "发送");

	private Integer value;

	private String name;

	private SendFlag(Integer value, String name) {
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
