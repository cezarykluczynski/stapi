package com.cezarykluczynski.stapi.server.occupation.query;

import com.cezarykluczynski.stapi.model.occupation.dto.OccupationRequestDTO;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import com.cezarykluczynski.stapi.model.occupation.repository.OccupationRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.occupation.dto.OccupationRestBeanParams;
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class OccupationRestQuery {

	private final OccupationBaseRestMapper occupationBaseRestMapper;

	private final PageMapper pageMapper;

	private final OccupationRepository occupationRepository;

	public OccupationRestQuery(OccupationBaseRestMapper occupationBaseRestMapper, PageMapper pageMapper, OccupationRepository occupationRepository) {
		this.occupationBaseRestMapper = occupationBaseRestMapper;
		this.pageMapper = pageMapper;
		this.occupationRepository = occupationRepository;
	}

	public Page<Occupation> query(OccupationRestBeanParams occupationRestBeanParams) {
		OccupationRequestDTO occupationRequestDTO = occupationBaseRestMapper.mapBase(occupationRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(occupationRestBeanParams);
		return occupationRepository.findMatching(occupationRequestDTO, pageRequest);
	}

}
