package com.cezarykluczynski.stapi.etl.character.creation.processor;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacterWriter implements ItemWriter<Character> {

	private final CharacterRepository characterRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public CharacterWriter(CharacterRepository characterRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.characterRepository = characterRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends Character> items) throws Exception {
		characterRepository.save(process(items));
	}

	private List<Character> process(List<? extends Character> characterList) {
		List<Character> characterListWithoutExtends = fromExtendsListToCharacterList(characterList);
		return filterDuplicates(characterListWithoutExtends);
	}

	private List<Character> fromExtendsListToCharacterList(List<? extends Character> characterList) {
		return characterList
				.stream()
				.map(pageAware -> (Character) pageAware)
				.collect(Collectors.toList());
	}

	private List<Character> filterDuplicates(List<Character> characterList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(characterList.stream()
				.map(character -> (PageAware) character)
				.collect(Collectors.toList()), Character.class).stream()
				.map(pageAware -> (Character) pageAware)
				.collect(Collectors.toList());
	}

}
