package com.cezarykluczynski.stapi.etl.character.link.processor;

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationsMap;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class CharacterLinkCacheStoringProcessor implements ItemProcessor<Page, CharacterRelationsMap> {

	@Override
	public CharacterRelationsMap process(Page item) throws Exception {
		// TODO
		return null;
	}

}
