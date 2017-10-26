package com.cezarykluczynski.stapi.server.comic_strip.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripFullResponse;
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip;
import com.cezarykluczynski.stapi.server.comic_strip.dto.ComicStripRestBeanParams;
import com.cezarykluczynski.stapi.server.comic_strip.mapper.ComicStripBaseRestMapper;
import com.cezarykluczynski.stapi.server.comic_strip.mapper.ComicStripFullRestMapper;
import com.cezarykluczynski.stapi.server.comic_strip.query.ComicStripRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ComicStripRestReader implements BaseReader<ComicStripRestBeanParams, ComicStripBaseResponse>,
		FullReader<String, ComicStripFullResponse> {

	private final ComicStripRestQuery comicStripRestQuery;

	private final ComicStripBaseRestMapper comicStripBaseRestMapper;

	private final ComicStripFullRestMapper comicStripFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public ComicStripRestReader(ComicStripRestQuery comicStripRestQuery, ComicStripBaseRestMapper comicStripBaseRestMapper,
			ComicStripFullRestMapper comicStripFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.comicStripRestQuery = comicStripRestQuery;
		this.comicStripBaseRestMapper = comicStripBaseRestMapper;
		this.comicStripFullRestMapper = comicStripFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public ComicStripBaseResponse readBase(ComicStripRestBeanParams input) {
		Page<ComicStrip> comicStripPage = comicStripRestQuery.query(input);
		ComicStripBaseResponse comicStripResponse = new ComicStripBaseResponse();
		comicStripResponse.setPage(pageMapper.fromPageToRestResponsePage(comicStripPage));
		comicStripResponse.setSort(sortMapper.map(input.getSort()));
		comicStripResponse.getComicStrips().addAll(comicStripBaseRestMapper.mapBase(comicStripPage.getContent()));
		return comicStripResponse;
	}

	@Override
	public ComicStripFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		ComicStripRestBeanParams comicStripRestBeanParams = new ComicStripRestBeanParams();
		comicStripRestBeanParams.setUid(uid);
		Page<ComicStrip> comicStripPage = comicStripRestQuery.query(comicStripRestBeanParams);
		ComicStripFullResponse comicStripResponse = new ComicStripFullResponse();
		comicStripResponse.setComicStrip(comicStripFullRestMapper.mapFull(Iterables.getOnlyElement(comicStripPage.getContent(), null)));
		return comicStripResponse;
	}

}
