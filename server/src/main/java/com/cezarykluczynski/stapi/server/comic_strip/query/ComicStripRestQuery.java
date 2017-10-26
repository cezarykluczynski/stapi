package com.cezarykluczynski.stapi.server.comic_strip.query;

import com.cezarykluczynski.stapi.model.comic_strip.dto.ComicStripRequestDTO;
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip;
import com.cezarykluczynski.stapi.model.comic_strip.repository.ComicStripRepository;
import com.cezarykluczynski.stapi.server.comic_strip.dto.ComicStripRestBeanParams;
import com.cezarykluczynski.stapi.server.comic_strip.mapper.ComicStripBaseRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ComicStripRestQuery {

	private final ComicStripBaseRestMapper comicStripBaseRestMapper;

	private final PageMapper pageMapper;

	private final ComicStripRepository comicStripRepository;

	public ComicStripRestQuery(ComicStripBaseRestMapper comicStripBaseRestMapper, PageMapper pageMapper, ComicStripRepository comicStripRepository) {
		this.comicStripBaseRestMapper = comicStripBaseRestMapper;
		this.pageMapper = pageMapper;
		this.comicStripRepository = comicStripRepository;
	}

	public Page<ComicStrip> query(ComicStripRestBeanParams comicStripRestBeanParams) {
		ComicStripRequestDTO comicStripRequestDTO = comicStripBaseRestMapper.mapBase(comicStripRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(comicStripRestBeanParams);
		return comicStripRepository.findMatching(comicStripRequestDTO, pageRequest);
	}

}
