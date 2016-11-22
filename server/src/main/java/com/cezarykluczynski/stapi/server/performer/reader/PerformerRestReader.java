package com.cezarykluczynski.stapi.server.performer.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerResponse;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.Reader;
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerRestMapper;
import com.cezarykluczynski.stapi.server.performer.query.PerformerRestQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class PerformerRestReader implements Reader<PerformerRestBeanParams, PerformerResponse> {

	private PerformerRestQuery performerRestQuery;

	private PerformerRestMapper performerRestMapper;

	private PageMapper pageMapper;

	@Inject
	public PerformerRestReader(PerformerRestQuery performerRestQuery, PerformerRestMapper performerRestMapper,
			PageMapper pageMapper) {
		this.performerRestQuery = performerRestQuery;
		this.performerRestMapper = performerRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public PerformerResponse read(PerformerRestBeanParams input) {
		Page<Performer> performerPage = performerRestQuery.query(input);
		PerformerResponse performerResponse = new PerformerResponse();
		performerResponse.setPage(pageMapper.fromPageToRestResponsePage(performerPage));
		performerResponse.getPerformers().addAll(performerRestMapper.map(performerPage.getContent()));
		return performerResponse;
	}

}
