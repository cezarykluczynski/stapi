package com.cezarykluczynski.stapi.server.species.reader;

import com.cezarykluczynski.stapi.client.rest.model.SpeciesV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.SpeciesV2FullResponse;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.species.dto.SpeciesV2RestBeanParams;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseRestMapper;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesFullRestMapper;
import com.cezarykluczynski.stapi.server.species.query.SpeciesRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SpeciesV2RestReader implements BaseReader<SpeciesV2RestBeanParams, SpeciesV2BaseResponse>, FullReader<SpeciesV2FullResponse> {

	private final SpeciesRestQuery speciesRestQuery;

	private final SpeciesBaseRestMapper speciesBaseRestMapper;

	private final SpeciesFullRestMapper speciesFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public SpeciesV2RestReader(SpeciesRestQuery speciesRestQuery, SpeciesBaseRestMapper speciesBaseRestMapper,
			SpeciesFullRestMapper speciesFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.speciesRestQuery = speciesRestQuery;
		this.speciesBaseRestMapper = speciesBaseRestMapper;
		this.speciesFullRestMapper = speciesFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public SpeciesV2BaseResponse readBase(SpeciesV2RestBeanParams input) {
		Page<Species> speciesPage = speciesRestQuery.query(input);
		SpeciesV2BaseResponse speciesResponse = new SpeciesV2BaseResponse();
		speciesResponse.setPage(pageMapper.fromPageToRestResponsePage(speciesPage));
		speciesResponse.setSort(sortMapper.map(input.getSort()));
		speciesResponse.getSpecies().addAll(speciesBaseRestMapper.mapV2Base(speciesPage.getContent()));
		return speciesResponse;
	}

	@Override
	public SpeciesV2FullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		SpeciesV2RestBeanParams speciesRestBeanParams = new SpeciesV2RestBeanParams();
		speciesRestBeanParams.setUid(uid);
		Page<Species> speciesPage = speciesRestQuery.query(speciesRestBeanParams);
		SpeciesV2FullResponse speciesResponse = new SpeciesV2FullResponse();
		speciesResponse.setSpecies(speciesFullRestMapper.mapV2Full(Iterables.getOnlyElement(speciesPage.getContent(), null)));
		return speciesResponse;
	}

}
