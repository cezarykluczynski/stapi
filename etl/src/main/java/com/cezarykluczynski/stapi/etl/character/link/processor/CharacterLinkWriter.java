package com.cezarykluczynski.stapi.etl.character.link.processor;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class CharacterLinkWriter implements ItemWriter<Character> {

	@Override
	public void write(Chunk<? extends Character> items) throws Exception {
		// do nothing
	}

}
