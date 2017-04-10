package com.cezarykluczynski.stapi.server.species.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesFullResponse;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.species.dto.SpeciesRestBeanParams;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseRestMapper;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesFullRestMapper;
import com.cezarykluczynski.stapi.server.species.query.SpeciesRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SpeciesRestReader implements BaseReader<SpeciesRestBeanParams, SpeciesBaseResponse>, FullReader<String, SpeciesFullResponse> {

	private SpeciesRestQuery speciesRestQuery;

	private SpeciesBaseRestMapper speciesBaseRestMapper;

	private SpeciesFullRestMapper speciesFullRestMapper;

	private PageMapper pageMapper;

	@Inject
	public SpeciesRestReader(SpeciesRestQuery speciesRestQuery, SpeciesBaseRestMapper speciesBaseRestMapper,
			SpeciesFullRestMapper speciesFullRestMapper, PageMapper pageMapper) {
		this.speciesRestQuery = speciesRestQuery;
		this.speciesBaseRestMapper = speciesBaseRestMapper;
		this.speciesFullRestMapper = speciesFullRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public SpeciesBaseResponse readBase(SpeciesRestBeanParams speciesRestBeanParams) {
		Page<Species> speciesPage = speciesRestQuery.query(speciesRestBeanParams);
		SpeciesBaseResponse speciesResponse = new SpeciesBaseResponse();
		speciesResponse.setPage(pageMapper.fromPageToRestResponsePage(speciesPage));
		speciesResponse.getSpecies().addAll(speciesBaseRestMapper.mapBase(speciesPage.getContent()));
		return speciesResponse;
	}

	@Override
	public SpeciesFullResponse readFull(String guid) {
		StaticValidator.requireGuid(guid);
		SpeciesRestBeanParams speciesRestBeanParams = new SpeciesRestBeanParams();
		speciesRestBeanParams.setGuid(guid);
		Page<Species> speciesPage = speciesRestQuery.query(speciesRestBeanParams);
		SpeciesFullResponse speciesResponse = new SpeciesFullResponse();
		speciesResponse.setSpecies(speciesFullRestMapper.mapFull(Iterables.getOnlyElement(speciesPage.getContent(), null)));
		return speciesResponse;
	}

}
