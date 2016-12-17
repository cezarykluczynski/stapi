package com.cezarykluczynski.stapi.server.character.query;

import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class CharacterRestQuery {

	private CharacterRestMapper characterRequestMapper;

	private PageMapper pageMapper;

	private CharacterRepository characterRepository;

	@Inject
	public CharacterRestQuery(CharacterRestMapper characterRestMapper, PageMapper pageMapper,
			CharacterRepository characterRepository) {
		this.characterRequestMapper = characterRestMapper;
		this.pageMapper = pageMapper;
		this.characterRepository = characterRepository;
	}

	public Page<Character> query(CharacterRestBeanParams characterRestBeanParams) {
		CharacterRequestDTO characterRequestDTO = characterRequestMapper.map(characterRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(characterRestBeanParams);
		return characterRepository.findMatching(characterRequestDTO, pageRequest);
	}


}
