package com.cezarykluczynski.stapi.server.comics.reader;

import com.cezarykluczynski.stapi.client.v1.soap.ComicsRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsResponse;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsSoapMapper;
import com.cezarykluczynski.stapi.server.comics.query.ComicsSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.Reader;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ComicsSoapReader implements Reader<ComicsRequest, ComicsResponse> {

	private ComicsSoapQuery comicsSoapQuery;

	private ComicsSoapMapper comicsSoapMapper;

	private PageMapper pageMapper;

	public ComicsSoapReader(ComicsSoapQuery comicsSoapQuery, ComicsSoapMapper comicsSoapMapper, PageMapper pageMapper) {
		this.comicsSoapQuery = comicsSoapQuery;
		this.comicsSoapMapper = comicsSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public ComicsResponse read(ComicsRequest input) {
		Page<Comics> comicsPage = comicsSoapQuery.query(input);
		ComicsResponse comicsResponse = new ComicsResponse();
		comicsResponse.setPage(pageMapper.fromPageToSoapResponsePage(comicsPage));
		comicsResponse.getComics().addAll(comicsSoapMapper.map(comicsPage.getContent()));
		return comicsResponse;
	}

}
