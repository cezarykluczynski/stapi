package com.cezarykluczynski.stapi.wiki.api;

import com.cezarykluczynski.stapi.wiki.connector.bliki.BlikiConnector;
import com.cezarykluczynski.stapi.wiki.dto.Page;
import com.cezarykluczynski.stapi.wiki.parser.XMLParseParser;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PageApiImpl implements PageApi {

	private BlikiConnector blikiConnector;

	public PageApiImpl(BlikiConnector blikiConnector) {
		this.blikiConnector = blikiConnector;
	}

	@Override
	public Page getPage(String title) {
		return parsePageInfo(blikiConnector.getPage(title));
	}

	@Override
	public List<Page> getPages(List<String> titleList) {
		List<Page> pageList = Lists.newArrayList();

		for (String title : titleList) {
			Page page = getPage(title);

			if (page == null) {
				log.warn("Could not get page with title {}", title);
				continue;
			}

			pageList.add(page);
		}

		return pageList;
	}

	private Page parsePageInfo(String xml) {
		try {
			XMLParseParser xmlParseParser = new XMLParseParser(xml);
			return xmlParseParser.getPage();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
