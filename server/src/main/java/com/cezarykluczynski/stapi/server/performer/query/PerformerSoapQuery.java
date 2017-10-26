package com.cezarykluczynski.stapi.server.performer.query;

import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullRequest;
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseSoapMapper;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PerformerSoapQuery {

	private final PerformerBaseSoapMapper performerBaseSoapMapper;

	private final PerformerFullSoapMapper performerFullSoapMapper;

	private final PageMapper pageMapper;

	private final PerformerRepository performerRepository;

	public PerformerSoapQuery(PerformerBaseSoapMapper performerBaseSoapMapper, PerformerFullSoapMapper performerFullSoapMapper, PageMapper pageMapper,
			PerformerRepository performerRepository) {
		this.performerBaseSoapMapper = performerBaseSoapMapper;
		this.performerFullSoapMapper = performerFullSoapMapper;
		this.pageMapper = pageMapper;
		this.performerRepository = performerRepository;
	}

	public Page<Performer> query(PerformerBaseRequest performerBaseRequest) {
		PerformerRequestDTO performerRequestDTO = performerBaseSoapMapper.mapBase(performerBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(performerBaseRequest.getPage());
		return performerRepository.findMatching(performerRequestDTO, pageRequest);
	}

	public Page<Performer> query(PerformerFullRequest performerFullRequest) {
		PerformerRequestDTO performerRequestDTO = performerFullSoapMapper.mapFull(performerFullRequest);
		return performerRepository.findMatching(performerRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
