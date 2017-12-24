package com.cezarykluczynski.stapi.etl.template.common.processor.gender;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender;
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualTemplatePartsGenderProcessor;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PageToGenderRoleProcessor implements ItemProcessor<Page, Gender> {

	private final PageApi pageApi;

	private final WikitextApi wikitextApi;

	private final TemplateFinder templateFinder;

	private final IndividualTemplatePartsGenderProcessor individualTemplatePartsGenderProcessor;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public PageToGenderRoleProcessor(PageApi pageApi, WikitextApi wikitextApi, TemplateFinder templateFinder,
			IndividualTemplatePartsGenderProcessor individualTemplatePartsGenderProcessor,
			CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.pageApi = pageApi;
		this.wikitextApi = wikitextApi;
		this.templateFinder = templateFinder;
		this.individualTemplatePartsGenderProcessor = individualTemplatePartsGenderProcessor;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public Gender process(Page item) throws Exception {
		String wikitext = item.getWikitext();

		int position = Optional.ofNullable(wikitext).orElse("").indexOf("played");

		if (position == -1) {
			if (isPerformer(item)) {
				log.info("Could not determine gender of \"{}\" from played roles", item.getTitle());
			}
			return null;
		}

		String candidate = wikitext.substring(position, Math.min(position + 200, wikitext.length()));
		List<String> linkedPagesList = wikitextApi.getPageTitlesFromWikitext(candidate);

		if (linkedPagesList.isEmpty()) {
			log.info("Could not extract any links from \"{}\"", item.getTitle());
			return null;
		}

		List<Page> pageList = pageApi.getPages(linkedPagesList, MediaWikiSource.MEMORY_ALPHA_EN);

		for (Page page : pageList) {
			Gender gender = getIndividualTemplateGender(page);
			if (gender != null) {
				log.info("Guessing gender {} of real person \"{}\" based of gender of played character \"{}\"",
						gender, item.getTitle(), page.getTitle());
				return gender;
			} else {
				log.debug("Performer \"{}\" played individual \"{}\", but the latter have no gender specified.",
						item.getTitle(), page.getTitle());
			}
		}

		log.info("Could not guess gender of performer {} from pages {}", item.getTitle(),
				pageList.stream().map(Page::getTitle).collect(Collectors.toList()));

		return null;
	}

	private Gender getIndividualTemplateGender(Page page) throws Exception {
		Optional<Template> templateOptional = templateFinder.findTemplate(page, TemplateTitle.SIDEBAR_INDIVIDUAL);

		if (!templateOptional.isPresent()) {
			return null;
		}

		return individualTemplatePartsGenderProcessor.process(templateOptional.get().getParts());
	}

	private boolean isPerformer(Page item) {
		List<CategoryHeader> categoryHeaderList = item.getCategories();

		if (CollectionUtils.isEmpty(categoryHeaderList)) {
			return false;
		}

		List<String> categories = categoryTitlesExtractingProcessor.process(categoryHeaderList);
		return !Collections.disjoint(categories, CategoryTitles.PERFORMERS);
	}

}
