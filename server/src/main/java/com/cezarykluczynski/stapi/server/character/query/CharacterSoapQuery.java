package com.cezarykluczynski.stapi.server.character.query;

import com.cezarykluczynski.stapi.client.v1.soap.CharacterRequest;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterRequestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class CharacterSoapQuery {

	private CharacterRequestMapper characterRequestMapper;

	private PageMapper pageMapper;

	private CharacterRepository characterRepository;

	@Inject
	public CharacterSoapQuery(CharacterRequestMapper characterRequestMapper, PageMapper pageMapper,
			CharacterRepository characterRepository) {
		this.characterRequestMapper = characterRequestMapper;
		this.pageMapper = pageMapper;
		this.characterRepository = characterRepository;
	}

	public Page<Character> query(CharacterRequest characterRequest) {
		CharacterRequestDTO characterRequestDTO = characterRequestMapper.map(characterRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(characterRequest.getPage());
		return characterRepository.findMatching(characterRequestDTO, pageRequest);
	}

}
