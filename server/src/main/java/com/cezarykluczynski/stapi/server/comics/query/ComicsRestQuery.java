package com.cezarykluczynski.stapi.server.comics.query;

import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.comics.repository.ComicsRepository;
import com.cezarykluczynski.stapi.server.comics.dto.ComicsRestBeanParams;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicsRestQuery {

	private ComicsRestMapper comicsRestMapper;

	private PageMapper pageMapper;

	private ComicsRepository comicsRepository;

	@Inject
	public ComicsRestQuery(ComicsRestMapper comicsRestMapper, PageMapper pageMapper, ComicsRepository comicsRepository) {
		this.comicsRestMapper = comicsRestMapper;
		this.pageMapper = pageMapper;
		this.comicsRepository = comicsRepository;
	}

	public Page<Comics> query(ComicsRestBeanParams comicsRestBeanParams) {
		ComicsRequestDTO comicsRequestDTO = comicsRestMapper.map(comicsRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(comicsRestBeanParams);
		return comicsRepository.findMatching(comicsRequestDTO, pageRequest);
	}

}
