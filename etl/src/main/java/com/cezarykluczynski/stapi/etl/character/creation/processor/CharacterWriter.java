package com.cezarykluczynski.stapi.etl.character.creation.processor;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class CharacterWriter implements ItemWriter<Character> {

	private final CharacterRepository characterRepository;

	public CharacterWriter(CharacterRepository characterRepository) {
		this.characterRepository = characterRepository;
	}

	@Override
	public void write(Chunk<? extends Character> items) throws Exception {
		characterRepository.saveAll(items.getItems());
	}

}
