package com.cezarykluczynski.stapi.server.species.query;

import com.cezarykluczynski.stapi.client.v1.soap.SpeciesRequest;
import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.model.species.repository.SpeciesRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SpeciesSoapQuery {

	private SpeciesSoapMapper speciesSoapMapper;

	private PageMapper pageMapper;

	private SpeciesRepository speciesRepository;

	@Inject
	public SpeciesSoapQuery(SpeciesSoapMapper speciesSoapMapper, PageMapper pageMapper, SpeciesRepository speciesRepository) {
		this.speciesSoapMapper = speciesSoapMapper;
		this.pageMapper = pageMapper;
		this.speciesRepository = speciesRepository;
	}

	public Page<Species> query(SpeciesRequest speciesRequest) {
		SpeciesRequestDTO speciesRequestDTO = speciesSoapMapper.map(speciesRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(speciesRequest.getPage());
		return speciesRepository.findMatching(speciesRequestDTO, pageRequest);
	}

}
