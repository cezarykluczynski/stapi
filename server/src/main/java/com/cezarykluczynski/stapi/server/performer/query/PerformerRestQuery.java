package com.cezarykluczynski.stapi.server.performer.query;

import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerRequestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class PerformerRestQuery {

	private PerformerRequestMapper performerRequestMapper;

	private PageMapper pageMapper;

	private PerformerRepository performerRepository;

	@Inject
	public PerformerRestQuery(PerformerRequestMapper performerRequestMapper, PageMapper pageMapper,
			PerformerRepository performerRepository) {
		this.performerRequestMapper = performerRequestMapper;
		this.pageMapper = pageMapper;
		this.performerRepository = performerRepository;
	}

	public Page<Performer> query(PerformerRestBeanParams performerRestBeanParams) {
		PerformerRequestDTO performerRequestDTO = performerRequestMapper.map(performerRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageAwareBeanParamsToPageRequest(performerRestBeanParams);
		return performerRepository.findMatching(performerRequestDTO, pageRequest);
	}


}
