package com.cezarykluczynski.stapi.server.performer.query;

import com.cezarykluczynski.stapi.client.soap.PerformerRequest;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class PerformerQueryBuilder {

	private PerformerRepository performerRepository;

	private PageMapper pageMapper;

	@Inject
	public PerformerQueryBuilder(PerformerRepository performerRepository, PageMapper pageMapper) {
		this.performerRepository = performerRepository;
		this.pageMapper = pageMapper;
	}

	public Page<Performer> query(PerformerRequest performerRequest) {
		// TODO: account for all criteria
		return performerRepository.findAll(pageMapper.fromRequestPageToPageRequest(performerRequest.getPage()));
	}

}
