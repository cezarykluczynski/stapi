package com.cezarykluczynski.stapi.server.title.reader;

import com.cezarykluczynski.stapi.client.v1.soap.TitleBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TitleBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TitleFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TitleFullResponse;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.title.mapper.TitleBaseSoapMapper;
import com.cezarykluczynski.stapi.server.title.mapper.TitleFullSoapMapper;
import com.cezarykluczynski.stapi.server.title.query.TitleSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TitleSoapReader implements BaseReader<TitleBaseRequest, TitleBaseResponse>,
		FullReader<TitleFullRequest, TitleFullResponse> {

	private final TitleSoapQuery titleSoapQuery;

	private final TitleBaseSoapMapper titleBaseSoapMapper;

	private final TitleFullSoapMapper titleFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public TitleSoapReader(TitleSoapQuery titleSoapQuery, TitleBaseSoapMapper titleBaseSoapMapper, TitleFullSoapMapper titleFullSoapMapper,
			PageMapper pageMapper, SortMapper sortMapper) {
		this.titleSoapQuery = titleSoapQuery;
		this.titleBaseSoapMapper = titleBaseSoapMapper;
		this.titleFullSoapMapper = titleFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public TitleBaseResponse readBase(TitleBaseRequest input) {
		Page<Title> titlePage = titleSoapQuery.query(input);
		TitleBaseResponse titleResponse = new TitleBaseResponse();
		titleResponse.setPage(pageMapper.fromPageToSoapResponsePage(titlePage));
		titleResponse.setSort(sortMapper.map(input.getSort()));
		titleResponse.getTitles().addAll(titleBaseSoapMapper.mapBase(titlePage.getContent()));
		return titleResponse;
	}

	@Override
	public TitleFullResponse readFull(TitleFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Title> titlePage = titleSoapQuery.query(input);
		TitleFullResponse titleFullResponse = new TitleFullResponse();
		titleFullResponse.setTitle(titleFullSoapMapper.mapFull(Iterables.getOnlyElement(titlePage.getContent(), null)));
		return titleFullResponse;
	}

}
