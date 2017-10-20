package com.cezarykluczynski.stapi.server.technology.reader;

import com.cezarykluczynski.stapi.client.v1.soap.TechnologyBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFullResponse;
import com.cezarykluczynski.stapi.model.technology.entity.Technology;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyBaseSoapMapper;
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyFullSoapMapper;
import com.cezarykluczynski.stapi.server.technology.query.TechnologySoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TechnologySoapReader implements BaseReader<TechnologyBaseRequest, TechnologyBaseResponse>,
		FullReader<TechnologyFullRequest, TechnologyFullResponse> {

	private final TechnologySoapQuery technologySoapQuery;

	private final TechnologyBaseSoapMapper technologyBaseSoapMapper;

	private final TechnologyFullSoapMapper technologyFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public TechnologySoapReader(TechnologySoapQuery technologySoapQuery, TechnologyBaseSoapMapper technologyBaseSoapMapper,
			TechnologyFullSoapMapper technologyFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.technologySoapQuery = technologySoapQuery;
		this.technologyBaseSoapMapper = technologyBaseSoapMapper;
		this.technologyFullSoapMapper = technologyFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public TechnologyBaseResponse readBase(TechnologyBaseRequest input) {
		Page<Technology> technologyPage = technologySoapQuery.query(input);
		TechnologyBaseResponse technologyResponse = new TechnologyBaseResponse();
		technologyResponse.setPage(pageMapper.fromPageToSoapResponsePage(technologyPage));
		technologyResponse.setSort(sortMapper.map(input.getSort()));
		technologyResponse.getTechnology().addAll(technologyBaseSoapMapper.mapBase(technologyPage.getContent()));
		return technologyResponse;
	}

	@Override
	public TechnologyFullResponse readFull(TechnologyFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Technology> technologyPage = technologySoapQuery.query(input);
		TechnologyFullResponse technologyFullResponse = new TechnologyFullResponse();
		technologyFullResponse.setTechnology(technologyFullSoapMapper.mapFull(Iterables.getOnlyElement(technologyPage.getContent(), null)));
		return technologyFullResponse;
	}

}
