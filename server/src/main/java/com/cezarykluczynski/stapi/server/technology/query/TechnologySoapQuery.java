package com.cezarykluczynski.stapi.server.technology.query;

import com.cezarykluczynski.stapi.client.v1.soap.TechnologyBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFullRequest;
import com.cezarykluczynski.stapi.model.technology.dto.TechnologyRequestDTO;
import com.cezarykluczynski.stapi.model.technology.entity.Technology;
import com.cezarykluczynski.stapi.model.technology.repository.TechnologyRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyBaseSoapMapper;
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TechnologySoapQuery {

	private final TechnologyBaseSoapMapper technologyBaseSoapMapper;

	private final TechnologyFullSoapMapper technologyFullSoapMapper;

	private final PageMapper pageMapper;

	private final TechnologyRepository technologyRepository;

	public TechnologySoapQuery(TechnologyBaseSoapMapper technologyBaseSoapMapper, TechnologyFullSoapMapper technologyFullSoapMapper,
			PageMapper pageMapper, TechnologyRepository technologyRepository) {
		this.technologyBaseSoapMapper = technologyBaseSoapMapper;
		this.technologyFullSoapMapper = technologyFullSoapMapper;
		this.pageMapper = pageMapper;
		this.technologyRepository = technologyRepository;
	}

	public Page<Technology> query(TechnologyBaseRequest technologyBaseRequest) {
		TechnologyRequestDTO technologyRequestDTO = technologyBaseSoapMapper.mapBase(technologyBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(technologyBaseRequest.getPage());
		return technologyRepository.findMatching(technologyRequestDTO, pageRequest);
	}

	public Page<Technology> query(TechnologyFullRequest technologyFullRequest) {
		TechnologyRequestDTO seriesRequestDTO = technologyFullSoapMapper.mapFull(technologyFullRequest);
		return technologyRepository.findMatching(seriesRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
