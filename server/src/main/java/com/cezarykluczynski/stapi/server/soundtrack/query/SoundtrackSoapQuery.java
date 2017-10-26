package com.cezarykluczynski.stapi.server.soundtrack.query;

import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFullRequest;
import com.cezarykluczynski.stapi.model.soundtrack.dto.SoundtrackRequestDTO;
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack;
import com.cezarykluczynski.stapi.model.soundtrack.repository.SoundtrackRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackBaseSoapMapper;
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SoundtrackSoapQuery {

	private final SoundtrackBaseSoapMapper soundtrackBaseSoapMapper;

	private final SoundtrackFullSoapMapper soundtrackFullSoapMapper;

	private final PageMapper pageMapper;

	private final SoundtrackRepository soundtrackRepository;

	public SoundtrackSoapQuery(SoundtrackBaseSoapMapper soundtrackBaseSoapMapper, SoundtrackFullSoapMapper soundtrackFullSoapMapper,
			PageMapper pageMapper, SoundtrackRepository soundtrackRepository) {
		this.soundtrackBaseSoapMapper = soundtrackBaseSoapMapper;
		this.soundtrackFullSoapMapper = soundtrackFullSoapMapper;
		this.pageMapper = pageMapper;
		this.soundtrackRepository = soundtrackRepository;
	}

	public Page<Soundtrack> query(SoundtrackBaseRequest soundtrackBaseRequest) {
		SoundtrackRequestDTO soundtrackRequestDTO = soundtrackBaseSoapMapper.mapBase(soundtrackBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(soundtrackBaseRequest.getPage());
		return soundtrackRepository.findMatching(soundtrackRequestDTO, pageRequest);
	}

	public Page<Soundtrack> query(SoundtrackFullRequest soundtrackFullRequest) {
		SoundtrackRequestDTO soundtrackRequestDTO = soundtrackFullSoapMapper.mapFull(soundtrackFullRequest);
		return soundtrackRepository.findMatching(soundtrackRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
