package com.comtom.bc.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 欢迎页面控制器
 * 
 * @author zhucanhui
 *
 */
@Controller
public class IndexController {
	@RequestMapping("/")
	public ModelAndView index() {
		return new ModelAndView("welcome.html");
	}
}
