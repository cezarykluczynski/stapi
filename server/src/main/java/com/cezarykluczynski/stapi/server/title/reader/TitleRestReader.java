package com.cezarykluczynski.stapi.server.title.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.TitleBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleFullResponse;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.title.dto.TitleRestBeanParams;
import com.cezarykluczynski.stapi.server.title.mapper.TitleBaseRestMapper;
import com.cezarykluczynski.stapi.server.title.mapper.TitleFullRestMapper;
import com.cezarykluczynski.stapi.server.title.query.TitleRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TitleRestReader implements BaseReader<TitleRestBeanParams, TitleBaseResponse>, FullReader<String, TitleFullResponse> {

	private final TitleRestQuery titleRestQuery;

	private final TitleBaseRestMapper titleBaseRestMapper;

	private final TitleFullRestMapper titleFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public TitleRestReader(TitleRestQuery titleRestQuery, TitleBaseRestMapper titleBaseRestMapper, TitleFullRestMapper titleFullRestMapper,
			PageMapper pageMapper, SortMapper sortMapper) {
		this.titleRestQuery = titleRestQuery;
		this.titleBaseRestMapper = titleBaseRestMapper;
		this.titleFullRestMapper = titleFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public TitleBaseResponse readBase(TitleRestBeanParams input) {
		Page<Title> titlePage = titleRestQuery.query(input);
		TitleBaseResponse titleResponse = new TitleBaseResponse();
		titleResponse.setPage(pageMapper.fromPageToRestResponsePage(titlePage));
		titleResponse.setSort(sortMapper.map(input.getSort()));
		titleResponse.getTitles().addAll(titleBaseRestMapper.mapBase(titlePage.getContent()));
		return titleResponse;
	}

	@Override
	public TitleFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		TitleRestBeanParams titleRestBeanParams = new TitleRestBeanParams();
		titleRestBeanParams.setUid(uid);
		Page<Title> titlePage = titleRestQuery.query(titleRestBeanParams);
		TitleFullResponse titleResponse = new TitleFullResponse();
		titleResponse.setTitle(titleFullRestMapper.mapFull(Iterables.getOnlyElement(titlePage.getContent(), null)));
		return titleResponse;
	}
}
