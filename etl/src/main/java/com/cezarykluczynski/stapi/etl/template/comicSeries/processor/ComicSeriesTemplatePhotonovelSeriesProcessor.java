package com.cezarykluczynski.stapi.etl.template.comicSeries.processor;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class ComicSeriesTemplatePhotonovelSeriesProcessor implements ItemProcessor<Page, Boolean> {

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	@Inject
	public ComicSeriesTemplatePhotonovelSeriesProcessor(CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public Boolean process(Page item) throws Exception {
		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(item.getCategories());
		return categoryTitleList.contains(CategoryName.PHOTONOVEL_SERIES);
	}

}
