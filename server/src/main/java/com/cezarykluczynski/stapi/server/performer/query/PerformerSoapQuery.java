package com.cezarykluczynski.stapi.server.performer.query;

import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullRequest;
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

	public Page<Performer> query(PerformerBaseRequest performerBaseRequest) {
		PerformerRequestDTO performerRequestDTO = performerSoapMapper.mapBase(performerBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(performerBaseRequest.getPage());
		return performerRepository.findMatching(performerRequestDTO, pageRequest);
	}

	public Page<Performer> query(PerformerFullRequest performerFullRequest) {
		PerformerRequestDTO performerRequestDTO = performerSoapMapper.mapFull(performerFullRequest);
		return performerRepository.findMatching(performerRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
