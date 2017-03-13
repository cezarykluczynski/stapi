package com.cezarykluczynski.stapi.server.performer.reader;

import com.cezarykluczynski.stapi.client.v1.soap.PerformerRequest;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerResponse;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerSoapMapper;
import com.cezarykluczynski.stapi.server.performer.query.PerformerSoapQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PerformerSoapReader implements BaseReader<PerformerRequest, PerformerResponse> {

	private PerformerSoapQuery performerSoapQuery;

	private PerformerSoapMapper performerSoapMapper;

	private PageMapper pageMapper;

	public PerformerSoapReader(PerformerSoapQuery performerSoapQuery, PerformerSoapMapper performerSoapMapper, PageMapper pageMapper) {
		this.performerSoapQuery = performerSoapQuery;
		this.performerSoapMapper = performerSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public PerformerResponse readBase(PerformerRequest input) {
		Page<Performer> performerPage = performerSoapQuery.query(input);
		PerformerResponse performerResponse = new PerformerResponse();
		performerResponse.setPage(pageMapper.fromPageToSoapResponsePage(performerPage));
		performerResponse.getPerformers().addAll(performerSoapMapper.map(performerPage.getContent()));
		return performerResponse;
	}

}
