package com.cezarykluczynski.stapi.server.staff.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.StaffRequest;
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestOrderMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EnumMapper.class, RequestOrderMapper.class})
public interface StaffSoapMapper {

	@Mappings({
			@Mapping(source = "dateOfBirth.dateFrom", target = "dateOfBirthFrom"),
			@Mapping(source = "dateOfBirth.dateTo", target = "dateOfBirthTo"),
			@Mapping(source = "dateOfDeath.dateFrom", target = "dateOfDeathFrom"),
			@Mapping(source = "dateOfDeath.dateTo", target = "dateOfDeathTo")
	})
	StaffRequestDTO map(StaffRequest performerRequest);

	com.cezarykluczynski.stapi.client.v1.soap.Staff map(Staff series);

	List<com.cezarykluczynski.stapi.client.v1.soap.Staff> map(List<Staff> series);

}
