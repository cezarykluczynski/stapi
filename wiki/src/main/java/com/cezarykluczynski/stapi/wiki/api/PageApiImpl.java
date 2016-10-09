package com.cezarykluczynski.stapi.wiki.api;

import com.cezarykluczynski.stapi.wiki.connector.bliki.BlikiConnector;
import com.cezarykluczynski.stapi.wiki.dto.Page;
import com.cezarykluczynski.stapi.wiki.parser.XMLParseParser;
import org.springframework.stereotype.Service;

@Service
public class PageApiImpl implements PageApi {

	private BlikiConnector blikiConnector;

	public PageApiImpl(BlikiConnector blikiConnector) {
		this.blikiConnector = blikiConnector;
	}

	@Override
	public Page getPage(String title) {
		return parsePageInfo(blikiConnector.getPage(title));
	}

	private Page parsePageInfo(String xml) {
		try {
			XMLParseParser xmlCategoryMembersParser = new XMLParseParser(xml);
			return xmlCategoryMembersParser.getPage();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
