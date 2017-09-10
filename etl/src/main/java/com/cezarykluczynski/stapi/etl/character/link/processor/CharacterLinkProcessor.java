package com.cezarykluczynski.stapi.etl.character.link.processor;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class CharacterLinkProcessor implements ItemProcessor<PageHeader, Character> {

	@Override
	public Character process(PageHeader item) throws Exception {
		// TODO
		return null;
	}

}
