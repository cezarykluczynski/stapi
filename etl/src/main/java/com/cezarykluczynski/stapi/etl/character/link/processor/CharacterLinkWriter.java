package com.cezarykluczynski.stapi.etl.character.link.processor;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class CharacterLinkWriter implements ItemWriter<Character> {

	private final CharacterRepository characterRepository;

	@Inject
	public CharacterLinkWriter(CharacterRepository characterRepository) {
		this.characterRepository = characterRepository;
	}

	@Override
	public void write(List<? extends Character> items) throws Exception {
		characterRepository.save(items);
	}

}
