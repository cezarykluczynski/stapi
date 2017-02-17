package com.cezarykluczynski.stapi.server.character.reader;

import com.cezarykluczynski.stapi.client.v1.soap.CharacterRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterResponse;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterSoapMapper;
import com.cezarykluczynski.stapi.server.character.query.CharacterSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.Reader;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CharacterSoapReader implements Reader<CharacterRequest, CharacterResponse> {

	private CharacterSoapQuery characterSoapQuery;

	private CharacterSoapMapper characterSoapMapper;

	private PageMapper pageMapper;

	public CharacterSoapReader(CharacterSoapQuery characterSoapQuery, CharacterSoapMapper characterSoapMapper, PageMapper pageMapper) {
		this.characterSoapQuery = characterSoapQuery;
		this.characterSoapMapper = characterSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public CharacterResponse read(CharacterRequest input) {
		Page<Character> characterPage = characterSoapQuery.query(input);
		CharacterResponse characterResponse = new CharacterResponse();
		characterResponse.setPage(pageMapper.fromPageToSoapResponsePage(characterPage));
		characterResponse.getCharacters().addAll(characterSoapMapper.map(characterPage.getContent()));
		return characterResponse;
	}

}
