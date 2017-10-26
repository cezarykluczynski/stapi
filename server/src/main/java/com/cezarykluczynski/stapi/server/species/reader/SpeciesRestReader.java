package com.cezarykluczynski.stapi.server.species.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesFullResponse;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
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

@Service
public class SpeciesRestReader implements BaseReader<SpeciesRestBeanParams, SpeciesBaseResponse>, FullReader<String, SpeciesFullResponse> {

	private final SpeciesRestQuery speciesRestQuery;

	private final SpeciesBaseRestMapper speciesBaseRestMapper;

	private final SpeciesFullRestMapper speciesFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public SpeciesRestReader(SpeciesRestQuery speciesRestQuery, SpeciesBaseRestMapper speciesBaseRestMapper,
			SpeciesFullRestMapper speciesFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.speciesRestQuery = speciesRestQuery;
		this.speciesBaseRestMapper = speciesBaseRestMapper;
		this.speciesFullRestMapper = speciesFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public SpeciesBaseResponse readBase(SpeciesRestBeanParams input) {
		Page<Species> speciesPage = speciesRestQuery.query(input);
		SpeciesBaseResponse speciesResponse = new SpeciesBaseResponse();
		speciesResponse.setPage(pageMapper.fromPageToRestResponsePage(speciesPage));
		speciesResponse.setSort(sortMapper.map(input.getSort()));
		speciesResponse.getSpecies().addAll(speciesBaseRestMapper.mapBase(speciesPage.getContent()));
		return speciesResponse;
	}

	@Override
	public SpeciesFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		SpeciesRestBeanParams speciesRestBeanParams = new SpeciesRestBeanParams();
		speciesRestBeanParams.setUid(uid);
		Page<Species> speciesPage = speciesRestQuery.query(speciesRestBeanParams);
		SpeciesFullResponse speciesResponse = new SpeciesFullResponse();
		speciesResponse.setSpecies(speciesFullRestMapper.mapFull(Iterables.getOnlyElement(speciesPage.getContent(), null)));
		return speciesResponse;
	}

}
