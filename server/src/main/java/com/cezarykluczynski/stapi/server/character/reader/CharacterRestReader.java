package com.cezarykluczynski.stapi.server.character.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterFullResponse;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterFullRestMapper;
import com.cezarykluczynski.stapi.server.character.query.CharacterRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CharacterRestReader implements BaseReader<CharacterRestBeanParams, CharacterBaseResponse>, FullReader<String, CharacterFullResponse> {

	private final CharacterRestQuery characterRestQuery;

	private final CharacterBaseRestMapper characterBaseRestMapper;

	private final CharacterFullRestMapper characterFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public CharacterRestReader(CharacterRestQuery characterRestQuery, CharacterBaseRestMapper characterBaseRestMapper,
			CharacterFullRestMapper characterFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.characterRestQuery = characterRestQuery;
		this.characterBaseRestMapper = characterBaseRestMapper;
		this.characterFullRestMapper = characterFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public CharacterBaseResponse readBase(CharacterRestBeanParams input) {
		Page<Character> characterPage = characterRestQuery.query(input);
		CharacterBaseResponse characterResponse = new CharacterBaseResponse();
		characterResponse.setPage(pageMapper.fromPageToRestResponsePage(characterPage));
		characterResponse.setSort(sortMapper.map(input.getSort()));
		characterResponse.getCharacters().addAll(characterBaseRestMapper.mapBase(characterPage.getContent()));
		return characterResponse;
	}

	@Override
	public CharacterFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		CharacterRestBeanParams characterRestBeanParams = new CharacterRestBeanParams();
		characterRestBeanParams.setUid(uid);
		Page<Character> characterPage = characterRestQuery.query(characterRestBeanParams);
		CharacterFullResponse characterResponse = new CharacterFullResponse();
		characterResponse.setCharacter(characterFullRestMapper.mapFull(Iterables.getOnlyElement(characterPage.getContent(), null)));
		return characterResponse;
	}

}
