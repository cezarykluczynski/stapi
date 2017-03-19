package com.cezarykluczynski.stapi.server.character.reader;

import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullResponse;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterSoapMapper;
import com.cezarykluczynski.stapi.server.character.query.CharacterSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CharacterSoapReader implements BaseReader<CharacterBaseRequest, CharacterBaseResponse>,
		FullReader<CharacterFullRequest, CharacterFullResponse> {

	private CharacterSoapQuery characterSoapQuery;

	private CharacterSoapMapper characterSoapMapper;

	private PageMapper pageMapper;

	public CharacterSoapReader(CharacterSoapQuery characterSoapQuery, CharacterSoapMapper characterSoapMapper, PageMapper pageMapper) {
		this.characterSoapQuery = characterSoapQuery;
		this.characterSoapMapper = characterSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public CharacterBaseResponse readBase(CharacterBaseRequest input) {
		Page<Character> characterPage = characterSoapQuery.query(input);
		CharacterBaseResponse characterResponse = new CharacterBaseResponse();
		characterResponse.setPage(pageMapper.fromPageToSoapResponsePage(characterPage));
		characterResponse.getCharacters().addAll(characterSoapMapper.mapBase(characterPage.getContent()));
		return characterResponse;
	}

	@Override
	public CharacterFullResponse readFull(CharacterFullRequest input) {
		Page<Character> characterPage = characterSoapQuery.query(input);
		CharacterFullResponse characterFullResponse = new CharacterFullResponse();
		characterFullResponse.setCharacter(characterSoapMapper.mapFull(Iterables.getOnlyElement(characterPage.getContent(), null)));
		return characterFullResponse;
	}

}
