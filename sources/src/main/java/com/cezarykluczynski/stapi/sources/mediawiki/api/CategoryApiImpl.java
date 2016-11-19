package com.cezarykluczynski.stapi.sources.mediawiki.api;

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki.BlikiConnector;
import com.cezarykluczynski.stapi.sources.mediawiki.converter.PageHeaderConverter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.sources.mediawiki.parser.XMLCategoryMembersParser;
import com.cezarykluczynski.stapi.sources.mediawiki.util.constant.ApiParams;
import com.cezarykluczynski.stapi.sources.mediawiki.util.constant.MemoryAlpha;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
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
		List<PageInfo> pageInfoList = getPageInfoList(title, mediaWikiSource);
		return pageHeaderConverter.fromPageInfoList(pageInfoList, mediaWikiSource);
	}

	@Override
	public List<PageHeader> getPagesIncludingSubcategories(String title, MediaWikiSource mediaWikiSource) {
		List<PageInfo> pageInfoIncludingSubcategoriesList = getPagesIncludingSubcategories(
				title, mediaWikiSource, Lists.newArrayList(title));
		pageInfoIncludingSubcategoriesList = Lists.newArrayList(Sets.newHashSet(pageInfoIncludingSubcategoriesList));
		return pageHeaderConverter.fromPageInfoList(pageInfoIncludingSubcategoriesList, mediaWikiSource);
	}

	private List<PageInfo> getPagesIncludingSubcategories(String title, MediaWikiSource mediaWikiSource,
			List<String> visitedCategoriesNames) {
		List<PageInfo> pageInfoList = getPageInfoList(title, mediaWikiSource);
		List<PageInfo> pages = Lists.newArrayList();

		for (PageInfo pageInfo : pageInfoList) {
			String namespace = pageInfo.getNs();
			String categoryTitle = toQueryableCategoryName(pageInfo.getTitle());
			if (MemoryAlpha.CONTENT_NAMESPACE.equals(namespace)) {
				pages.add(pageInfo);
			} else if (MemoryAlpha.CATEGORY_NAMESPACE.equals(namespace)) {
				if (!visitedCategoriesNames.contains(categoryTitle)) {
					visitedCategoriesNames.add(categoryTitle);
					pages.addAll(getPagesIncludingSubcategories(categoryTitle,
							mediaWikiSource, visitedCategoriesNames));
				}
			}
		}

		return pages;
	}

	private List<PageInfo> getPageInfoList(String title, MediaWikiSource mediaWikiSource) {
		Map<String, String> params = getCategoryMembersInitialParams(title);
		List<PageInfo> pageInfoList = Lists.newArrayList();

		String lastCmContinue = null;

		do {
			String xml = blikiConnector.readXML(params, mediaWikiSource);
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

		return pageInfoList;
	}

	private Map<String, String> getCategoryMembersInitialParams(String title) {
		Map<String, String> params = Maps.newHashMap();
		params.put(ApiParams.KEY_CATEGORY_TITLE, ApiParams.KEY_CATEGORY_TITLE_VALUE_PREFIX + title);
		params.put(ApiParams.KEY_CATEGORY_LIMIT, ApiParams.KEY_CATEGORY_LIMIT_VALUE);
		params.put(ApiParams.KEY_LIST, ApiParams.KEY_LIST_VALUE_CATEGORYMEMBERS);
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

	private String toQueryableCategoryName(String categoryName) {
		return StringUtils.substringAfter(categoryName.replaceAll(" ", "_"), ":");
	}


}
