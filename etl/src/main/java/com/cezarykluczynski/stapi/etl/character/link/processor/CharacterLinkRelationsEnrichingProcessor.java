package com.cezarykluczynski.stapi.etl.character.link.processor;

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationsMap;
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import org.springframework.stereotype.Service;

@Service
public class CharacterLinkRelationsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<CharacterRelationsMap, Character>> {

	@Override
	public void enrich(EnrichablePair<CharacterRelationsMap, Character> enrichablePair) throws Exception {
		// TODO
	}

}
