package com.cezarykluczynski.stapi.server.spacecraft.query;

import com.cezarykluczynski.stapi.model.spacecraft.dto.SpacecraftRequestDTO;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import com.cezarykluczynski.stapi.model.spacecraft.repository.SpacecraftRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.spacecraft.dto.SpacecraftRestBeanParams;
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftRestQuery {

	private final SpacecraftBaseRestMapper spacecraftBaseRestMapper;

	private final PageMapper pageMapper;

	private final SpacecraftRepository spacecraftRepository;

	public SpacecraftRestQuery(SpacecraftBaseRestMapper spacecraftBaseRestMapper, PageMapper pageMapper, SpacecraftRepository spacecraftRepository) {
		this.spacecraftBaseRestMapper = spacecraftBaseRestMapper;
		this.pageMapper = pageMapper;
		this.spacecraftRepository = spacecraftRepository;
	}

	public Page<Spacecraft> query(SpacecraftRestBeanParams spacecraftRestBeanParams) {
		SpacecraftRequestDTO spacecraftRequestDTO = spacecraftBaseRestMapper.mapBase(spacecraftRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(spacecraftRestBeanParams);
		return spacecraftRepository.findMatching(spacecraftRequestDTO, pageRequest);
	}

}
