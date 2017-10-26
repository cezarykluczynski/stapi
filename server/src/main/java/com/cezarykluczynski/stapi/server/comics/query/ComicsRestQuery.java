package com.cezarykluczynski.stapi.server.comics.query;

import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.comics.repository.ComicsRepository;
import com.cezarykluczynski.stapi.server.comics.dto.ComicsRestBeanParams;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ComicsRestQuery {

	private final ComicsBaseRestMapper comicsBaseRestMapper;

	private final PageMapper pageMapper;

	private final ComicsRepository comicsRepository;

	public ComicsRestQuery(ComicsBaseRestMapper comicsBaseRestMapper, PageMapper pageMapper, ComicsRepository comicsRepository) {
		this.comicsBaseRestMapper = comicsBaseRestMapper;
		this.pageMapper = pageMapper;
		this.comicsRepository = comicsRepository;
	}

	public Page<Comics> query(ComicsRestBeanParams comicsRestBeanParams) {
		ComicsRequestDTO comicsRequestDTO = comicsBaseRestMapper.mapBase(comicsRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(comicsRestBeanParams);
		return comicsRepository.findMatching(comicsRequestDTO, pageRequest);
	}

}
