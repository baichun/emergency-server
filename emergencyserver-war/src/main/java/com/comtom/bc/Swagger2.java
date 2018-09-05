package com.comtom.bc;

import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.comtom.bc.common.Constants;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置初始化
 * 
 * @author zhucanhui
 *
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
//				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.apis(RequestHandlerSelectors.basePackage("com.comtom.bc.server.controller"))
				.build();
	}
	
	@Bean
	public Docket createProtectRestApi() {
		ParameterBuilder tokenPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<Parameter>();
		tokenPar.name(Constants.ACCESS_TOKEN).description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
		pars.add(tokenPar.build());
		return new Docket(DocumentationType.SWAGGER_2).groupName("accessAPI").select()
				.apis(RequestHandlerSelectors.basePackage("com.comtom.bc.server.rest")).build().globalOperationParameters(pars).apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("应急广播平台RESTful API文档")
				.description("提供给前端调用者接口参考和查看API详细信息").termsOfServiceUrl("http://www.comtom.cn/")
				.contact("zhucanhui").version("1.0").build();
	}

}
