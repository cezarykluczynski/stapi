package com.cezarykluczynski.stapi.server.technology.query;

import com.cezarykluczynski.stapi.model.technology.dto.TechnologyRequestDTO;
import com.cezarykluczynski.stapi.model.technology.entity.Technology;
import com.cezarykluczynski.stapi.model.technology.repository.TechnologyRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.technology.dto.TechnologyRestBeanParams;
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TechnologyRestQuery {

	private final TechnologyBaseRestMapper technologyBaseRestMapper;

	private final PageMapper pageMapper;

	private final TechnologyRepository technologyRepository;

	public TechnologyRestQuery(TechnologyBaseRestMapper technologyBaseRestMapper, PageMapper pageMapper, TechnologyRepository technologyRepository) {
		this.technologyBaseRestMapper = technologyBaseRestMapper;
		this.pageMapper = pageMapper;
		this.technologyRepository = technologyRepository;
	}

	public Page<Technology> query(TechnologyRestBeanParams technologyRestBeanParams) {
		TechnologyRequestDTO technologyRequestDTO = technologyBaseRestMapper.mapBase(technologyRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(technologyRestBeanParams);
		return technologyRepository.findMatching(technologyRequestDTO, pageRequest);
	}

}
