package com.cezarykluczynski.stapi.server.performer.reader;

import com.cezarykluczynski.stapi.client.v1.soap.PerformerRequest;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerResponse;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.Reader;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerSoapMapper;
import com.cezarykluczynski.stapi.server.performer.query.PerformerQueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PerformerSoapReader implements Reader<PerformerRequest, PerformerResponse> {

	private PerformerQueryBuilder performerQueryBuilder;

	private PerformerSoapMapper performerSoapMapper;

	private PageMapper pageMapper;

	public PerformerSoapReader(PerformerQueryBuilder performerQueryBuilder, PerformerSoapMapper performerSoapMapper,
			PageMapper pageMapper) {
		this.performerQueryBuilder = performerQueryBuilder;
		this.performerSoapMapper = performerSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public PerformerResponse read(PerformerRequest input) {
		Page<Performer> seriesPage = performerQueryBuilder.query(input);
		PerformerResponse performerResponse = new PerformerResponse();
		performerResponse.setPage(pageMapper.fromPageToSoapResponsePage(seriesPage));
		performerResponse.getPerformers().addAll(performerSoapMapper.map(seriesPage.getContent()));
		return performerResponse;
	}

}
