package com.cezarykluczynski.stapi.server.comics.reader;

import com.cezarykluczynski.stapi.client.v1.soap.ComicsBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullResponse;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsFullSoapMapper;
import com.cezarykluczynski.stapi.server.comics.query.ComicsSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ComicsSoapReader implements BaseReader<ComicsBaseRequest, ComicsBaseResponse>, FullReader<ComicsFullRequest, ComicsFullResponse> {

	private final ComicsSoapQuery comicsSoapQuery;

	private final ComicsBaseSoapMapper comicsBaseSoapMapper;

	private final ComicsFullSoapMapper comicsFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public ComicsSoapReader(ComicsSoapQuery comicsSoapQuery, ComicsBaseSoapMapper comicsBaseSoapMapper, ComicsFullSoapMapper comicsFullSoapMapper,
			PageMapper pageMapper, SortMapper sortMapper) {
		this.comicsSoapQuery = comicsSoapQuery;
		this.comicsBaseSoapMapper = comicsBaseSoapMapper;
		this.comicsFullSoapMapper = comicsFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public ComicsBaseResponse readBase(ComicsBaseRequest input) {
		Page<Comics> comicsPage = comicsSoapQuery.query(input);
		ComicsBaseResponse comicsResponse = new ComicsBaseResponse();
		comicsResponse.setPage(pageMapper.fromPageToSoapResponsePage(comicsPage));
		comicsResponse.setSort(sortMapper.map(input.getSort()));
		comicsResponse.getComics().addAll(comicsBaseSoapMapper.mapBase(comicsPage.getContent()));
		return comicsResponse;
	}

	@Override
	public ComicsFullResponse readFull(ComicsFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Comics> comicsPage = comicsSoapQuery.query(input);
		ComicsFullResponse comicsFullResponse = new ComicsFullResponse();
		comicsFullResponse.setComics(comicsFullSoapMapper.mapFull(Iterables.getOnlyElement(comicsPage.getContent(), null)));
		return comicsFullResponse;
	}

}
