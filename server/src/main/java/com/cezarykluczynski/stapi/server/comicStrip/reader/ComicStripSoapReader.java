package com.cezarykluczynski.stapi.server.comicStrip.reader;

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullResponse;
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip;
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripFullSoapMapper;
import com.cezarykluczynski.stapi.server.comicStrip.query.ComicStripSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ComicStripSoapReader implements BaseReader<ComicStripBaseRequest, ComicStripBaseResponse>,
		FullReader<ComicStripFullRequest, ComicStripFullResponse> {

	private ComicStripSoapQuery comicStripSoapQuery;

	private ComicStripBaseSoapMapper comicStripBaseSoapMapper;

	private ComicStripFullSoapMapper comicStripFullSoapMapper;

	private PageMapper pageMapper;

	public ComicStripSoapReader(ComicStripSoapQuery comicStripSoapQuery, ComicStripBaseSoapMapper comicStripBaseSoapMapper,
			ComicStripFullSoapMapper comicStripFullSoapMapper, PageMapper pageMapper) {
		this.comicStripSoapQuery = comicStripSoapQuery;
		this.comicStripBaseSoapMapper = comicStripBaseSoapMapper;
		this.comicStripFullSoapMapper = comicStripFullSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public ComicStripBaseResponse readBase(ComicStripBaseRequest input) {
		Page<ComicStrip> comicStripPage = comicStripSoapQuery.query(input);
		ComicStripBaseResponse comicStripResponse = new ComicStripBaseResponse();
		comicStripResponse.setPage(pageMapper.fromPageToSoapResponsePage(comicStripPage));
		comicStripResponse.getComicStrips().addAll(comicStripBaseSoapMapper.mapBase(comicStripPage.getContent()));
		return comicStripResponse;
	}

	@Override
	public ComicStripFullResponse readFull(ComicStripFullRequest input) {
		Page<ComicStrip> comicStripPage = comicStripSoapQuery.query(input);
		ComicStripFullResponse comicStripFullResponse = new ComicStripFullResponse();
		comicStripFullResponse.setComicStrip(comicStripFullSoapMapper.mapFull(Iterables.getOnlyElement(comicStripPage.getContent(), null)));
		return comicStripFullResponse;
	}

}
