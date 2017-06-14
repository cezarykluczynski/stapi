package com.cezarykluczynski.stapi.server.comic_strip.reader;

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullResponse;
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip;
import com.cezarykluczynski.stapi.server.comic_strip.mapper.ComicStripBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comic_strip.mapper.ComicStripFullSoapMapper;
import com.cezarykluczynski.stapi.server.comic_strip.query.ComicStripSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ComicStripSoapReader implements BaseReader<ComicStripBaseRequest, ComicStripBaseResponse>,
		FullReader<ComicStripFullRequest, ComicStripFullResponse> {

	private final ComicStripSoapQuery comicStripSoapQuery;

	private final ComicStripBaseSoapMapper comicStripBaseSoapMapper;

	private final ComicStripFullSoapMapper comicStripFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public ComicStripSoapReader(ComicStripSoapQuery comicStripSoapQuery, ComicStripBaseSoapMapper comicStripBaseSoapMapper,
			ComicStripFullSoapMapper comicStripFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.comicStripSoapQuery = comicStripSoapQuery;
		this.comicStripBaseSoapMapper = comicStripBaseSoapMapper;
		this.comicStripFullSoapMapper = comicStripFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public ComicStripBaseResponse readBase(ComicStripBaseRequest input) {
		Page<ComicStrip> comicStripPage = comicStripSoapQuery.query(input);
		ComicStripBaseResponse comicStripResponse = new ComicStripBaseResponse();
		comicStripResponse.setPage(pageMapper.fromPageToSoapResponsePage(comicStripPage));
		comicStripResponse.setSort(sortMapper.map(input.getSort()));
		comicStripResponse.getComicStrips().addAll(comicStripBaseSoapMapper.mapBase(comicStripPage.getContent()));
		return comicStripResponse;
	}

	@Override
	public ComicStripFullResponse readFull(ComicStripFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<ComicStrip> comicStripPage = comicStripSoapQuery.query(input);
		ComicStripFullResponse comicStripFullResponse = new ComicStripFullResponse();
		comicStripFullResponse.setComicStrip(comicStripFullSoapMapper.mapFull(Iterables.getOnlyElement(comicStripPage.getContent(), null)));
		return comicStripFullResponse;
	}

}
