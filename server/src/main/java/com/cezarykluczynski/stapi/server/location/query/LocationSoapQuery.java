package com.cezarykluczynski.stapi.server.location.query;

import com.cezarykluczynski.stapi.client.v1.soap.LocationBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.LocationFullRequest;
import com.cezarykluczynski.stapi.model.location.dto.LocationRequestDTO;
import com.cezarykluczynski.stapi.model.location.entity.Location;
import com.cezarykluczynski.stapi.model.location.repository.LocationRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.location.mapper.LocationBaseSoapMapper;
import com.cezarykluczynski.stapi.server.location.mapper.LocationFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class LocationSoapQuery {

	private final LocationBaseSoapMapper locationBaseSoapMapper;

	private final LocationFullSoapMapper locationFullSoapMapper;

	private final PageMapper pageMapper;

	private final LocationRepository locationRepository;

	public LocationSoapQuery(LocationBaseSoapMapper locationBaseSoapMapper, LocationFullSoapMapper locationFullSoapMapper, PageMapper pageMapper,
			LocationRepository locationRepository) {
		this.locationBaseSoapMapper = locationBaseSoapMapper;
		this.locationFullSoapMapper = locationFullSoapMapper;
		this.pageMapper = pageMapper;
		this.locationRepository = locationRepository;
	}

	public Page<Location> query(LocationBaseRequest locationBaseRequest) {
		LocationRequestDTO locationRequestDTO = locationBaseSoapMapper.mapBase(locationBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(locationBaseRequest.getPage());
		return locationRepository.findMatching(locationRequestDTO, pageRequest);
	}

	public Page<Location> query(LocationFullRequest locationFullRequest) {
		LocationRequestDTO seriesRequestDTO = locationFullSoapMapper.mapFull(locationFullRequest);
		return locationRepository.findMatching(seriesRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
