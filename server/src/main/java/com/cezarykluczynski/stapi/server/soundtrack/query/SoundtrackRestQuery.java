package com.cezarykluczynski.stapi.server.soundtrack.query;

import com.cezarykluczynski.stapi.model.soundtrack.dto.SoundtrackRequestDTO;
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack;
import com.cezarykluczynski.stapi.model.soundtrack.repository.SoundtrackRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.soundtrack.dto.SoundtrackRestBeanParams;
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SoundtrackRestQuery {

	private final SoundtrackBaseRestMapper soundtrackBaseRestMapper;

	private final PageMapper pageMapper;

	private final SoundtrackRepository soundtrackRepository;

	public SoundtrackRestQuery(SoundtrackBaseRestMapper soundtrackBaseRestMapper, PageMapper pageMapper, SoundtrackRepository soundtrackRepository) {
		this.soundtrackBaseRestMapper = soundtrackBaseRestMapper;
		this.pageMapper = pageMapper;
		this.soundtrackRepository = soundtrackRepository;
	}

	public Page<Soundtrack> query(SoundtrackRestBeanParams soundtrackRestBeanParams) {
		SoundtrackRequestDTO soundtrackRequestDTO = soundtrackBaseRestMapper.mapBase(soundtrackRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(soundtrackRestBeanParams);
		return soundtrackRepository.findMatching(soundtrackRequestDTO, pageRequest);
	}

}
