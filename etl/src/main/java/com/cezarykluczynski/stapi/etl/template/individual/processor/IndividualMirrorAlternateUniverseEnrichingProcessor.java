package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.TemplateName;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndividualMirrorAlternateUniverseEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, IndividualTemplate>> {

	private static final String MIRROR = "(mirror)";

	private static final String ALTERNATE_REALITY = "(alternate reality)";

	private TemplateFinder templateFinder;

	@Inject
	public IndividualMirrorAlternateUniverseEnrichingProcessor(TemplateFinder templateFinder) {
		this.templateFinder = templateFinder;
	}

	@Override
	public void enrich(EnrichablePair<Page, IndividualTemplate> enrichablePair) throws Exception {
		Page page = enrichablePair.getInput();
		IndividualTemplate individualTemplate = enrichablePair.getOutput();
		String pageTitle = page.getTitle();
		List<String> categoryNameList = page.getCategories()
				.stream()
				.map(CategoryHeader::getTitle)
				.collect(Collectors.toList());

		if (pageTitle.contains(MIRROR)) {
			individualTemplate.setMirror(true);
		}

		if (templateFinder.findTemplate(page, TemplateName.MIRROR).isPresent()) {
			individualTemplate.setMirror(true);
		}

		if (categoryNameList.contains(CategoryName.MIRROR_UNIVERSE_INHABITANTS)) {
			individualTemplate.setMirror(true);
		}

		if (pageTitle.contains(ALTERNATE_REALITY)) {
			individualTemplate.setAlternateReality(true);
		}

		if (templateFinder.findTemplate(page, TemplateName.ALT_REALITY).isPresent()) {
			individualTemplate.setAlternateReality(true);
		}

		if (categoryNameList.stream().anyMatch(categoryName -> categoryName.contains(ALTERNATE_REALITY))) {
			individualTemplate.setAlternateReality(true);
		}
	}

}
