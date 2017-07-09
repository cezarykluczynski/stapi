package com.cezarykluczynski.stapi.etl.common.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class JsoupParser {

	public Document parse(String html) {
		return Jsoup.parse(html);
	}

}
