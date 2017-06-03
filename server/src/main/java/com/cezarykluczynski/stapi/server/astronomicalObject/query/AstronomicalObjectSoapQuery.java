package com.cezarykluczynski.stapi.server.astronomicalObject.query;

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullRequest;
import com.cezarykluczynski.stapi.model.astronomicalObject.dto.AstronomicalObjectRequestDTO;
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.astronomicalObject.repository.AstronomicalObjectRepository;
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectBaseSoapMapper;
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class AstronomicalObjectSoapQuery {

	private final AstronomicalObjectBaseSoapMapper astronomicalObjectBaseSoapMapper;

	private final AstronomicalObjectFullSoapMapper astronomicalObjectFullSoapMapper;

	private final PageMapper pageMapper;

	private final AstronomicalObjectRepository astronomicalObjectRepository;

	@Inject
	public AstronomicalObjectSoapQuery(AstronomicalObjectBaseSoapMapper astronomicalObjectBaseSoapMapper,
			AstronomicalObjectFullSoapMapper astronomicalObjectFullSoapMapper, PageMapper pageMapper,
			AstronomicalObjectRepository astronomicalObjectRepository) {
		this.astronomicalObjectBaseSoapMapper = astronomicalObjectBaseSoapMapper;
		this.astronomicalObjectFullSoapMapper = astronomicalObjectFullSoapMapper;
		this.pageMapper = pageMapper;
		this.astronomicalObjectRepository = astronomicalObjectRepository;
	}

	public Page<AstronomicalObject> query(AstronomicalObjectBaseRequest astronomicalObjectBaseRequest) {
		AstronomicalObjectRequestDTO astronomicalObjectRequestDTO = astronomicalObjectBaseSoapMapper.mapBase(astronomicalObjectBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(astronomicalObjectBaseRequest.getPage());
		return astronomicalObjectRepository.findMatching(astronomicalObjectRequestDTO, pageRequest);
	}

	public Page<AstronomicalObject> query(AstronomicalObjectFullRequest astronomicalObjectFullRequest) {
		AstronomicalObjectRequestDTO astronomicalObjectRequestDTO = astronomicalObjectFullSoapMapper.mapFull(astronomicalObjectFullRequest);
		return astronomicalObjectRepository.findMatching(astronomicalObjectRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
