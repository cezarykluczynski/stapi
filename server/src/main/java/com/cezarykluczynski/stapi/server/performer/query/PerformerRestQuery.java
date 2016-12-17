package com.cezarykluczynski.stapi.server.performer.query;

import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class PerformerRestQuery {

	private PerformerRestMapper performerRestMapper;

	private PageMapper pageMapper;

	private PerformerRepository performerRepository;

	@Inject
	public PerformerRestQuery(PerformerRestMapper performerRestMapper, PageMapper pageMapper,
			PerformerRepository performerRepository) {
		this.performerRestMapper = performerRestMapper;
		this.pageMapper = pageMapper;
		this.performerRepository = performerRepository;
	}

	public Page<Performer> query(PerformerRestBeanParams performerRestBeanParams) {
		PerformerRequestDTO performerRequestDTO = performerRestMapper.map(performerRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(performerRestBeanParams);
		return performerRepository.findMatching(performerRequestDTO, pageRequest);
	}


}
