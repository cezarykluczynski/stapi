package com.cezarykluczynski.stapi.server.spacecraft_class.query;

import com.cezarykluczynski.stapi.model.spacecraft_class.dto.SpacecraftClassRequestDTO;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.model.spacecraft_class.repository.SpacecraftClassRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.spacecraft_class.dto.SpacecraftClassRestBeanParams;
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftClassRestQuery {

	private final SpacecraftClassBaseRestMapper spacecraftClassBaseRestMapper;

	private final PageMapper pageMapper;

	private final SpacecraftClassRepository spacecraftClassRepository;

	public SpacecraftClassRestQuery(SpacecraftClassBaseRestMapper spacecraftClassBaseRestMapper, PageMapper pageMapper,
			SpacecraftClassRepository spacecraftClassRepository) {
		this.spacecraftClassBaseRestMapper = spacecraftClassBaseRestMapper;
		this.pageMapper = pageMapper;
		this.spacecraftClassRepository = spacecraftClassRepository;
	}

	public Page<SpacecraftClass> query(SpacecraftClassRestBeanParams spacecraftClassRestBeanParams) {
		SpacecraftClassRequestDTO spacecraftClassRequestDTO = spacecraftClassBaseRestMapper.mapBase(spacecraftClassRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(spacecraftClassRestBeanParams);
		return spacecraftClassRepository.findMatching(spacecraftClassRequestDTO, pageRequest);
	}

}
