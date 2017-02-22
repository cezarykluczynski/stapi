package com.cezarykluczynski.stapi.server.comicStrip.query;

import com.cezarykluczynski.stapi.model.comicStrip.dto.ComicStripRequestDTO;
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip;
import com.cezarykluczynski.stapi.model.comicStrip.repository.ComicStripRepository;
import com.cezarykluczynski.stapi.server.comicStrip.dto.ComicStripRestBeanParams;
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicStripRestQuery {

	private ComicStripRestMapper comicStripRestMapper;

	private PageMapper pageMapper;

	private ComicStripRepository comicStripRepository;

	@Inject
	public ComicStripRestQuery(ComicStripRestMapper comicStripRestMapper, PageMapper pageMapper, ComicStripRepository comicStripRepository) {
		this.comicStripRestMapper = comicStripRestMapper;
		this.pageMapper = pageMapper;
		this.comicStripRepository = comicStripRepository;
	}

	public Page<ComicStrip> query(ComicStripRestBeanParams comicStripRestBeanParams) {
		ComicStripRequestDTO comicStripRequestDTO = comicStripRestMapper.map(comicStripRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(comicStripRestBeanParams);
		return comicStripRepository.findMatching(comicStripRequestDTO, pageRequest);
	}

}
