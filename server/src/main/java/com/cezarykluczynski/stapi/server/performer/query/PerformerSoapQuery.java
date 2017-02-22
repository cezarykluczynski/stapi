package com.cezarykluczynski.stapi.server.performer.query;

import com.cezarykluczynski.stapi.client.v1.soap.PerformerRequest;
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class PerformerSoapQuery {

	private PerformerSoapMapper performerSoapMapper;

	private PageMapper pageMapper;

	private PerformerRepository performerRepository;

	@Inject
	public PerformerSoapQuery(PerformerSoapMapper performerSoapMapper, PageMapper pageMapper, PerformerRepository performerRepository) {
		this.performerSoapMapper = performerSoapMapper;
		this.pageMapper = pageMapper;
		this.performerRepository = performerRepository;
	}

	public Page<Performer> query(PerformerRequest performerRequest) {
		PerformerRequestDTO performerRequestDTO = performerSoapMapper.map(performerRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(performerRequest.getPage());
		return performerRepository.findMatching(performerRequestDTO, pageRequest);
	}

}
