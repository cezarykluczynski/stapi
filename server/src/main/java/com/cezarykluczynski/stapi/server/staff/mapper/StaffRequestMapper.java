package com.cezarykluczynski.stapi.server.staff.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.StaffRequest;
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.staff.dto.StaffRestBeanParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class, DateMapper.class})
public interface StaffRequestMapper {

	@Mappings({
			@Mapping(source = "dateOfBirth.dateFrom", target = "dateOfBirthFrom"),
			@Mapping(source = "dateOfBirth.dateTo", target = "dateOfBirthTo"),
			@Mapping(source = "dateOfDeath.dateFrom", target = "dateOfDeathFrom"),
			@Mapping(source = "dateOfDeath.dateTo", target = "dateOfDeathTo")
	})
	StaffRequestDTO map(StaffRequest performerRequest);

	StaffRequestDTO map(StaffRestBeanParams performerRestBeanParams);

}
