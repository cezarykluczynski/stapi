package com.cezarykluczynski.stapi.server.character.query;

import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CharacterRestQuery {

	private final CharacterBaseRestMapper characterRequestMapper;

	private final PageMapper pageMapper;

	private final CharacterRepository characterRepository;

	public CharacterRestQuery(CharacterBaseRestMapper characterBaseRestMapper, PageMapper pageMapper, CharacterRepository characterRepository) {
		this.characterRequestMapper = characterBaseRestMapper;
		this.pageMapper = pageMapper;
		this.characterRepository = characterRepository;
	}

	public Page<Character> query(CharacterRestBeanParams characterRestBeanParams) {
		CharacterRequestDTO characterRequestDTO = characterRequestMapper.mapBase(characterRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(characterRestBeanParams);
		return characterRepository.findMatching(characterRequestDTO, pageRequest);
	}


}
