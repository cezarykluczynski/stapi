package com.cezarykluczynski.stapi.etl

import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.google.common.collect.Lists

class EtlTestUtils {

	public static List<CategoryHeader> createCategoryHeaderList(String title) {
		return Lists.newArrayList(new CategoryHeader(title: title))
	}

}
