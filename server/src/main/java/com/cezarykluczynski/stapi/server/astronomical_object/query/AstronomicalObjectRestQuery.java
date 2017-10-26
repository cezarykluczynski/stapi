package com.cezarykluczynski.stapi.server.astronomical_object.query;

import com.cezarykluczynski.stapi.model.astronomical_object.dto.AstronomicalObjectRequestDTO;
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.astronomical_object.repository.AstronomicalObjectRepository;
import com.cezarykluczynski.stapi.server.astronomical_object.dto.AstronomicalObjectRestBeanParams;
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectBaseRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AstronomicalObjectRestQuery {

	private final AstronomicalObjectBaseRestMapper astronomicalObjectBaseRestMapper;

	private final PageMapper pageMapper;

	private final AstronomicalObjectRepository astronomicalObjectRepository;

	public AstronomicalObjectRestQuery(AstronomicalObjectBaseRestMapper astronomicalObjectBaseRestMapper, PageMapper pageMapper,
			AstronomicalObjectRepository astronomicalObjectRepository) {
		this.astronomicalObjectBaseRestMapper = astronomicalObjectBaseRestMapper;
		this.pageMapper = pageMapper;
		this.astronomicalObjectRepository = astronomicalObjectRepository;
	}

	public Page<AstronomicalObject> query(AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams) {
		AstronomicalObjectRequestDTO astronomicalObjectRequestDTO = astronomicalObjectBaseRestMapper.mapBase(astronomicalObjectRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(astronomicalObjectRestBeanParams);
		return astronomicalObjectRepository.findMatching(astronomicalObjectRequestDTO, pageRequest);
	}

}
