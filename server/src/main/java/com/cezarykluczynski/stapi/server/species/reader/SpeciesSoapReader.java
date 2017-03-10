package com.cezarykluczynski.stapi.server.species.reader;

import com.cezarykluczynski.stapi.client.v1.soap.SpeciesRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesResponse;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.Reader;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesSoapMapper;
import com.cezarykluczynski.stapi.server.species.query.SpeciesSoapQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SpeciesSoapReader implements Reader<SpeciesRequest, SpeciesResponse> {

	private SpeciesSoapQuery speciesSoapQuery;

	private SpeciesSoapMapper speciesSoapMapper;

	private PageMapper pageMapper;

	public SpeciesSoapReader(SpeciesSoapQuery speciesSoapQuery, SpeciesSoapMapper speciesSoapMapper, PageMapper pageMapper) {
		this.speciesSoapQuery = speciesSoapQuery;
		this.speciesSoapMapper = speciesSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public SpeciesResponse read(SpeciesRequest input) {
		Page<Species> speciesPage = speciesSoapQuery.query(input);
		SpeciesResponse speciesResponse = new SpeciesResponse();
		speciesResponse.setPage(pageMapper.fromPageToSoapResponsePage(speciesPage));
		speciesResponse.getSpecies().addAll(speciesSoapMapper.map(speciesPage.getContent()));
		return speciesResponse;
	}

}
