package com.cezarykluczynski.stapi.etl.occupation.creation.processor;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.occupation.creation.service.OccupationPageFilter;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OccupationPageProcessor implements ItemProcessor<Page, Occupation> {

	private final OccupationPageFilter occupationPageFilter;

	private final PageBindingService pageBindingService;

	private final UidGenerator uidGenerator;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public OccupationPageProcessor(OccupationPageFilter occupationPageFilter, PageBindingService pageBindingService, UidGenerator uidGenerator,
			CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.occupationPageFilter = occupationPageFilter;
		this.pageBindingService = pageBindingService;
		this.uidGenerator = uidGenerator;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public Occupation process(Page item) throws Exception {
		if (occupationPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		Occupation occupation = new Occupation();
		occupation.setName(TitleUtil.getNameFromTitle(item.getTitle()));

		occupation.setPage(pageBindingService.fromPageToPageEntity(item));
		occupation.setUid(uidGenerator.generateFromPage(occupation.getPage(), Occupation.class));

		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(item.getCategories());

		occupation.setLegalOccupation(categoryTitleList.contains(CategoryTitle.LEGAL_OCCUPATIONS));
		occupation.setMedicalOccupation(categoryTitleList.contains(CategoryTitle.MEDICAL_OCCUPATIONS));
		occupation.setScientificOccupation(categoryTitleList.contains(CategoryTitle.SCIENTIFIC_OCCUPATIONS));

		return occupation;
	}

}
