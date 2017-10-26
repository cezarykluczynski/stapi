package com.cezarykluczynski.stapi.server.technology.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyFullResponse;
import com.cezarykluczynski.stapi.model.technology.entity.Technology;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.technology.dto.TechnologyRestBeanParams;
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyBaseRestMapper;
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyFullRestMapper;
import com.cezarykluczynski.stapi.server.technology.query.TechnologyRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TechnologyRestReader implements BaseReader<TechnologyRestBeanParams, TechnologyBaseResponse>,
		FullReader<String, TechnologyFullResponse> {

	private TechnologyRestQuery technologyRestQuery;

	private TechnologyBaseRestMapper technologyBaseRestMapper;

	private TechnologyFullRestMapper technologyFullRestMapper;

	private PageMapper pageMapper;

	private final SortMapper sortMapper;

	public TechnologyRestReader(TechnologyRestQuery technologyRestQuery, TechnologyBaseRestMapper technologyBaseRestMapper,
			TechnologyFullRestMapper technologyFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.technologyRestQuery = technologyRestQuery;
		this.technologyBaseRestMapper = technologyBaseRestMapper;
		this.technologyFullRestMapper = technologyFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public TechnologyBaseResponse readBase(TechnologyRestBeanParams input) {
		Page<Technology> technologyPage = technologyRestQuery.query(input);
		TechnologyBaseResponse technologyResponse = new TechnologyBaseResponse();
		technologyResponse.setPage(pageMapper.fromPageToRestResponsePage(technologyPage));
		technologyResponse.setSort(sortMapper.map(input.getSort()));
		technologyResponse.getTechnology().addAll(technologyBaseRestMapper.mapBase(technologyPage.getContent()));
		return technologyResponse;
	}

	@Override
	public TechnologyFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		TechnologyRestBeanParams technologyRestBeanParams = new TechnologyRestBeanParams();
		technologyRestBeanParams.setUid(uid);
		Page<Technology> technologyPage = technologyRestQuery.query(technologyRestBeanParams);
		TechnologyFullResponse technologyResponse = new TechnologyFullResponse();
		technologyResponse.setTechnology(technologyFullRestMapper.mapFull(Iterables.getOnlyElement(technologyPage.getContent(), null)));
		return technologyResponse;
	}
}
