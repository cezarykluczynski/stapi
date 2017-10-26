package com.cezarykluczynski.stapi.etl.common.processor.character;

import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Sets;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class WikitextSectionsCharactersProcessor implements ItemProcessor<Page, Set<Character>> {

	private static final String CHARACTERS = "Characters";
	private static final String REGULAR_CAST = "Regular Cast";

	private final PageSectionExtractor pageSectionExtractor;

	private final WikitextToEntitiesProcessor wikitextToEntitiesProcessor;

	public WikitextSectionsCharactersProcessor(PageSectionExtractor pageSectionExtractor, WikitextToEntitiesProcessor wikitextToEntitiesProcessor) {
		this.pageSectionExtractor = pageSectionExtractor;
		this.wikitextToEntitiesProcessor = wikitextToEntitiesProcessor;
	}

	@Override
	public Set<Character> process(Page page) throws Exception {
		Set<Character> characters = Sets.newHashSet();

		pageSectionExtractor.findByTitlesIncludingSubsections(page, CHARACTERS, REGULAR_CAST)
				.forEach(wikitextList -> characters.addAll(wikitextToEntitiesProcessor.findCharacters(wikitextList.getWikitext())));

		return characters;
	}

}
