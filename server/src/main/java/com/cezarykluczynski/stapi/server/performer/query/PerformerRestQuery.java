package com.cezarykluczynski.stapi.server.performer.query;

import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PerformerRestQuery {

	private final PerformerBaseRestMapper performerBaseRestMapper;

	private final PageMapper pageMapper;

	private final PerformerRepository performerRepository;

	public PerformerRestQuery(PerformerBaseRestMapper performerBaseRestMapper, PageMapper pageMapper, PerformerRepository performerRepository) {
		this.performerBaseRestMapper = performerBaseRestMapper;
		this.pageMapper = pageMapper;
		this.performerRepository = performerRepository;
	}

	public Page<Performer> query(PerformerRestBeanParams performerRestBeanParams) {
		PerformerRequestDTO performerRequestDTO = performerBaseRestMapper.mapBase(performerRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(performerRestBeanParams);
		return performerRepository.findMatching(performerRequestDTO, pageRequest);
	}

}
