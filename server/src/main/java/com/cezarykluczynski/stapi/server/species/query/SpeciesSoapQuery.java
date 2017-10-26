package com.cezarykluczynski.stapi.server.species.query;

import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFullRequest;
import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.model.species.repository.SpeciesRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SpeciesSoapQuery {

	private final SpeciesBaseSoapMapper speciesBaseSoapMapper;

	private final SpeciesFullSoapMapper speciesFullSoapMapper;

	private final PageMapper pageMapper;

	private final SpeciesRepository speciesRepository;

	public SpeciesSoapQuery(SpeciesBaseSoapMapper speciesBaseSoapMapper, SpeciesFullSoapMapper speciesFullSoapMapper, PageMapper pageMapper,
			SpeciesRepository speciesRepository) {
		this.speciesBaseSoapMapper = speciesBaseSoapMapper;
		this.speciesFullSoapMapper = speciesFullSoapMapper;
		this.pageMapper = pageMapper;
		this.speciesRepository = speciesRepository;
	}

	public Page<Species> query(SpeciesBaseRequest speciesBaseRequest) {
		SpeciesRequestDTO speciesRequestDTO = speciesBaseSoapMapper.mapBase(speciesBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(speciesBaseRequest.getPage());
		return speciesRepository.findMatching(speciesRequestDTO, pageRequest);
	}

	public Page<Species> query(SpeciesFullRequest speciesFullRequest) {
		SpeciesRequestDTO speciesRequestDTO = speciesFullSoapMapper.mapFull(speciesFullRequest);
		return speciesRepository.findMatching(speciesRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
