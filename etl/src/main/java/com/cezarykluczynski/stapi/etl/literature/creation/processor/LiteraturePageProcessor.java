package com.cezarykluczynski.stapi.etl.literature.creation.processor;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.literature.creation.service.LiteraturePageFilter;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.literature.entity.Literature;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiteraturePageProcessor implements ItemProcessor<Page, Literature> {

	private static final String THEORETICAL = "(Theoretical)";
	private static final String OMEGA_IV = "(Omega IV)";

	private final LiteraturePageFilter literaturePageFilter;

	private final PageBindingService pageBindingService;

	private final UidGenerator uidGenerator;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	private final TemplateFinder templateFinder;

	public LiteraturePageProcessor(LiteraturePageFilter literaturePageFilter, PageBindingService pageBindingService,
			UidGenerator uidGenerator, CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor, TemplateFinder templateFinder) {
		this.literaturePageFilter = literaturePageFilter;
		this.pageBindingService = pageBindingService;
		this.uidGenerator = uidGenerator;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
		this.templateFinder = templateFinder;
	}

	@Override
	public Literature process(Page item) throws Exception {
		if (literaturePageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		Literature literature = new Literature();
		String pageTitle = item.getTitle();
		literature.setTitle(StringUtils.endsWithAny(pageTitle, THEORETICAL, OMEGA_IV) ? pageTitle : TitleUtil.getNameFromTitle(pageTitle));

		literature.setPage(pageBindingService.fromPageToPageEntity(item));
		literature.setUid(uidGenerator.generateFromPage(literature.getPage(), Literature.class));

		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(item.getCategories());

		literature.setShakespeareanWork(categoryTitleList.contains(CategoryTitle.SHAKESPEAREAN_WORKS));
		literature.setEarthlyOrigin(literature.getShakespeareanWork() || categoryTitleList.contains(CategoryTitle.EARTH_LITERATURE));
		literature.setReport(categoryTitleList.contains(CategoryTitle.REPORTS));
		literature.setScientificLiterature(categoryTitleList.contains(CategoryTitle.SCIENTIFIC_LITERATURE)
				|| categoryTitleList.contains(CategoryTitle.SCIENTIFIC_LITERATURE_RETCONNED));
		literature.setTechnicalManual(categoryTitleList.contains(CategoryTitle.TECHNICAL_MANUALS));
		literature.setReligiousLiterature(templateFinder.findTemplate(item, TemplateTitle.RELIGIOUS_TEXTS).isPresent());

		return literature;
	}

}
