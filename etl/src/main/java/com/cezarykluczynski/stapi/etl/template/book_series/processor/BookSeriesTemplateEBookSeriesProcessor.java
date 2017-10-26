package com.cezarykluczynski.stapi.etl.template.book_series.processor;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookSeriesTemplateEBookSeriesProcessor implements ItemProcessor<Page, Boolean> {

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public BookSeriesTemplateEBookSeriesProcessor(CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public Boolean process(Page item) throws Exception {
		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(item.getCategories());
		return categoryTitleList.contains(CategoryTitle.E_BOOK_SERIES);
	}

}
