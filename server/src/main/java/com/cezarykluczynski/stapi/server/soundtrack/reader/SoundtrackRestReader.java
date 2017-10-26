package com.cezarykluczynski.stapi.server.soundtrack.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackFullResponse;
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.soundtrack.dto.SoundtrackRestBeanParams;
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackBaseRestMapper;
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackFullRestMapper;
import com.cezarykluczynski.stapi.server.soundtrack.query.SoundtrackRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SoundtrackRestReader implements BaseReader<SoundtrackRestBeanParams, SoundtrackBaseResponse>,
		FullReader<String, SoundtrackFullResponse> {

	private final SoundtrackRestQuery soundtrackRestQuery;

	private final SoundtrackBaseRestMapper soundtrackBaseRestMapper;

	private final SoundtrackFullRestMapper soundtrackFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public SoundtrackRestReader(SoundtrackRestQuery soundtrackRestQuery, SoundtrackBaseRestMapper soundtrackBaseRestMapper,
			SoundtrackFullRestMapper soundtrackFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.soundtrackRestQuery = soundtrackRestQuery;
		this.soundtrackBaseRestMapper = soundtrackBaseRestMapper;
		this.soundtrackFullRestMapper = soundtrackFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public SoundtrackBaseResponse readBase(SoundtrackRestBeanParams input) {
		Page<Soundtrack> soundtrackPage = soundtrackRestQuery.query(input);
		SoundtrackBaseResponse soundtrackResponse = new SoundtrackBaseResponse();
		soundtrackResponse.setPage(pageMapper.fromPageToRestResponsePage(soundtrackPage));
		soundtrackResponse.setSort(sortMapper.map(input.getSort()));
		soundtrackResponse.getSoundtracks().addAll(soundtrackBaseRestMapper.mapBase(soundtrackPage.getContent()));
		return soundtrackResponse;
	}

	@Override
	public SoundtrackFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		SoundtrackRestBeanParams soundtrackRestBeanParams = new SoundtrackRestBeanParams();
		soundtrackRestBeanParams.setUid(uid);
		Page<Soundtrack> soundtrackPage = soundtrackRestQuery.query(soundtrackRestBeanParams);
		SoundtrackFullResponse soundtrackResponse = new SoundtrackFullResponse();
		soundtrackResponse.setSoundtrack(soundtrackFullRestMapper.mapFull(Iterables.getOnlyElement(soundtrackPage.getContent(), null)));
		return soundtrackResponse;
	}

}
