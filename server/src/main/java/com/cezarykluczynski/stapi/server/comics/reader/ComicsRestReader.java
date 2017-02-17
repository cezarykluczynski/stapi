package com.cezarykluczynski.stapi.server.comics.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsResponse;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.server.comics.dto.ComicsRestBeanParams;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsRestMapper;
import com.cezarykluczynski.stapi.server.comics.query.ComicsRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.Reader;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicsRestReader implements Reader<ComicsRestBeanParams, ComicsResponse> {

	private ComicsRestQuery comicsRestQuery;

	private ComicsRestMapper comicsRestMapper;

	private PageMapper pageMapper;

	@Inject
	public ComicsRestReader(ComicsRestQuery comicsRestQuery, ComicsRestMapper comicsRestMapper, PageMapper pageMapper) {
		this.comicsRestQuery = comicsRestQuery;
		this.comicsRestMapper = comicsRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public ComicsResponse read(ComicsRestBeanParams input) {
		Page<Comics> comicsPage = comicsRestQuery.query(input);
		ComicsResponse comicsResponse = new ComicsResponse();
		comicsResponse.setPage(pageMapper.fromPageToRestResponsePage(comicsPage));
		comicsResponse.getComics().addAll(comicsRestMapper.map(comicsPage.getContent()));
		return comicsResponse;
	}

}
