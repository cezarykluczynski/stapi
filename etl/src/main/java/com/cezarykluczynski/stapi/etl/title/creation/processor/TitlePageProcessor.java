package com.cezarykluczynski.stapi.etl.title.creation.processor;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.title.creation.service.RanksTemplateService;
import com.cezarykluczynski.stapi.etl.title.creation.service.TitlePageFilter;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitlePageProcessor implements ItemProcessor<Page, Title> {

	private final TitlePageFilter titlePageFilter;

	private final PageBindingService pageBindingService;

	private final UidGenerator uidGenerator;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	private final RanksTemplateService ranksTemplateService;

	public TitlePageProcessor(TitlePageFilter titlePageFilter, PageBindingService pageBindingService, UidGenerator uidGenerator,
			CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor, RanksTemplateService ranksTemplateService) {
		this.titlePageFilter = titlePageFilter;
		this.pageBindingService = pageBindingService;
		this.uidGenerator = uidGenerator;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
		this.ranksTemplateService = ranksTemplateService;
	}

	@Override
	public Title process(Page item) throws Exception {
		if (titlePageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		Title title = new Title();

		title.setName(TitleUtil.getNameFromTitle(item.getTitle()));
		title.setPage(pageBindingService.fromPageToPageEntity(item));
		title.setUid(uidGenerator.generateFromPage(title.getPage(), Title.class));

		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(item.getCategories());
		String pageTitle = item.getTitle();

		title.setMilitaryRank(categoryTitleList.contains(CategoryTitle.MILITARY_RANKS) || ranksTemplateService.isMilitaryRank(pageTitle));
		title.setReligiousTitle(categoryTitleList.contains(CategoryTitle.RELIGIOUS_TITLES));
		title.setFleetRank(ranksTemplateService.isFleetRank(pageTitle));
		title.setPosition(ranksTemplateService.isPosition(pageTitle));
		title.setMirror(categoryTitleList.contains(CategoryTitle.MIRROR_UNIVERSE));

		return title;
	}

}
