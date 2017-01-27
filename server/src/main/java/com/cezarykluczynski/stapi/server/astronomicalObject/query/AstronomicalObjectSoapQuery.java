package com.cezarykluczynski.stapi.server.astronomicalObject.query;

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectRequest;
import com.cezarykluczynski.stapi.model.astronomicalObject.dto.AstronomicalObjectRequestDTO;
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.astronomicalObject.repository.AstronomicalObjectRepository;
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class AstronomicalObjectSoapQuery {

	private AstronomicalObjectSoapMapper astronomicalObjectSoapMapper;

	private PageMapper pageMapper;

	private AstronomicalObjectRepository astronomicalObjectRepository;

	@Inject
	public AstronomicalObjectSoapQuery(AstronomicalObjectSoapMapper astronomicalObjectSoapMapper, PageMapper pageMapper,
			AstronomicalObjectRepository astronomicalObjectRepository) {
		this.astronomicalObjectSoapMapper = astronomicalObjectSoapMapper;
		this.pageMapper = pageMapper;
		this.astronomicalObjectRepository = astronomicalObjectRepository;
	}

	public Page<AstronomicalObject> query(AstronomicalObjectRequest astronomicalObjectRequest) {
		AstronomicalObjectRequestDTO astronomicalObjectRequestDTO = astronomicalObjectSoapMapper.map(astronomicalObjectRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(astronomicalObjectRequest.getPage());
		return astronomicalObjectRepository.findMatching(astronomicalObjectRequestDTO, pageRequest);
	}

}
