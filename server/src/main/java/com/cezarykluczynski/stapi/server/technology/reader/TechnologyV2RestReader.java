package com.cezarykluczynski.stapi.server.technology.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyV2FullResponse;
import com.cezarykluczynski.stapi.model.technology.entity.Technology;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.technology.dto.TechnologyV2RestBeanParams;
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyBaseRestMapper;
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyFullRestMapper;
import com.cezarykluczynski.stapi.server.technology.query.TechnologyRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TechnologyV2RestReader implements BaseReader<TechnologyV2RestBeanParams, TechnologyV2BaseResponse>,
		FullReader<TechnologyV2FullResponse> {

	private final TechnologyRestQuery technologyRestQuery;

	private final TechnologyBaseRestMapper technologyBaseRestMapper;

	private final TechnologyFullRestMapper technologyFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public TechnologyV2RestReader(TechnologyRestQuery technologyRestQuery, TechnologyBaseRestMapper technologyBaseRestMapper,
			TechnologyFullRestMapper technologyFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.technologyRestQuery = technologyRestQuery;
		this.technologyBaseRestMapper = technologyBaseRestMapper;
		this.technologyFullRestMapper = technologyFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public TechnologyV2BaseResponse readBase(TechnologyV2RestBeanParams input) {
		Page<Technology> technologyV2Page = technologyRestQuery.query(input);
		TechnologyV2BaseResponse technologyV2Response = new TechnologyV2BaseResponse();
		technologyV2Response.setPage(pageMapper.fromPageToRestResponsePage(technologyV2Page));
		technologyV2Response.setSort(sortMapper.map(input.getSort()));
		technologyV2Response.getTechnology().addAll(technologyBaseRestMapper.mapV2Base(technologyV2Page.getContent()));
		return technologyV2Response;
	}

	@Override
	public TechnologyV2FullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		TechnologyV2RestBeanParams technologyV2RestBeanParams = new TechnologyV2RestBeanParams();
		technologyV2RestBeanParams.setUid(uid);
		Page<Technology> technologyPage = technologyRestQuery.query(technologyV2RestBeanParams);
		TechnologyV2FullResponse technologyV2Response = new TechnologyV2FullResponse();
		technologyV2Response.setTechnology(technologyFullRestMapper.mapV2Full(Iterables.getOnlyElement(technologyPage.getContent(), null)));
		return technologyV2Response;
	}
}
