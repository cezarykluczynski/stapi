package com.cezarykluczynski.stapi.model.location.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.location.dto.LocationRequestDTO;
import com.cezarykluczynski.stapi.model.location.entity.Location;

public interface LocationRepositoryCustom extends CriteriaMatcher<LocationRequestDTO, Location> {
}
