package com.cezarykluczynski.stapi.server.comicStrip.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripResponse;
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip;
import com.cezarykluczynski.stapi.server.comicStrip.dto.ComicStripRestBeanParams;
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripRestMapper;
import com.cezarykluczynski.stapi.server.comicStrip.query.ComicStripRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.Reader;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicStripRestReader implements Reader<ComicStripRestBeanParams, ComicStripResponse> {

	private ComicStripRestQuery comicStripRestQuery;

	private ComicStripRestMapper comicStripRestMapper;

	private PageMapper pageMapper;

	@Inject
	public ComicStripRestReader(ComicStripRestQuery comicStripRestQuery, ComicStripRestMapper comicStripRestMapper, PageMapper pageMapper) {
		this.comicStripRestQuery = comicStripRestQuery;
		this.comicStripRestMapper = comicStripRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public ComicStripResponse read(ComicStripRestBeanParams input) {
		Page<ComicStrip> comicStripPage = comicStripRestQuery.query(input);
		ComicStripResponse comicStripResponse = new ComicStripResponse();
		comicStripResponse.setPage(pageMapper.fromPageToRestResponsePage(comicStripPage));
		comicStripResponse.getComicStrips().addAll(comicStripRestMapper.map(comicStripPage.getContent()));
		return comicStripResponse;
	}

}
