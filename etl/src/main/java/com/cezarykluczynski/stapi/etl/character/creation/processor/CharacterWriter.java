package com.cezarykluczynski.stapi.etl.character.creation.processor;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterWriter implements ItemWriter<Character> {

	@Override
	public void write(List<? extends Character> items) throws Exception {
		// TODO
	}

}
