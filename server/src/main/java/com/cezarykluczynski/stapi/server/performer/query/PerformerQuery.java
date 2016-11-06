package com.cezarykluczynski.stapi.server.performer.query;

import com.cezarykluczynski.stapi.client.v1.soap.PerformerRequest;
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
public class PerformerQuery {

	private PerformerRequestMapper performerRequestMapper;

	private PageMapper pageMapper;

	private PerformerRepository performerRepository;

	@Inject
	public PerformerQuery(PerformerRequestMapper performerRequestMapper, PageMapper pageMapper,
			PerformerRepository performerRepository) {
		this.performerRequestMapper = performerRequestMapper;
		this.pageMapper = pageMapper;
		this.performerRepository = performerRepository;
	}

	public Page<Performer> query(PerformerRequest performerRequest) {
		PerformerRequestDTO performerRequestDTO = performerRequestMapper.map(performerRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(performerRequest.getPage());
		return performerRepository.findMatching(performerRequestDTO, pageRequest);
	}

	public Page<Performer> query(PerformerRestBeanParams performerRestBeanParams) {
		PerformerRequestDTO performerRequestDTO = performerRequestMapper.map(performerRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageAwareBeanParamsToPageRequest(performerRestBeanParams);
		return performerRepository.findMatching(performerRequestDTO, pageRequest);
	}

}
