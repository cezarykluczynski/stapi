package com.cezarykluczynski.stapi.server.location.query;

import com.cezarykluczynski.stapi.model.location.dto.LocationRequestDTO;
import com.cezarykluczynski.stapi.model.location.entity.Location;
import com.cezarykluczynski.stapi.model.location.repository.LocationRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.location.dto.LocationRestBeanParams;
import com.cezarykluczynski.stapi.server.location.mapper.LocationBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class LocationRestQuery {

	private final LocationBaseRestMapper locationBaseRestMapper;

	private final PageMapper pageMapper;

	private final LocationRepository locationRepository;

	public LocationRestQuery(LocationBaseRestMapper locationBaseRestMapper, PageMapper pageMapper, LocationRepository locationRepository) {
		this.locationBaseRestMapper = locationBaseRestMapper;
		this.pageMapper = pageMapper;
		this.locationRepository = locationRepository;
	}

	public Page<Location> query(LocationRestBeanParams locationRestBeanParams) {
		LocationRequestDTO locationRequestDTO = locationBaseRestMapper.mapBase(locationRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(locationRestBeanParams);
		return locationRepository.findMatching(locationRequestDTO, pageRequest);
	}

}
