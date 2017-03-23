package com.cezarykluczynski.stapi.server.comicStrip.query;

import com.cezarykluczynski.stapi.model.comicStrip.dto.ComicStripRequestDTO;
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip;
import com.cezarykluczynski.stapi.model.comicStrip.repository.ComicStripRepository;
import com.cezarykluczynski.stapi.server.comicStrip.dto.ComicStripRestBeanParams;
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripBaseRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicStripRestQuery {

	private ComicStripBaseRestMapper comicStripBaseRestMapper;

	private PageMapper pageMapper;

	private ComicStripRepository comicStripRepository;

	@Inject
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
