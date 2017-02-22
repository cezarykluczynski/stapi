package com.cezarykluczynski.stapi.server.comicStrip.reader;

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripResponse;
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip;
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripSoapMapper;
import com.cezarykluczynski.stapi.server.comicStrip.query.ComicStripSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.Reader;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ComicStripSoapReader implements Reader<ComicStripRequest, ComicStripResponse> {

	private ComicStripSoapQuery comicStripSoapQuery;

	private ComicStripSoapMapper comicStripSoapMapper;

	private PageMapper pageMapper;

	public ComicStripSoapReader(ComicStripSoapQuery comicStripSoapQuery, ComicStripSoapMapper comicStripSoapMapper, PageMapper pageMapper) {
		this.comicStripSoapQuery = comicStripSoapQuery;
		this.comicStripSoapMapper = comicStripSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public ComicStripResponse read(ComicStripRequest input) {
		Page<ComicStrip> comicStripPage = comicStripSoapQuery.query(input);
		ComicStripResponse comicStripResponse = new ComicStripResponse();
		comicStripResponse.setPage(pageMapper.fromPageToSoapResponsePage(comicStripPage));
		comicStripResponse.getComicStrip().addAll(comicStripSoapMapper.map(comicStripPage.getContent()));
		return comicStripResponse;
	}

}
