package com.cezarykluczynski.stapi.server.title.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.TitleV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleV2FullResponse;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.title.dto.TitleV2RestBeanParams;
import com.cezarykluczynski.stapi.server.title.mapper.TitleBaseRestMapper;
import com.cezarykluczynski.stapi.server.title.mapper.TitleFullRestMapper;
import com.cezarykluczynski.stapi.server.title.query.TitleRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TitleV2RestReader implements BaseReader<TitleV2RestBeanParams, TitleV2BaseResponse>,
		FullReader<String, TitleV2FullResponse> {

	private final TitleRestQuery titleRestQuery;

	private final TitleBaseRestMapper titleBaseRestMapper;

	private final TitleFullRestMapper titleFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public TitleV2RestReader(TitleRestQuery titleRestQuery, TitleBaseRestMapper titleBaseRestMapper,
			TitleFullRestMapper titleFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.titleRestQuery = titleRestQuery;
		this.titleBaseRestMapper = titleBaseRestMapper;
		this.titleFullRestMapper = titleFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public TitleV2BaseResponse readBase(TitleV2RestBeanParams input) {
		Page<Title> titleV2Page = titleRestQuery.query(input);
		TitleV2BaseResponse titleV2Response = new TitleV2BaseResponse();
		titleV2Response.setPage(pageMapper.fromPageToRestResponsePage(titleV2Page));
		titleV2Response.setSort(sortMapper.map(input.getSort()));
		titleV2Response.getTitles().addAll(titleBaseRestMapper.mapV2Base(titleV2Page.getContent()));
		return titleV2Response;
	}

	@Override
	public TitleV2FullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		TitleV2RestBeanParams titleV2RestBeanParams = new TitleV2RestBeanParams();
		titleV2RestBeanParams.setUid(uid);
		Page<Title> titlePage = titleRestQuery.query(titleV2RestBeanParams);
		TitleV2FullResponse titleV2Response = new TitleV2FullResponse();
		titleV2Response.setTitle(titleFullRestMapper.mapV2Full(Iterables.getOnlyElement(titlePage.getContent(), null)));
		return titleV2Response;
	}
}
