package com.cezarykluczynski.stapi.etl.mediawiki.api;

import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.etl.mediawiki.connector.bliki.BlikiConnector;
import com.cezarykluczynski.stapi.etl.mediawiki.converter.PageHeaderConverter;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.CategoryHeader;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.etl.mediawiki.parser.XMLCategoryMembersParser;
import com.cezarykluczynski.stapi.etl.mediawiki.util.constant.ApiParams;
import com.cezarykluczynski.stapi.etl.mediawiki.util.constant.MemoryAlpha;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import info.bliki.api.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CategoryApiImpl implements CategoryApi {

	private final BlikiConnector blikiConnector;

	private final PageHeaderConverter pageHeaderConverter;

	public CategoryApiImpl(BlikiConnector blikiConnector, PageHeaderConverter pageHeaderConverter) {
		this.blikiConnector = blikiConnector;
		this.pageHeaderConverter = pageHeaderConverter;
	}

	@Override
	public List<PageHeader> getPages(String title, MediaWikiSource mediaWikiSource) {
		List<PageInfo> pageInfoList = getPageInfoList(title, mediaWikiSource);
		assertCategoryNotEmpty(pageInfoList, title, mediaWikiSource);
		return pageHeaderConverter.fromPageInfoList(pageInfoList, mediaWikiSource);
	}

	@Override
	public List<PageHeader> getPages(List<String> titleList, MediaWikiSource mediaWikiSource) {
		Set<PageHeader> pageHeaderSet = Sets.newHashSet();
		titleList.forEach(title -> pageHeaderSet.addAll(getPages(title, mediaWikiSource)));
		return Lists.newArrayList(pageHeaderSet);
	}

	@Override
	public List<PageHeader> getPagesIncludingSubcategories(String title, MediaWikiSource mediaWikiSource) {
		return getPagesIncludingSubcategories(Lists.newArrayList(title), mediaWikiSource);
	}

	@Override
	public List<PageHeader> getPagesIncludingSubcategories(String title, int depth, MediaWikiSource mediaWikiSource) {
		return getPagesIncludingSubcategories(Lists.newArrayList(title), depth, mediaWikiSource);
	}

	@Override
	public List<PageHeader> getPagesIncludingSubcategories(List<String> titleList, MediaWikiSource mediaWikiSource) {
		List<PageInfo> pageInfoIncludingSubcategoriesList = privateGetPagesIncludingSubcategories(titleList, mediaWikiSource,
				Lists.newArrayList(titleList), Lists.newArrayList(), 0, 1);
		pageInfoIncludingSubcategoriesList = Lists.newArrayList(Sets.newHashSet(pageInfoIncludingSubcategoriesList));
		assertCategoryNotEmpty(pageInfoIncludingSubcategoriesList, titleList, mediaWikiSource);
		return pageHeaderConverter.fromPageInfoList(pageInfoIncludingSubcategoriesList, mediaWikiSource);
	}

	@Override
	public List<PageHeader> getPagesIncludingSubcategories(List<String> titleList, int depth, MediaWikiSource mediaWikiSource) {
		List<PageInfo> pageInfoIncludingSubcategoriesList = privateGetPagesIncludingSubcategories(titleList, mediaWikiSource,
				Lists.newArrayList(titleList), Lists.newArrayList(), 0, 1);
		pageInfoIncludingSubcategoriesList = Lists.newArrayList(Sets.newHashSet(pageInfoIncludingSubcategoriesList));
		assertCategoryNotEmpty(pageInfoIncludingSubcategoriesList, titleList, mediaWikiSource);
		return pageHeaderConverter.fromPageInfoList(pageInfoIncludingSubcategoriesList, mediaWikiSource);
	}

	@Override
	public List<PageHeader> getPagesIncludingSubcategoriesExcept(String title, List<String> exceptions, MediaWikiSource mediaWikiSource) {
		List<String> visitedCategoriesNames = Lists.newArrayList(exceptions);
		visitedCategoriesNames.add(title);
		List<PageInfo> pageInfoIncludingSubcategoriesList = privateGetPagesIncludingSubcategories(Lists.newArrayList(title), mediaWikiSource,
				visitedCategoriesNames, Lists.newArrayList(), 0, 1);
		pageInfoIncludingSubcategoriesList = Lists.newArrayList(Sets.newHashSet(pageInfoIncludingSubcategoriesList));
		assertCategoryNotEmpty(pageInfoIncludingSubcategoriesList, title, mediaWikiSource);
		return pageHeaderConverter.fromPageInfoList(pageInfoIncludingSubcategoriesList, mediaWikiSource);
	}

	@Override
	public List<PageHeader> getPagesIncludingSubcategoriesExcluding(String title, List<Pattern> excludes, int depth,
			MediaWikiSource mediaWikiSource) {
		List<String> visitedCategoriesNames = Lists.newArrayList();
		visitedCategoriesNames.add(title);
		List<PageInfo> pageInfoIncludingSubcategoriesList = privateGetPagesIncludingSubcategories(Lists.newArrayList(title), mediaWikiSource,
				visitedCategoriesNames, excludes, 0, depth);
		pageInfoIncludingSubcategoriesList = Lists.newArrayList(Sets.newHashSet(pageInfoIncludingSubcategoriesList));
		assertCategoryNotEmpty(pageInfoIncludingSubcategoriesList, title, mediaWikiSource);
		return pageHeaderConverter.fromPageInfoList(pageInfoIncludingSubcategoriesList, mediaWikiSource);
	}

	@Override
	public List<CategoryHeader> getCategoriesInCategory(String categoryTitle, MediaWikiSource mediaWikiSource) {
		return blikiConnector.getCategories(categoryTitle, mediaWikiSource, 1);
	}

	@Override
	public List<CategoryHeader> getCategoriesInCategoryIncludingSubcategories(String categoryTitle, MediaWikiSource mediaWikiSource,
			List<String> excluding) {
		List<CategoryHeader> categoriesInCategoryForIteration = getCategoriesInCategory(categoryTitle, mediaWikiSource);
		List<CategoryHeader> categoriesInCategory = Lists.newArrayList();
		categoriesInCategory.addAll(categoriesInCategoryForIteration);
		for (CategoryHeader categoryHeader : categoriesInCategoryForIteration) {
			if (excluding.contains(categoryHeader.getTitle())) {
				continue;
			}
			excluding.add(categoryHeader.getTitle());
			final List<CategoryHeader> subcategories = getCategoriesInCategoryIncludingSubcategories(
					categoryHeader.getTitle(), mediaWikiSource, excluding);
			categoriesInCategory.addAll(subcategories);
		}

		return categoriesInCategory.stream()
				.distinct()
				.sorted(Comparator.comparing(CategoryHeader::getTitle))
				.collect(Collectors.toList());
	}

	private List<PageInfo> privateGetPagesIncludingSubcategories(List<String> titleList, MediaWikiSource mediaWikiSource,
			List<String> visitedCategoriesNames, List<Pattern> excludes, int currentNesting, int maxNesting) {
		if (currentNesting > maxNesting) {
			return Lists.newArrayList();
		}
		List<PageInfo> pageInfoList = getPageInfoList(filterWithExcludes(titleList, excludes), mediaWikiSource);
		List<PageInfo> pages = Lists.newArrayList();

		for (PageInfo pageInfo : pageInfoList) {
			String namespace = pageInfo.getNs();
			String categoryTitle = toQueryableCategoryName(pageInfo.getTitle());
			if (MemoryAlpha.CONTENT_NAMESPACE.equals(namespace)) {
				pages.add(pageInfo);
			} else if (MemoryAlpha.CATEGORY_NAMESPACE.equals(namespace) && !visitedCategoriesNames.contains(categoryTitle)) {
				visitedCategoriesNames.add(categoryTitle);
				pages.addAll(privateGetPagesIncludingSubcategories(Lists.newArrayList(categoryTitle), mediaWikiSource, visitedCategoriesNames,
						excludes, currentNesting + 1, maxNesting));
			}
		}

		return pages;
	}

	private List<String> filterWithExcludes(List<String> titleList, List<Pattern> excludes) {
		return titleList.stream()
				.filter(title -> {
					for (Pattern pattern : excludes) {
						if (pattern.matcher(title).matches()) {
							return false;
						}
					}
					return true;
				})
				.collect(Collectors.toList());
	}

	private List<PageInfo> getPageInfoList(List<String> titleList, MediaWikiSource mediaWikiSource) {
		List<PageInfo> pageInfoList = Lists.newArrayList();
		titleList.forEach(title -> pageInfoList.addAll(getPageInfoList(title, mediaWikiSource)));
		return pageInfoList;
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
			throw new StapiRuntimeException(e);
		}
	}

	private String toQueryableCategoryName(String categoryName) {
		return StringUtils.substringAfter(categoryName.replaceAll(" ", "_"), ":");
	}

	private void assertCategoryNotEmpty(List<PageInfo> pageInfoList, String title, MediaWikiSource mediaWikiSource) {
		if (pageInfoList.isEmpty()) {
			throw new StapiRuntimeException(String.format("Expected pages in category %s (source %s), but none were found.", title, mediaWikiSource));
		}
	}

	private void assertCategoryNotEmpty(List<PageInfo> pageInfoList, List<String> titleList, MediaWikiSource mediaWikiSource) {
		if (pageInfoList.isEmpty()) {
			throw new StapiRuntimeException(String.format("Expected pages in categories %s (source %s), but none were found.",
					titleList, mediaWikiSource));
		}
	}

}
