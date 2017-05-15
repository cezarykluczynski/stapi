package com.cezarykluczynski.stapi.server.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebAppController {

	@RequestMapping({"/", "/about", "/api-browser", "/api-documentation", "/licensing", "/statistics"})
	public String index() {
		return "index";
	}
}


