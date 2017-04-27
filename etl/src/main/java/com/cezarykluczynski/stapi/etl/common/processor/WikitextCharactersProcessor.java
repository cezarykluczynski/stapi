package com.cezarykluczynski.stapi.etl.common.processor;

import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Sets;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Service
public class WikitextCharactersProcessor implements ItemProcessor<Page, Set<Character>> {

	private static final String CHARACTERS = "Characters";
	private static final String REGULAR_CAST = "Regular Cast";

	private PageSectionExtractor pageSectionExtractor;

	private WikitextApi wikitextApi;

	private EntityLookupByNameService entityLookupByNameService;

	@Inject
	public WikitextCharactersProcessor(PageSectionExtractor pageSectionExtractor, WikitextApi wikitextApi,
			EntityLookupByNameService entityLookupByNameService) {
		this.pageSectionExtractor = pageSectionExtractor;
		this.wikitextApi = wikitextApi;
		this.entityLookupByNameService = entityLookupByNameService;
	}

	@Override
	public Set<Character> process(Page page) throws Exception {
		Set<Character> characters = Sets.newHashSet();

		List<PageSection> charactersPageSectionList = pageSectionExtractor.findByTitlesIncludingSubsections(page, CHARACTERS, REGULAR_CAST);

		charactersPageSectionList.forEach(wikitextList -> {
			List<String> pageTitleList = wikitextApi.getPageTitlesFromWikitext(wikitextList.getWikitext());

			pageTitleList.forEach(pageTitle -> entityLookupByNameService
					.findCharacterByName(pageTitle, MediaWikiSource.MEMORY_ALPHA_EN)
					.ifPresent(characters::add));
		});

		return characters;
	}

}
