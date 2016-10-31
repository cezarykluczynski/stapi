package com.cezarykluczynski.stapi.server.performer.mapper;

import com.cezarykluczynski.stapi.client.soap.PerformerRequest;
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class, DateMapper.class})
public interface PerformerRequestMapper {

	@Mappings({
			@Mapping(source = "dateOfBirth.dateFrom", target = "dateOfBirthFrom"),
			@Mapping(source = "dateOfBirth.dateTo", target = "dateOfBirthTo"),
			@Mapping(source = "dateOfDeath.dateFrom", target = "dateOfDeathFrom"),
			@Mapping(source = "dateOfDeath.dateTo", target = "dateOfDeathTo")
	})
	PerformerRequestDTO map(PerformerRequest performerRequest);

	PerformerRequestDTO map(PerformerRestBeanParams performerRestBeanParams);

}
