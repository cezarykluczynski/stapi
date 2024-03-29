package com.cezarykluczynski.stapi.server.common.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebAppController {

	@RequestMapping({"/", "/about", "/api-overview", "/api-browser", "/api-documentation", "/licensing", "/clients"})
	public String index() {
		return "index";
	}

	@RequestMapping({"/terms-of-service"})
	public String termsOfService() {
		return "termsOfService";
	}

	@RequestMapping({"/privacy-policy"})
	public String privacyPolicy() {
		return "privacyPolicy";
	}

	@RequestMapping({"/robots.txt"})
	public HttpEntity<String> robotsTxt() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.TEXT_PLAIN);
		return new HttpEntity<>("User-agent: *\nAllow: /\nDisallow: /terms-of-service\nDisallow: /privacy-policy", httpHeaders);
	}

}
