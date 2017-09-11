package com.cezarykluczynski.stapi.etl.common.processor.character;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WikitextToCharactersProcessor implements ItemProcessor<String, List<Character>> {

	private final WikitextApi wikitextApi;

	private final CharacterRepository characterRepository;

	@Inject
	public WikitextToCharactersProcessor(WikitextApi wikitextApi, CharacterRepository characterRepository) {
		this.wikitextApi = wikitextApi;
		this.characterRepository = characterRepository;
	}

	@Override
	public List<Character> process(String item) throws Exception {
		List<String> pageLinkTitleList = wikitextApi.getPageTitlesFromWikitext(item);

		return pageLinkTitleList.stream()
				.map(pageLinkTitle -> characterRepository.findByPageTitleAndPageMediaWikiSource(pageLinkTitle, MediaWikiSource.MEMORY_ALPHA_EN))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
	}

}
