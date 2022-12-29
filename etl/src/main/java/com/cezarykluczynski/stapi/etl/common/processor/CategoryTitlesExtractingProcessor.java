package com.cezarykluczynski.stapi.etl.common.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryTitlesExtractingProcessor implements ItemProcessor<List<CategoryHeader>, List<String>> {

	@NonNull
	public List<String> process(List<CategoryHeader> item) {
		List<CategoryHeader> categoryHeaderList = CollectionUtils.isEmpty(item) ? Lists.newArrayList() : item;

		return categoryHeaderList.stream()
				.map(CategoryHeader::getTitle)
				.map(s -> s.replace(" ", "_"))
				.collect(Collectors.toList());
	}

}
