package com.cezarykluczynski.stapi.server.astronomicalObject.query;

import com.cezarykluczynski.stapi.model.astronomicalObject.dto.AstronomicalObjectRequestDTO;
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.astronomicalObject.repository.AstronomicalObjectRepository;
import com.cezarykluczynski.stapi.server.astronomicalObject.dto.AstronomicalObjectRestBeanParams;
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class AstronomicalObjectRestQuery {

	private AstronomicalObjectRestMapper astronomicalObjectRestMapper;

	private PageMapper pageMapper;

	private AstronomicalObjectRepository astronomicalObjectRepository;

	@Inject
	public AstronomicalObjectRestQuery(AstronomicalObjectRestMapper astronomicalObjectRestMapper, PageMapper pageMapper,
			AstronomicalObjectRepository astronomicalObjectRepository) {
		this.astronomicalObjectRestMapper = astronomicalObjectRestMapper;
		this.pageMapper = pageMapper;
		this.astronomicalObjectRepository = astronomicalObjectRepository;
	}

	public Page<AstronomicalObject> query(AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams) {
		AstronomicalObjectRequestDTO astronomicalObjectRequestDTO = astronomicalObjectRestMapper.mapBase(astronomicalObjectRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(astronomicalObjectRestBeanParams);
		return astronomicalObjectRepository.findMatching(astronomicalObjectRequestDTO, pageRequest);
	}

}
