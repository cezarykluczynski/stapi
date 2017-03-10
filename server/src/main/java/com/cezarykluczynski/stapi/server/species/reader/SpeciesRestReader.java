package com.cezarykluczynski.stapi.server.species.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesResponse;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.Reader;
import com.cezarykluczynski.stapi.server.species.dto.SpeciesRestBeanParams;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesRestMapper;
import com.cezarykluczynski.stapi.server.species.query.SpeciesRestQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SpeciesRestReader implements Reader<SpeciesRestBeanParams, SpeciesResponse> {

	private SpeciesRestQuery speciesRestQuery;

	private SpeciesRestMapper speciesRestMapper;

	private PageMapper pageMapper;

	@Inject
	public SpeciesRestReader(SpeciesRestQuery speciesRestQuery, SpeciesRestMapper speciesRestMapper, PageMapper pageMapper) {
		this.speciesRestQuery = speciesRestQuery;
		this.speciesRestMapper = speciesRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public SpeciesResponse read(SpeciesRestBeanParams input) {
		Page<Species> speciesPage = speciesRestQuery.query(input);
		SpeciesResponse speciesResponse = new SpeciesResponse();
		speciesResponse.setPage(pageMapper.fromPageToRestResponsePage(speciesPage));
		speciesResponse.getSpecies().addAll(speciesRestMapper.map(speciesPage.getContent()));
		return speciesResponse;
	}

}
