package com.cezarykluczynski.stapi.server.performer.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerV2FullResponse;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.performer.dto.PerformerV2RestBeanParams;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseRestMapper;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerFullRestMapper;
import com.cezarykluczynski.stapi.server.performer.query.PerformerRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PerformerV2RestReader implements BaseReader<PerformerV2RestBeanParams, PerformerV2BaseResponse>,
		FullReader<PerformerV2FullResponse> {

	private final PerformerRestQuery performerRestQuery;

	private final PerformerBaseRestMapper performerBaseRestMapper;

	private final PerformerFullRestMapper performerFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public PerformerV2RestReader(PerformerRestQuery performerRestQuery, PerformerBaseRestMapper performerBaseRestMapper,
			PerformerFullRestMapper performerFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.performerRestQuery = performerRestQuery;
		this.performerBaseRestMapper = performerBaseRestMapper;
		this.performerFullRestMapper = performerFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public PerformerV2BaseResponse readBase(PerformerV2RestBeanParams input) {
		Page<Performer> performerPage = performerRestQuery.query(input);
		PerformerV2BaseResponse performerResponse = new PerformerV2BaseResponse();
		performerResponse.setPage(pageMapper.fromPageToRestResponsePage(performerPage));
		performerResponse.setSort(sortMapper.map(input.getSort()));
		performerResponse.getPerformers().addAll(performerBaseRestMapper.mapV2Base(performerPage.getContent()));
		return performerResponse;
	}

	@Override
	public PerformerV2FullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		PerformerV2RestBeanParams performerV2RestBeanParams = new PerformerV2RestBeanParams();
		performerV2RestBeanParams.setUid(uid);
		Page<Performer> performerPage = performerRestQuery.query(performerV2RestBeanParams);
		PerformerV2FullResponse performerResponse = new PerformerV2FullResponse();
		performerResponse.setPerformer(performerFullRestMapper.mapV2Full(Iterables.getOnlyElement(performerPage.getContent(), null)));
		return performerResponse;
	}

}
