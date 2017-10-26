package com.cezarykluczynski.stapi.etl.template.comic_series.processor;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComicSeriesTemplatePhotonovelSeriesProcessor implements ItemProcessor<Page, Boolean> {

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public ComicSeriesTemplatePhotonovelSeriesProcessor(CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public Boolean process(Page item) throws Exception {
		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(item.getCategories());
		return categoryTitleList.contains(CategoryTitle.PHOTONOVEL_SERIES);
	}

}
