package com.cezarykluczynski.stapi.etl

import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.google.common.collect.Lists

class EtlTestUtils {

	static List<CategoryHeader> createCategoryHeaderList(String title) {
		Lists.newArrayList(new CategoryHeader(title: title))
	}

}
