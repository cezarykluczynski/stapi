package com.cezarykluczynski.stapi.server.occupation.query;

import com.cezarykluczynski.stapi.client.v1.soap.OccupationBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.OccupationFullRequest;
import com.cezarykluczynski.stapi.model.occupation.dto.OccupationRequestDTO;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import com.cezarykluczynski.stapi.model.occupation.repository.OccupationRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationBaseSoapMapper;
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class OccupationSoapQuery {

	private final OccupationBaseSoapMapper occupationBaseSoapMapper;

	private final OccupationFullSoapMapper occupationFullSoapMapper;

	private final PageMapper pageMapper;

	private final OccupationRepository occupationRepository;

	public OccupationSoapQuery(OccupationBaseSoapMapper occupationBaseSoapMapper, OccupationFullSoapMapper occupationFullSoapMapper,
			PageMapper pageMapper, OccupationRepository occupationRepository) {
		this.occupationBaseSoapMapper = occupationBaseSoapMapper;
		this.occupationFullSoapMapper = occupationFullSoapMapper;
		this.pageMapper = pageMapper;
		this.occupationRepository = occupationRepository;
	}

	public Page<Occupation> query(OccupationBaseRequest occupationBaseRequest) {
		OccupationRequestDTO occupationRequestDTO = occupationBaseSoapMapper.mapBase(occupationBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(occupationBaseRequest.getPage());
		return occupationRepository.findMatching(occupationRequestDTO, pageRequest);
	}

	public Page<Occupation> query(OccupationFullRequest occupationFullRequest) {
		OccupationRequestDTO seriesRequestDTO = occupationFullSoapMapper.mapFull(occupationFullRequest);
		return occupationRepository.findMatching(seriesRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
