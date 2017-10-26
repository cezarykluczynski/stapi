package com.cezarykluczynski.stapi.server.spacecraft_class.query;

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFullRequest;
import com.cezarykluczynski.stapi.model.spacecraft_class.dto.SpacecraftClassRequestDTO;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.model.spacecraft_class.repository.SpacecraftClassRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassBaseSoapMapper;
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftClassSoapQuery {

	private final SpacecraftClassBaseSoapMapper spacecraftClassBaseSoapMapper;

	private final SpacecraftClassFullSoapMapper spacecraftClassFullSoapMapper;

	private final PageMapper pageMapper;

	private final SpacecraftClassRepository spacecraftClassRepository;

	public SpacecraftClassSoapQuery(SpacecraftClassBaseSoapMapper spacecraftClassBaseSoapMapper,
			SpacecraftClassFullSoapMapper spacecraftClassFullSoapMapper, PageMapper pageMapper, SpacecraftClassRepository spacecraftClassRepository) {
		this.spacecraftClassBaseSoapMapper = spacecraftClassBaseSoapMapper;
		this.spacecraftClassFullSoapMapper = spacecraftClassFullSoapMapper;
		this.pageMapper = pageMapper;
		this.spacecraftClassRepository = spacecraftClassRepository;
	}

	public Page<SpacecraftClass> query(SpacecraftClassBaseRequest spacecraftClassBaseRequest) {
		SpacecraftClassRequestDTO spacecraftClassRequestDTO = spacecraftClassBaseSoapMapper.mapBase(spacecraftClassBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(spacecraftClassBaseRequest.getPage());
		return spacecraftClassRepository.findMatching(spacecraftClassRequestDTO, pageRequest);
	}

	public Page<SpacecraftClass> query(SpacecraftClassFullRequest spacecraftClassFullRequest) {
		SpacecraftClassRequestDTO spacecraftClassRequestDTO = spacecraftClassFullSoapMapper.mapFull(spacecraftClassFullRequest);
		return spacecraftClassRepository.findMatching(spacecraftClassRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
