package com.cezarykluczynski.stapi.server.performer.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerFullResponse;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerRestMapper;
import com.cezarykluczynski.stapi.server.performer.query.PerformerRestQuery;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class PerformerRestReader implements BaseReader<PerformerRestBeanParams, PerformerBaseResponse>, FullReader<String, PerformerFullResponse> {

	private PerformerRestQuery performerRestQuery;

	private PerformerRestMapper performerRestMapper;

	private PageMapper pageMapper;

	@Inject
	public PerformerRestReader(PerformerRestQuery performerRestQuery, PerformerRestMapper performerRestMapper, PageMapper pageMapper) {
		this.performerRestQuery = performerRestQuery;
		this.performerRestMapper = performerRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public PerformerBaseResponse readBase(PerformerRestBeanParams performerRestBeanParams) {
		Page<Performer> performerPage = performerRestQuery.query(performerRestBeanParams);
		PerformerBaseResponse performerResponse = new PerformerBaseResponse();
		performerResponse.setPage(pageMapper.fromPageToRestResponsePage(performerPage));
		performerResponse.getPerformers().addAll(performerRestMapper.mapBase(performerPage.getContent()));
		return performerResponse;
	}

	@Override
	public PerformerFullResponse readFull(String guid) {
		Preconditions.checkNotNull(guid, "GUID is required");
		PerformerRestBeanParams performerRestBeanParams = new PerformerRestBeanParams();
		performerRestBeanParams.setGuid(guid);
		Page<Performer> performerPage = performerRestQuery.query(performerRestBeanParams);
		PerformerFullResponse performerResponse = new PerformerFullResponse();
		performerResponse.setPerformer(performerRestMapper.mapFull(Iterables.getOnlyElement(performerPage.getContent(), null)));
		return performerResponse;
	}

}
