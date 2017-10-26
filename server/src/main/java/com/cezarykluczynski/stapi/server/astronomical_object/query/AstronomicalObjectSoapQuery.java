package com.cezarykluczynski.stapi.server.astronomical_object.query;

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullRequest;
import com.cezarykluczynski.stapi.model.astronomical_object.dto.AstronomicalObjectRequestDTO;
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.astronomical_object.repository.AstronomicalObjectRepository;
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectBaseSoapMapper;
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AstronomicalObjectSoapQuery {

	private final AstronomicalObjectBaseSoapMapper astronomicalObjectBaseSoapMapper;

	private final AstronomicalObjectFullSoapMapper astronomicalObjectFullSoapMapper;

	private final PageMapper pageMapper;

	private final AstronomicalObjectRepository astronomicalObjectRepository;

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
