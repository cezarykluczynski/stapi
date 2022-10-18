package com.cezarykluczynski.stapi.etl.util;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortingUtil {

	public static List<PageHeader> sortedUnique(Collection<PageHeader> pageHeaders) {
		return Lists.newArrayList(pageHeaders).stream()
				.distinct()
				.sorted(Comparator.comparing(PageHeader::getTitle))
				.collect(Collectors.toList());
	}

}
