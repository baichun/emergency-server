package com.comtom.bc.server.rest.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.rest.dto.SingleBroadcastDTO;

public class JaxbObjectAndXmlUtil {

	/**
	 * @param xmlStr
	 *            字符串
	 * @param c
	 *            对象Class类型
	 * @return 对象实例
	 */
	@SuppressWarnings("unchecked")
	public static <T> T xml2Object(String xmlStr, Class<T> c) {
		try {
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = context.createUnmarshaller();

			T t = (T) unmarshaller.unmarshal(new StringReader(xmlStr));

			return t;

		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * @param object
	 *            对象
	 * @return 返回xmlStr
	 */
	public static String object2Xml(Object object) {
		try {
			StringWriter writer = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			Marshaller marshal = context.createMarshaller();

			marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // 格式化输出
			marshal.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// 编码格式,默认为utf-8
			marshal.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xml头信息
			marshal.setProperty("jaxb.encoding", "utf-8");
			marshal.marshal(object, writer);

			return new String(writer.getBuffer());

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void main(String[] args) {
		/** 构造测试报文头对象 */
		SingleBroadcastDTO dataBean = new SingleBroadcastDTO();
		dataBean.setPlatformId("account1");
		dataBean.setTitle("测试");
		dataBean.setContentType("text");
		dataBean.setEventLevel(1);
		dataBean.setEventCategory("11000");
		dataBean.setPlayTime(new Date());
		List<String> regions=new ArrayList<String>();
		regions.add("43");
		regions.add("42");
		dataBean.setRegions(regions);
		dataBean.setContentType("text");
		dataBean.setContent("预警内容");
		dataBean.setUrl("hello.mp3");
		String xmlStr = JaxbObjectAndXmlUtil.object2Xml(dataBean);// 构造报文 XML 格式的字符串
		System.out.println("对象转xml报文： \n" + xmlStr);

		SingleBroadcastDTO msgBean2 = JaxbObjectAndXmlUtil.xml2Object(xmlStr, SingleBroadcastDTO.class);
		System.out.println("报文转xml转： \n" + JsonUtil.toJson(msgBean2));
	}
}