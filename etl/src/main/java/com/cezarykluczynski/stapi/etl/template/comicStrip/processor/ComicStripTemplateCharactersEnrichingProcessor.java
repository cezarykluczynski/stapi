package com.cezarykluczynski.stapi.etl.template.comicStrip.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor;
import com.cezarykluczynski.stapi.etl.template.comicStrip.dto.ComicStripTemplate;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Service
public class ComicStripTemplateCharactersEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, ComicStripTemplate>> {

	private static final String CHARACTERS = "Characters";
	private static final String REGULAR_CAST = "Regular Cast";

	private PageSectionExtractor pageSectionExtractor;

	private WikitextApi wikitextApi;

	private EntityLookupByNameService entityLookupByNameService;

	@Inject
	public ComicStripTemplateCharactersEnrichingProcessor(PageSectionExtractor pageSectionExtractor, WikitextApi wikitextApi,
			EntityLookupByNameService entityLookupByNameService) {
		this.pageSectionExtractor = pageSectionExtractor;
		this.wikitextApi = wikitextApi;
		this.entityLookupByNameService = entityLookupByNameService;
	}

	@Override
	public void enrich(EnrichablePair<Page, ComicStripTemplate> enrichablePair) throws Exception {
		Page page = enrichablePair.getInput();
		ComicStripTemplate comicStripTemplate = enrichablePair.getOutput();
		Set<Character> characters = comicStripTemplate.getCharacters();

		List<PageSection> charactersPageSectionList = pageSectionExtractor.findByTitlesIncludingSubsections(page, CHARACTERS, REGULAR_CAST);

		charactersPageSectionList.forEach(wikitextList -> {
			List<String> pageTitleList = wikitextApi.getPageTitlesFromWikitext(wikitextList.getWikitext());

			pageTitleList.forEach(pageTitle -> entityLookupByNameService
						.findCharacterByName(pageTitle, MediaWikiSource.MEMORY_ALPHA_EN)
						.ifPresent(characters::add));
		});
	}

}
