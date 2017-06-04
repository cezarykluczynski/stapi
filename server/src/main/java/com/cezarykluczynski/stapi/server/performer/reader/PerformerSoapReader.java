package com.cezarykluczynski.stapi.server.performer.reader;

import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullResponse;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseSoapMapper;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerFullSoapMapper;
import com.cezarykluczynski.stapi.server.performer.query.PerformerSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PerformerSoapReader implements BaseReader<PerformerBaseRequest, PerformerBaseResponse>,
		FullReader<PerformerFullRequest, PerformerFullResponse> {

	private final PerformerSoapQuery performerSoapQuery;

	private final PerformerBaseSoapMapper performerBaseSoapMapper;

	private final PerformerFullSoapMapper performerFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public PerformerSoapReader(PerformerSoapQuery performerSoapQuery, PerformerBaseSoapMapper performerBaseSoapMapper,
			PerformerFullSoapMapper performerFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.performerSoapQuery = performerSoapQuery;
		this.performerBaseSoapMapper = performerBaseSoapMapper;
		this.performerFullSoapMapper = performerFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public PerformerBaseResponse readBase(PerformerBaseRequest input) {
		Page<Performer> performerPage = performerSoapQuery.query(input);
		PerformerBaseResponse performerResponse = new PerformerBaseResponse();
		performerResponse.setPage(pageMapper.fromPageToSoapResponsePage(performerPage));
		performerResponse.setSort(sortMapper.map(input.getSort()));
		performerResponse.getPerformers().addAll(performerBaseSoapMapper.mapBase(performerPage.getContent()));
		return performerResponse;
	}

	@Override
	public PerformerFullResponse readFull(PerformerFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Performer> performerPage = performerSoapQuery.query(input);
		PerformerFullResponse performerFullResponse = new PerformerFullResponse();
		performerFullResponse.setPerformer(performerFullSoapMapper.mapFull(Iterables.getOnlyElement(performerPage.getContent(), null)));
		return performerFullResponse;
	}

}
