package com.cezarykluczynski.stapi.server.spacecraft.query;

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFullRequest;
import com.cezarykluczynski.stapi.model.spacecraft.dto.SpacecraftRequestDTO;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import com.cezarykluczynski.stapi.model.spacecraft.repository.SpacecraftRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftBaseSoapMapper;
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftSoapQuery {

	private final SpacecraftBaseSoapMapper spacecraftBaseSoapMapper;

	private final SpacecraftFullSoapMapper spacecraftFullSoapMapper;

	private final PageMapper pageMapper;

	private final SpacecraftRepository spacecraftRepository;

	public SpacecraftSoapQuery(SpacecraftBaseSoapMapper spacecraftBaseSoapMapper, SpacecraftFullSoapMapper spacecraftFullSoapMapper,
			PageMapper pageMapper, SpacecraftRepository spacecraftRepository) {
		this.spacecraftBaseSoapMapper = spacecraftBaseSoapMapper;
		this.spacecraftFullSoapMapper = spacecraftFullSoapMapper;
		this.pageMapper = pageMapper;
		this.spacecraftRepository = spacecraftRepository;
	}

	public Page<Spacecraft> query(SpacecraftBaseRequest spacecraftBaseRequest) {
		SpacecraftRequestDTO spacecraftRequestDTO = spacecraftBaseSoapMapper.mapBase(spacecraftBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(spacecraftBaseRequest.getPage());
		return spacecraftRepository.findMatching(spacecraftRequestDTO, pageRequest);
	}

	public Page<Spacecraft> query(SpacecraftFullRequest spacecraftFullRequest) {
		SpacecraftRequestDTO spacecraftRequestDTO = spacecraftFullSoapMapper.mapFull(spacecraftFullRequest);
		return spacecraftRepository.findMatching(spacecraftRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
