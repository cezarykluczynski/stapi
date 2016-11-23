package com.cezarykluczynski.stapi.server.character.query;

import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterRequestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class CharacterRestQuery {

	private CharacterRequestMapper characterRequestMapper;

	private PageMapper pageMapper;

	private CharacterRepository characterRepository;

	@Inject
	public CharacterRestQuery(CharacterRequestMapper characterRequestMapper, PageMapper pageMapper,
			CharacterRepository characterRepository) {
		this.characterRequestMapper = characterRequestMapper;
		this.pageMapper = pageMapper;
		this.characterRepository = characterRepository;
	}

	public Page<Character> query(CharacterRestBeanParams characterRestBeanParams) {
		CharacterRequestDTO characterRequestDTO = characterRequestMapper.map(characterRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageAwareBeanParamsToPageRequest(characterRestBeanParams);
		return characterRepository.findMatching(characterRequestDTO, pageRequest);
	}


}
