package com.cezarykluczynski.stapi.etl.template.common.processor.gender;

import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate;
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualTemplatePageProcessor;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryNames;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PageToGenderRoleProcessor implements ItemProcessor<Page, Gender> {

	private PageApi pageApi;

	private WikitextApi wikitextApi;

	private IndividualTemplatePageProcessor individualTemplatePageProcessor;

	@Inject
	public PageToGenderRoleProcessor(PageApi pageApi, WikitextApi wikitextApi,
			IndividualTemplatePageProcessor individualTemplatePageProcessor) {
		this.pageApi = pageApi;
		this.wikitextApi = wikitextApi;
		this.individualTemplatePageProcessor = individualTemplatePageProcessor;
	}

	@Override
	public Gender process(Page item) throws Exception {
		String wikitext = item.getWikitext();

		int position = Optional.ofNullable(wikitext).orElse("").indexOf("played");

		if (position == -1) {
			if (isPerformer(item)) {
				log.info("Could not determine gender of {} from played roles", item.getTitle());
			}
			return null;
		}

		String candidate = wikitext.substring(position, Math.min(position + 200, wikitext.length()));
		List<String> linkedPagesList = wikitextApi.getPageTitlesFromWikitext(candidate);

		if (linkedPagesList.isEmpty()) {
			log.warn("Could not extract any links from {}", item.getTitle());
			return null;
		}

		List<Page> pageList = pageApi.getPages(linkedPagesList, MediaWikiSource.MEMORY_ALPHA_EN);

		for (Page page : pageList) {
			IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page);
			if (individualTemplate != null) {
				Gender gender = individualTemplate.getGender();
				if (gender != null) {
					log.info("Guessing gender {} of real person {} based of gender of played character {}",
							gender, item.getTitle(), page.getTitle());
					return gender;
				} else {
					log.info("Performer {} played individual {}, but the latter have no gender specified.",
							item.getTitle(), page.getTitle());
				}
			}
		}

		log.info("Could not guess gender of performer {} from pages {}", item.getTitle(),
				pageList.stream().map(Page::getTitle).collect(Collectors.toList()));

		return null;
	}

	private boolean isPerformer(Page item) {
		List<CategoryHeader> categoryHeaderList = item.getCategories();

		if (CollectionUtils.isEmpty(categoryHeaderList)) {
			return false;
		}

		List<String> categories = categoryHeaderList
				.stream()
				.map(CategoryHeader::getTitle)
				.collect(Collectors.toList());

		return !Collections.disjoint(categories, CategoryNames.PERFORMER);

	}

}
