package com.cezarykluczynski.stapi.etl.common.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryTitlesExtractingProcessor implements ItemProcessor<List<CategoryHeader>, List<String>> {

	public List<String> process(List<CategoryHeader> item) {
		List<CategoryHeader> categoryHeaderList = item == null ? Lists.newArrayList() : item;

		return categoryHeaderList.stream()
				.map(CategoryHeader::getTitle)
				.map(s -> s.replace(" ", "_"))
				.collect(Collectors.toList());
	}

}
