package com.cezarykluczynski.stapi.server.character.query;

import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullRequest;
import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseSoapMapper;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CharacterSoapQuery {

	private final CharacterBaseSoapMapper characterBaseSoapMapper;

	private final CharacterFullSoapMapper characterFullSoapMapper;

	private final PageMapper pageMapper;

	private final CharacterRepository characterRepository;

	public CharacterSoapQuery(CharacterBaseSoapMapper characterBaseSoapMapper, CharacterFullSoapMapper characterFullSoapMapper, PageMapper pageMapper,
			CharacterRepository characterRepository) {
		this.characterBaseSoapMapper = characterBaseSoapMapper;
		this.characterFullSoapMapper = characterFullSoapMapper;
		this.pageMapper = pageMapper;
		this.characterRepository = characterRepository;
	}

	public Page<Character> query(CharacterBaseRequest characterBaseRequest) {
		CharacterRequestDTO characterRequestDTO = characterBaseSoapMapper.mapBase(characterBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(characterBaseRequest.getPage());
		return characterRepository.findMatching(characterRequestDTO, pageRequest);
	}

	public Page<Character> query(CharacterFullRequest characterFullRequest) {
		CharacterRequestDTO characterRequestDTO = characterFullSoapMapper.mapFull(characterFullRequest);
		return characterRepository.findMatching(characterRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
