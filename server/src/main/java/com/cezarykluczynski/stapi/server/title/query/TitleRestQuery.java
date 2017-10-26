package com.cezarykluczynski.stapi.server.title.query;

import com.cezarykluczynski.stapi.model.title.dto.TitleRequestDTO;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.model.title.repository.TitleRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.title.dto.TitleRestBeanParams;
import com.cezarykluczynski.stapi.server.title.mapper.TitleBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TitleRestQuery {

	private final TitleBaseRestMapper titleBaseRestMapper;

	private final PageMapper pageMapper;

	private final TitleRepository titleRepository;

	public TitleRestQuery(TitleBaseRestMapper titleBaseRestMapper, PageMapper pageMapper, TitleRepository titleRepository) {
		this.titleBaseRestMapper = titleBaseRestMapper;
		this.pageMapper = pageMapper;
		this.titleRepository = titleRepository;
	}

	public Page<Title> query(TitleRestBeanParams titleRestBeanParams) {
		TitleRequestDTO titleRequestDTO = titleBaseRestMapper.mapBase(titleRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(titleRestBeanParams);
		return titleRepository.findMatching(titleRequestDTO, pageRequest);
	}

}
