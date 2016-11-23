package com.cezarykluczynski.stapi.server.character.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterResponse;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterRestMapper;
import com.cezarykluczynski.stapi.server.character.query.CharacterRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.Reader;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class CharacterRestReader implements Reader<CharacterRestBeanParams, CharacterResponse> {

	private CharacterRestQuery characterRestQuery;

	private CharacterRestMapper characterRestMapper;

	private PageMapper pageMapper;

	@Inject
	public CharacterRestReader(CharacterRestQuery characterRestQuery, CharacterRestMapper characterRestMapper,
				PageMapper pageMapper) {
		this.characterRestQuery = characterRestQuery;
		this.characterRestMapper = characterRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public CharacterResponse read(CharacterRestBeanParams input) {
		Page<Character> characterPage = characterRestQuery.query(input);
		CharacterResponse characterResponse = new CharacterResponse();
		characterResponse.setPage(pageMapper.fromPageToRestResponsePage(characterPage));
		characterResponse.getCharacters().addAll(characterRestMapper.map(characterPage.getContent()));
		return characterResponse;
	}

}
