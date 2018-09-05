package com.comtom.bc;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.comtom.bc.common.Constants;
import com.comtom.bc.server.repository.support.CustomRepositoryFactoryBean;

/**
 * Web服务入口类，提供Web容器初始化<br>
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
@EnableAsync
@ImportResource("classpath:application-jpa.xml")
@MapperScan(basePackages = {"com.comtom.bc.server.repository.mapper"})
public class Application extends SpringBootServletInitializer {

	/**
	 * 应用上下文, 缺省为/(无上下文)
	 */
	private static String webContext = "/";

	/**
	 * 服务端口, 缺省为8080
	 */
	private static int port = 8080;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		SpringApplication.run(Application.class, args);

		// 设置服务访问上下文和服务端口
		setWebContext("/ewbsserver/swagger-ui.html");
		setPort(7070);
		outputMsg(start);
	}

	@Bean
	public CharacterEncodingFilter initializeCharacterEncodingFilter() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding(Constants.CHARACTER_ENCODING);
		filter.setForceEncoding(true);
		return filter;
	}

	/**
	 * 输出启动完成后提示信息
	 * 
	 * @param start
	 */
	private static void outputMsg(long start) {

		// 构造web初始访问地址
		webContext = webContext.equals("/") ? "" : webContext;
		String webUrl = "http://localhost";
		webUrl = webUrl + ":" + getPort() + getWebContext();
		long alltime = System.currentTimeMillis() - start;
		String msg = "ewbsserver启动成功";
		String supportMsg = "";
		msg = msg + "[" + alltime + "毫秒]" + " >> " + webUrl + supportMsg;
		System.out.println(msg);
	}

	private static String getWebContext() {
		return webContext;
	}

	/**
	 * 应用上下文, 缺省为/(无上下文)
	 *
	 * @param webContext
	 */
	private static void setWebContext(String webContext) {
		Application.webContext = webContext;
	}

	/**
	 * @return the port
	 */
	public static int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public static void setPort(int port) {
		Application.port = port;
	}


}
