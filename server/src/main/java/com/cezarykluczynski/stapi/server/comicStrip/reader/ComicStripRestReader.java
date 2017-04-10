package com.cezarykluczynski.stapi.server.comicStrip.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripFullResponse;
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip;
import com.cezarykluczynski.stapi.server.comicStrip.dto.ComicStripRestBeanParams;
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripBaseRestMapper;
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripFullRestMapper;
import com.cezarykluczynski.stapi.server.comicStrip.query.ComicStripRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicStripRestReader implements BaseReader<ComicStripRestBeanParams, ComicStripBaseResponse>,
		FullReader<String, ComicStripFullResponse> {

	private ComicStripRestQuery comicStripRestQuery;

	private ComicStripBaseRestMapper comicStripBaseRestMapper;

	private ComicStripFullRestMapper comicStripFullRestMapper;

	private PageMapper pageMapper;

	@Inject
	public ComicStripRestReader(ComicStripRestQuery comicStripRestQuery, ComicStripBaseRestMapper comicStripBaseRestMapper,
			ComicStripFullRestMapper comicStripFullRestMapper, PageMapper pageMapper) {
		this.comicStripRestQuery = comicStripRestQuery;
		this.comicStripBaseRestMapper = comicStripBaseRestMapper;
		this.comicStripFullRestMapper = comicStripFullRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public ComicStripBaseResponse readBase(ComicStripRestBeanParams comicStripRestBeanParams) {
		Page<ComicStrip> comicStripPage = comicStripRestQuery.query(comicStripRestBeanParams);
		ComicStripBaseResponse comicStripResponse = new ComicStripBaseResponse();
		comicStripResponse.setPage(pageMapper.fromPageToRestResponsePage(comicStripPage));
		comicStripResponse.getComicStrips().addAll(comicStripBaseRestMapper.mapBase(comicStripPage.getContent()));
		return comicStripResponse;
	}

	@Override
	public ComicStripFullResponse readFull(String guid) {
		StaticValidator.requireGuid(guid);
		ComicStripRestBeanParams comicStripRestBeanParams = new ComicStripRestBeanParams();
		comicStripRestBeanParams.setGuid(guid);
		Page<ComicStrip> comicStripPage = comicStripRestQuery.query(comicStripRestBeanParams);
		ComicStripFullResponse comicStripResponse = new ComicStripFullResponse();
		comicStripResponse.setComicStrip(comicStripFullRestMapper.mapFull(Iterables.getOnlyElement(comicStripPage.getContent(), null)));
		return comicStripResponse;
	}

}
