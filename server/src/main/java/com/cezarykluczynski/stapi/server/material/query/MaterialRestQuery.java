package com.cezarykluczynski.stapi.server.material.query;

import com.cezarykluczynski.stapi.model.material.dto.MaterialRequestDTO;
import com.cezarykluczynski.stapi.model.material.entity.Material;
import com.cezarykluczynski.stapi.model.material.repository.MaterialRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.material.dto.MaterialRestBeanParams;
import com.cezarykluczynski.stapi.server.material.mapper.MaterialBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MaterialRestQuery {

	private final MaterialBaseRestMapper materialBaseRestMapper;

	private final PageMapper pageMapper;

	private final MaterialRepository materialRepository;

	public MaterialRestQuery(MaterialBaseRestMapper materialBaseRestMapper, PageMapper pageMapper, MaterialRepository materialRepository) {
		this.materialBaseRestMapper = materialBaseRestMapper;
		this.pageMapper = pageMapper;
		this.materialRepository = materialRepository;
	}

	public Page<Material> query(MaterialRestBeanParams materialRestBeanParams) {
		MaterialRequestDTO materialRequestDTO = materialBaseRestMapper.mapBase(materialRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(materialRestBeanParams);
		return materialRepository.findMatching(materialRequestDTO, pageRequest);
	}

}
