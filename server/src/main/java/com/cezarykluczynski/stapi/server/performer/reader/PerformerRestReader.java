package com.cezarykluczynski.stapi.server.performer.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerResponse;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.Reader;
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerRestMapper;
import com.cezarykluczynski.stapi.server.performer.query.PerformerQueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class PerformerRestReader implements Reader<PerformerRestBeanParams, PerformerResponse> {

	private PerformerQueryBuilder performerQueryBuilder;

	private PerformerRestMapper performerRestMapper;

	private PageMapper pageMapper;

	@Inject
	public PerformerRestReader(PerformerQueryBuilder performerQueryBuilder, PerformerRestMapper performerRestMapper,
			PageMapper pageMapper) {
		this.performerQueryBuilder = performerQueryBuilder;
		this.performerRestMapper = performerRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public PerformerResponse read(PerformerRestBeanParams input) {
		Page<Performer> seriesPage = performerQueryBuilder.query(input);
		PerformerResponse performerResponse = new PerformerResponse();
		performerResponse.setPage(pageMapper.fromPageToRestResponsePage(seriesPage));
		performerResponse.getPerformers().addAll(performerRestMapper.map(seriesPage.getContent()));
		return performerResponse;
	}

}
