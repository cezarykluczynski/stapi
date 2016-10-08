//package com.cezarykluczynski.stapi.wiki.converter;
//
//import com.cezarykluczynski.stapi.wikiapi.dto.CategoryHeader;
//import info.bliki.api.PageInfo;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class CategoryHeaderConverter {
//
//	public static List<CategoryHeader> fromPageInfoList(List<PageInfo> pageInfoList) {
//		return pageInfoList.stream()
//				.map(CategoryHeaderConverter::fromPageInfo)
//				.collect(Collectors.toList());
//	}
//
//	public static CategoryHeader fromPageInfo(PageInfo pageInfo) {
//		return CategoryHeader.builder()
//				.pageId(Long.valueOf(pageInfo.getPageid()))
//				.title(pageInfo.getTitle())
//				.build();
//	}
//
//}
