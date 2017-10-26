package com.cezarykluczynski.stapi.server.material.query;

import com.cezarykluczynski.stapi.client.v1.soap.MaterialBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialFullRequest;
import com.cezarykluczynski.stapi.model.material.dto.MaterialRequestDTO;
import com.cezarykluczynski.stapi.model.material.entity.Material;
import com.cezarykluczynski.stapi.model.material.repository.MaterialRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.material.mapper.MaterialBaseSoapMapper;
import com.cezarykluczynski.stapi.server.material.mapper.MaterialFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MaterialSoapQuery {

	private final MaterialBaseSoapMapper materialBaseSoapMapper;

	private final MaterialFullSoapMapper materialFullSoapMapper;

	private final PageMapper pageMapper;

	private final MaterialRepository materialRepository;

	public MaterialSoapQuery(MaterialBaseSoapMapper materialBaseSoapMapper, MaterialFullSoapMapper materialFullSoapMapper, PageMapper pageMapper,
			MaterialRepository materialRepository) {
		this.materialBaseSoapMapper = materialBaseSoapMapper;
		this.materialFullSoapMapper = materialFullSoapMapper;
		this.pageMapper = pageMapper;
		this.materialRepository = materialRepository;
	}

	public Page<Material> query(MaterialBaseRequest materialBaseRequest) {
		MaterialRequestDTO materialRequestDTO = materialBaseSoapMapper.mapBase(materialBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(materialBaseRequest.getPage());
		return materialRepository.findMatching(materialRequestDTO, pageRequest);
	}

	public Page<Material> query(MaterialFullRequest materialFullRequest) {
		MaterialRequestDTO seriesRequestDTO = materialFullSoapMapper.mapFull(materialFullRequest);
		return materialRepository.findMatching(seriesRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
