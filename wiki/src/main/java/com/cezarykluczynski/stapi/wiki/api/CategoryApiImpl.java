package com.cezarykluczynski.stapi.wiki.api;

import com.cezarykluczynski.stapi.util.constants.ApiParams;
import com.cezarykluczynski.stapi.wiki.connector.bliki.BlikiConnector;
import com.cezarykluczynski.stapi.wiki.converter.PageHeaderConverter;
import com.cezarykluczynski.stapi.wiki.dto.PageHeader;
import com.google.common.collect.Maps;
import info.bliki.api.PageInfo;
import info.bliki.api.XMLCategoryMembersParser;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Service
public class CategoryApiImpl implements CategoryApi {

	private BlikiConnector blikiConnector;

	private PageHeaderConverter pageHeaderConverter;

	@Inject
	public CategoryApiImpl(BlikiConnector blikiConnector, PageHeaderConverter pageHeaderConverter) {
		this.blikiConnector = blikiConnector;
		this.pageHeaderConverter = pageHeaderConverter;
	}

	@Override
	public List<PageHeader> getPages(String title) {
		Map<String, String> params = Maps.newHashMap();
		params.put(ApiParams.KEY_LIST, ApiParams.KEY_LIST_VALUE_CATEGORYMEMBERS);
		params.put(ApiParams.KEY_CATEGORY_TITLE, ApiParams.KEY_CATEGORY_TITLE_VALUE_PREFIX + title);
		String xml = blikiConnector.readXML(params);
		return pageHeaderConverter.fromPageInfoList(parsePageInfo(xml));
	}

	private List<PageInfo> parsePageInfo(String xml) {
		try {
			XMLCategoryMembersParser xmlCategoryMembersParser = new XMLCategoryMembersParser(xml);
			xmlCategoryMembersParser.parse();
			return xmlCategoryMembersParser.getPagesList();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
