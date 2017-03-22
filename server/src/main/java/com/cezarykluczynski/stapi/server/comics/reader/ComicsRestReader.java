package com.cezarykluczynski.stapi.server.comics.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsFullResponse;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.server.comics.dto.ComicsRestBeanParams;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseRestMapper;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsFullRestMapper;
import com.cezarykluczynski.stapi.server.comics.query.ComicsRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicsRestReader implements BaseReader<ComicsRestBeanParams, ComicsBaseResponse>, FullReader<String, ComicsFullResponse> {

	private ComicsRestQuery comicsRestQuery;

	private ComicsBaseRestMapper comicsBaseRestMapper;

	private ComicsFullRestMapper comicsFullRestMapper;

	private PageMapper pageMapper;

	@Inject
	public ComicsRestReader(ComicsRestQuery comicsRestQuery, ComicsBaseRestMapper comicsBaseRestMapper, ComicsFullRestMapper comicsFullRestMapper,
			PageMapper pageMapper) {
		this.comicsRestQuery = comicsRestQuery;
		this.comicsBaseRestMapper = comicsBaseRestMapper;
		this.comicsFullRestMapper = comicsFullRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public ComicsBaseResponse readBase(ComicsRestBeanParams comicsRestBeanParams) {
		Page<Comics> comicsPage = comicsRestQuery.query(comicsRestBeanParams);
		ComicsBaseResponse comicsResponse = new ComicsBaseResponse();
		comicsResponse.setPage(pageMapper.fromPageToRestResponsePage(comicsPage));
		comicsResponse.getComics().addAll(comicsBaseRestMapper.mapBase(comicsPage.getContent()));
		return comicsResponse;
	}

	@Override
	public ComicsFullResponse readFull(String guid) {
		Preconditions.checkNotNull(guid, "GUID is required");
		ComicsRestBeanParams comicsRestBeanParams = new ComicsRestBeanParams();
		comicsRestBeanParams.setGuid(guid);
		Page<Comics> comicsPage = comicsRestQuery.query(comicsRestBeanParams);
		ComicsFullResponse comicsResponse = new ComicsFullResponse();
		comicsResponse.setComics(comicsFullRestMapper.mapFull(Iterables.getOnlyElement(comicsPage.getContent(), null)));
		return comicsResponse;
	}

}
