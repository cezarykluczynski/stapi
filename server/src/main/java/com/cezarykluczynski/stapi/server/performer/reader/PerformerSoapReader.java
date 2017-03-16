package com.cezarykluczynski.stapi.server.performer.reader;

import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullResponse;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerSoapMapper;
import com.cezarykluczynski.stapi.server.performer.query.PerformerSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PerformerSoapReader implements BaseReader<PerformerBaseRequest, PerformerBaseResponse>,
		FullReader<PerformerFullRequest, PerformerFullResponse> {

	private PerformerSoapQuery performerSoapQuery;

	private PerformerSoapMapper performerSoapMapper;

	private PageMapper pageMapper;

	public PerformerSoapReader(PerformerSoapQuery performerSoapQuery, PerformerSoapMapper performerSoapMapper, PageMapper pageMapper) {
		this.performerSoapQuery = performerSoapQuery;
		this.performerSoapMapper = performerSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public PerformerBaseResponse readBase(PerformerBaseRequest input) {
		Page<Performer> performerPage = performerSoapQuery.query(input);
		PerformerBaseResponse performerResponse = new PerformerBaseResponse();
		performerResponse.setPage(pageMapper.fromPageToSoapResponsePage(performerPage));
		performerResponse.getPerformers().addAll(performerSoapMapper.mapBase(performerPage.getContent()));
		return performerResponse;
	}

	@Override
	public PerformerFullResponse readFull(PerformerFullRequest input) {
		Page<Performer> performerPage = performerSoapQuery.query(input);
		PerformerFullResponse performerFullResponse = new PerformerFullResponse();
		performerFullResponse.setPerformer(performerSoapMapper.mapFull(Iterables.getOnlyElement(performerPage.getContent(), null)));
		return performerFullResponse;
	}
}
