package com.cezarykluczynski.stapi.server.occupation.reader;

import com.cezarykluczynski.stapi.client.rest.model.OccupationV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.OccupationV2FullResponse;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.occupation.dto.OccupationV2RestBeanParams;
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationBaseRestMapper;
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationFullRestMapper;
import com.cezarykluczynski.stapi.server.occupation.query.OccupationRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class OccupationV2RestReader implements BaseReader<OccupationV2RestBeanParams, OccupationV2BaseResponse>,
		FullReader<OccupationV2FullResponse> {

	private final OccupationRestQuery occupationRestQuery;

	private final OccupationBaseRestMapper occupationBaseRestMapper;

	private final OccupationFullRestMapper occupationFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public OccupationV2RestReader(OccupationRestQuery occupationRestQuery, OccupationBaseRestMapper occupationBaseRestMapper,
			OccupationFullRestMapper occupationFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.occupationRestQuery = occupationRestQuery;
		this.occupationBaseRestMapper = occupationBaseRestMapper;
		this.occupationFullRestMapper = occupationFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public OccupationV2BaseResponse readBase(OccupationV2RestBeanParams input) {
		Page<Occupation> occupationV2Page = occupationRestQuery.query(input);
		OccupationV2BaseResponse occupationV2Response = new OccupationV2BaseResponse();
		occupationV2Response.setPage(pageMapper.fromPageToRestResponsePage(occupationV2Page));
		occupationV2Response.setSort(sortMapper.map(input.getSort()));
		occupationV2Response.getOccupations().addAll(occupationBaseRestMapper.mapV2Base(occupationV2Page.getContent()));
		return occupationV2Response;
	}

	@Override
	public OccupationV2FullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		OccupationV2RestBeanParams occupationV2RestBeanParams = new OccupationV2RestBeanParams();
		occupationV2RestBeanParams.setUid(uid);
		Page<Occupation> occupationPage = occupationRestQuery.query(occupationV2RestBeanParams);
		OccupationV2FullResponse occupationV2Response = new OccupationV2FullResponse();
		occupationV2Response.setOccupation(occupationFullRestMapper.mapV2Full(Iterables.getOnlyElement(occupationPage.getContent(), null)));
		return occupationV2Response;
	}
}
