package com.cezarykluczynski.stapi.etl.template.comics.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.WikitextList;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.etl.common.service.WikitextListsExtractor;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ComicsTemplateWikitextCharactersEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<String, ComicsTemplate>> {

	private WikitextListsExtractor wikitextListsExtractor;

	private WikitextApi wikitextApi;

	private EntityLookupByNameService entityLookupByNameService;

	public ComicsTemplateWikitextCharactersEnrichingProcessor(WikitextListsExtractor wikitextListsExtractor, WikitextApi wikitextApi,
			EntityLookupByNameService entityLookupByNameService) {
		this.wikitextListsExtractor = wikitextListsExtractor;
		this.wikitextApi = wikitextApi;
		this.entityLookupByNameService = entityLookupByNameService;
	}

	@Override
	public void enrich(EnrichablePair<String, ComicsTemplate> enrichablePair) throws Exception {
		String pageSectionWikitext = enrichablePair.getInput();
		ComicsTemplate comicsTemplate = enrichablePair.getOutput();
		List<WikitextList> wikitextListList = wikitextListsExtractor.extractDefinitionsFromWikitext(pageSectionWikitext);
		Set<Character> characters = comicsTemplate.getCharacters();

		wikitextListList.forEach(wikitextList -> {
			List<String> pageTitleList = wikitextApi.getPageTitlesFromWikitext(wikitextList.getText());

			if (pageTitleList.isEmpty()) {
				return;
			}

			entityLookupByNameService
					.findCharacterByName(pageTitleList.get(0), MediaWikiSource.MEMORY_ALPHA_EN)
					.ifPresent(characters::add);
		});

		if (characters.isEmpty() && !wikitextListList.isEmpty()) {
			log.info("No characters found for {}", comicsTemplate.getTitle());
		}
	}

}
