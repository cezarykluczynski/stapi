package com.cezarykluczynski.stapi.server.comics.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsFullResponse;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.server.comics.dto.ComicsRestBeanParams;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseRestMapper;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsFullRestMapper;
import com.cezarykluczynski.stapi.server.comics.query.ComicsRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ComicsRestReader implements BaseReader<ComicsRestBeanParams, ComicsBaseResponse>, FullReader<String, ComicsFullResponse> {

	private final ComicsRestQuery comicsRestQuery;

	private final ComicsBaseRestMapper comicsBaseRestMapper;

	private final ComicsFullRestMapper comicsFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public ComicsRestReader(ComicsRestQuery comicsRestQuery, ComicsBaseRestMapper comicsBaseRestMapper, ComicsFullRestMapper comicsFullRestMapper,
			PageMapper pageMapper, SortMapper sortMapper) {
		this.comicsRestQuery = comicsRestQuery;
		this.comicsBaseRestMapper = comicsBaseRestMapper;
		this.comicsFullRestMapper = comicsFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public ComicsBaseResponse readBase(ComicsRestBeanParams input) {
		Page<Comics> comicsPage = comicsRestQuery.query(input);
		ComicsBaseResponse comicsResponse = new ComicsBaseResponse();
		comicsResponse.setPage(pageMapper.fromPageToRestResponsePage(comicsPage));
		comicsResponse.setSort(sortMapper.map(input.getSort()));
		comicsResponse.getComics().addAll(comicsBaseRestMapper.mapBase(comicsPage.getContent()));
		return comicsResponse;
	}

	@Override
	public ComicsFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		ComicsRestBeanParams comicsRestBeanParams = new ComicsRestBeanParams();
		comicsRestBeanParams.setUid(uid);
		Page<Comics> comicsPage = comicsRestQuery.query(comicsRestBeanParams);
		ComicsFullResponse comicsResponse = new ComicsFullResponse();
		comicsResponse.setComics(comicsFullRestMapper.mapFull(Iterables.getOnlyElement(comicsPage.getContent(), null)));
		return comicsResponse;
	}

}
