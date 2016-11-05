package com.cezarykluczynski.stapi.sources.mediawiki.api;

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki.BlikiConnector;
import com.cezarykluczynski.stapi.sources.mediawiki.converter.PageHeaderConverter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.sources.mediawiki.parser.XMLCategoryMembersParser;
import com.cezarykluczynski.stapi.sources.mediawiki.util.constant.ApiParams;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import info.bliki.api.PageInfo;
import org.apache.commons.lang3.StringUtils;
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
	public List<PageHeader> getPages(String title, MediaWikiSource mediaWikiSource) {
		Map<String, String> params = getInitialParams(title);
		List<PageInfo> pageInfoList = Lists.newArrayList();

		String lastCmContinue = null;

		do {
			String xml = blikiConnector.readXML(params);
			XMLCategoryMembersParser xmlCategoryMembersParser = parsePageInfo(xml);
			pageInfoList.addAll(xmlCategoryMembersParser.getPagesList());
			String cmContinue = xmlCategoryMembersParser.getCmContinue();

			if (StringUtils.isNotBlank(cmContinue) && (lastCmContinue == null || !cmContinue.equals(lastCmContinue))) {
				params.put(ApiParams.KEY_CATEGORY_CONTINIUE, cmContinue);
				lastCmContinue = cmContinue;
			} else {
				break;
			}
		} while (true);

		return pageHeaderConverter.fromPageInfoList(pageInfoList, mediaWikiSource);
	}

	private Map<String, String> getInitialParams(String title) {
		Map<String, String> params = Maps.newHashMap();
		params.put(ApiParams.KEY_LIST, ApiParams.KEY_LIST_VALUE_CATEGORYMEMBERS);
		params.put(ApiParams.KEY_CATEGORY_TITLE, ApiParams.KEY_CATEGORY_TITLE_VALUE_PREFIX + title);
		params.put(ApiParams.KEY_CATEGORY_LIMIT, ApiParams.KEY_CATEGORY_LIMIT_VALUE);
		return params;
	}

	private XMLCategoryMembersParser parsePageInfo(String xml) {
		try {
			XMLCategoryMembersParser xmlCategoryMembersParser = new XMLCategoryMembersParser(xml);
			xmlCategoryMembersParser.parse();
			return xmlCategoryMembersParser;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
