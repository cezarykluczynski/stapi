package com.cezarykluczynski.stapi.etl.template.common.processor.gender;

import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender;
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualTemplatePartsGenderProcessor;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PageToGenderRoleProcessor implements ItemProcessor<Page, Gender> {

	private final PageApi pageApi;

	private final WikitextApi wikitextApi;

	private final TemplateFinder templateFinder;

	private final IndividualTemplatePartsGenderProcessor individualTemplatePartsGenderProcessor;

	public PageToGenderRoleProcessor(PageApi pageApi, WikitextApi wikitextApi, TemplateFinder templateFinder,
			IndividualTemplatePartsGenderProcessor individualTemplatePartsGenderProcessor) {
		this.pageApi = pageApi;
		this.wikitextApi = wikitextApi;
		this.templateFinder = templateFinder;
		this.individualTemplatePartsGenderProcessor = individualTemplatePartsGenderProcessor;
	}

	@Override
	public Gender process(Page item) throws Exception {
		String wikitext = item.getWikitext();

		int position = Optional.ofNullable(wikitext).orElse("").indexOf("played");

		if (position == -1) {
			return null;
		}

		String candidate = wikitext.substring(position, Math.min(position + 200, wikitext.length()));
		List<String> linkedPagesList = wikitextApi.getPageTitlesFromWikitext(candidate);

		if (linkedPagesList.isEmpty()) {
			return null;
		}

		List<Page> pageList = pageApi.getPages(linkedPagesList, MediaWikiSource.MEMORY_ALPHA_EN);

		for (Page page : pageList) {
			Gender gender = getIndividualTemplateGender(page);
			if (gender != null) {
				log.info("Guessing gender {} of real person \"{}\" based of gender of played character \"{}\"",
						gender, item.getTitle(), page.getTitle());
				return gender;
			}
		}

		return null;
	}

	private Gender getIndividualTemplateGender(Page page) throws Exception {
		Optional<Template> templateOptional = templateFinder.findTemplate(page, TemplateTitle.SIDEBAR_INDIVIDUAL);

		if (!templateOptional.isPresent()) {
			return null;
		}

		return individualTemplatePartsGenderProcessor.process(templateOptional.get().getParts());
	}

}
