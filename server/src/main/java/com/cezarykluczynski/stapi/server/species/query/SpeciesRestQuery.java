package com.cezarykluczynski.stapi.server.species.query;

import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.model.species.repository.SpeciesRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.species.dto.SpeciesRestBeanParams;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SpeciesRestQuery {

	private final SpeciesBaseRestMapper speciesRequestMapper;

	private final PageMapper pageMapper;

	private final SpeciesRepository speciesRepository;

	public SpeciesRestQuery(SpeciesBaseRestMapper speciesBaseRestMapper, PageMapper pageMapper, SpeciesRepository speciesRepository) {
		this.speciesRequestMapper = speciesBaseRestMapper;
		this.pageMapper = pageMapper;
		this.speciesRepository = speciesRepository;
	}

	public Page<Species> query(SpeciesRestBeanParams speciesRestBeanParams) {
		SpeciesRequestDTO speciesRequestDTO = speciesRequestMapper.mapBase(speciesRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(speciesRestBeanParams);
		return speciesRepository.findMatching(speciesRequestDTO, pageRequest);
	}


}
