package com.cezarykluczynski.stapi.server.soundtrack.reader;

import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFullResponse;
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackBaseSoapMapper;
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackFullSoapMapper;
import com.cezarykluczynski.stapi.server.soundtrack.query.SoundtrackSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SoundtrackSoapReader implements BaseReader<SoundtrackBaseRequest, SoundtrackBaseResponse>,
		FullReader<SoundtrackFullRequest, SoundtrackFullResponse> {

	private final SoundtrackSoapQuery soundtrackSoapQuery;

	private final SoundtrackBaseSoapMapper soundtrackBaseSoapMapper;

	private final SoundtrackFullSoapMapper soundtrackFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public SoundtrackSoapReader(SoundtrackSoapQuery soundtrackSoapQuery, SoundtrackBaseSoapMapper soundtrackBaseSoapMapper,
			SoundtrackFullSoapMapper soundtrackFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.soundtrackSoapQuery = soundtrackSoapQuery;
		this.soundtrackBaseSoapMapper = soundtrackBaseSoapMapper;
		this.soundtrackFullSoapMapper = soundtrackFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public SoundtrackBaseResponse readBase(SoundtrackBaseRequest input) {
		Page<Soundtrack> soundtrackPage = soundtrackSoapQuery.query(input);
		SoundtrackBaseResponse soundtrackResponse = new SoundtrackBaseResponse();
		soundtrackResponse.setPage(pageMapper.fromPageToSoapResponsePage(soundtrackPage));
		soundtrackResponse.setSort(sortMapper.map(input.getSort()));
		soundtrackResponse.getSoundtracks().addAll(soundtrackBaseSoapMapper.mapBase(soundtrackPage.getContent()));
		return soundtrackResponse;
	}

	@Override
	public SoundtrackFullResponse readFull(SoundtrackFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Soundtrack> soundtrackPage = soundtrackSoapQuery.query(input);
		SoundtrackFullResponse soundtrackFullResponse = new SoundtrackFullResponse();
		soundtrackFullResponse.setSoundtrack(soundtrackFullSoapMapper.mapFull(Iterables.getOnlyElement(soundtrackPage.getContent(), null)));
		return soundtrackFullResponse;
	}

}
