package com.cezarykluczynski.stapi.etl.template.book.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.CategoryHeader;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesBookTemplateEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<List<CategoryHeader>, BookTemplate>> {

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public CategoriesBookTemplateEnrichingProcessor(CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<List<CategoryHeader>, BookTemplate> enrichablePair) throws Exception {
		List<CategoryHeader> categoryHeaderList = enrichablePair.getInput();
		BookTemplate bookTemplate = enrichablePair.getOutput();

		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(categoryHeaderList);

		bookTemplate.setNovel(categoryTitleList.contains(CategoryTitle.NOVELS));
		bookTemplate.setReferenceBook(categoryTitleList.contains(CategoryTitle.REFERENCE_BOOKS)
				|| categoryTitleList.contains(CategoryTitle.PRODUCTION_USE_DOCUMENTS));
		bookTemplate.setBiographyBook(categoryTitleList.contains(CategoryTitle.BIOGRAPHY_BOOKS));
		bookTemplate.setRolePlayingBook(categoryTitleList.contains(CategoryTitle.ROLE_PLAYING_GAMES));
		bookTemplate.setEBook(categoryTitleList.contains(CategoryTitle.E_BOOKS));
		bookTemplate.setAnthology(categoryTitleList.contains(CategoryTitle.ANTHOLOGIES));
		bookTemplate.setNovelization(categoryTitleList.contains(CategoryTitle.NOVELIZATIONS));
		bookTemplate.setUnauthorizedPublication(categoryTitleList.contains(CategoryTitle.UNAUTHORIZED_PUBLICATIONS));
		bookTemplate.setAudiobook(categoryTitleList.contains(CategoryTitle.AUDIOBOOKS)
				|| categoryTitleList.contains(CategoryTitle.AUDIO_DRAMAS));
		bookTemplate.setAudiobookAbridged(false);
	}

}
