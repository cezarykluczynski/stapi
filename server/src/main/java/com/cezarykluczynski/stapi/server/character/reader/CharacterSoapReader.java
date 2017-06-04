package com.cezarykluczynski.stapi.server.character.reader;

import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullResponse;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseSoapMapper;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterFullSoapMapper;
import com.cezarykluczynski.stapi.server.character.query.CharacterSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CharacterSoapReader implements BaseReader<CharacterBaseRequest, CharacterBaseResponse>,
		FullReader<CharacterFullRequest, CharacterFullResponse> {

	private final CharacterSoapQuery characterSoapQuery;

	private final CharacterBaseSoapMapper characterBaseSoapMapper;

	private final CharacterFullSoapMapper characterFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public CharacterSoapReader(CharacterSoapQuery characterSoapQuery, CharacterBaseSoapMapper characterBaseSoapMapper,
			CharacterFullSoapMapper characterFullSoapMapper,PageMapper pageMapper, SortMapper sortMapper) {
		this.characterSoapQuery = characterSoapQuery;
		this.characterBaseSoapMapper = characterBaseSoapMapper;
		this.characterFullSoapMapper = characterFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public CharacterBaseResponse readBase(CharacterBaseRequest input) {
		Page<Character> characterPage = characterSoapQuery.query(input);
		CharacterBaseResponse characterResponse = new CharacterBaseResponse();
		characterResponse.setPage(pageMapper.fromPageToSoapResponsePage(characterPage));
		characterResponse.setSort(sortMapper.map(input.getSort()));
		characterResponse.getCharacters().addAll(characterBaseSoapMapper.mapBase(characterPage.getContent()));
		return characterResponse;
	}

	@Override
	public CharacterFullResponse readFull(CharacterFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Character> characterPage = characterSoapQuery.query(input);
		CharacterFullResponse characterFullResponse = new CharacterFullResponse();
		characterFullResponse.setCharacter(characterFullSoapMapper.mapFull(Iterables.getOnlyElement(characterPage.getContent(), null)));
		return characterFullResponse;
	}

}
