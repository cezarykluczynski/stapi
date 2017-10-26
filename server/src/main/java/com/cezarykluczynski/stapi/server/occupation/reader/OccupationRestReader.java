package com.cezarykluczynski.stapi.server.occupation.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationFullResponse;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.occupation.dto.OccupationRestBeanParams;
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationBaseRestMapper;
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationFullRestMapper;
import com.cezarykluczynski.stapi.server.occupation.query.OccupationRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class OccupationRestReader implements BaseReader<OccupationRestBeanParams, OccupationBaseResponse>,
		FullReader<String, OccupationFullResponse> {

	private OccupationRestQuery occupationRestQuery;

	private OccupationBaseRestMapper occupationBaseRestMapper;

	private OccupationFullRestMapper occupationFullRestMapper;

	private PageMapper pageMapper;

	private final SortMapper sortMapper;

	public OccupationRestReader(OccupationRestQuery occupationRestQuery, OccupationBaseRestMapper occupationBaseRestMapper,
			OccupationFullRestMapper occupationFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.occupationRestQuery = occupationRestQuery;
		this.occupationBaseRestMapper = occupationBaseRestMapper;
		this.occupationFullRestMapper = occupationFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public OccupationBaseResponse readBase(OccupationRestBeanParams input) {
		Page<Occupation> occupationPage = occupationRestQuery.query(input);
		OccupationBaseResponse occupationResponse = new OccupationBaseResponse();
		occupationResponse.setPage(pageMapper.fromPageToRestResponsePage(occupationPage));
		occupationResponse.setSort(sortMapper.map(input.getSort()));
		occupationResponse.getOccupations().addAll(occupationBaseRestMapper.mapBase(occupationPage.getContent()));
		return occupationResponse;
	}

	@Override
	public OccupationFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		OccupationRestBeanParams occupationRestBeanParams = new OccupationRestBeanParams();
		occupationRestBeanParams.setUid(uid);
		Page<Occupation> occupationPage = occupationRestQuery.query(occupationRestBeanParams);
		OccupationFullResponse occupationResponse = new OccupationFullResponse();
		occupationResponse.setOccupation(occupationFullRestMapper.mapFull(Iterables.getOnlyElement(occupationPage.getContent(), null)));
		return occupationResponse;
	}
}
