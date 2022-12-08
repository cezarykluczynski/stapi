package com.cezarykluczynski.stapi.server.occupation.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.OccupationFull;
import com.cezarykluczynski.stapi.client.v1.soap.OccupationFullRequest;
import com.cezarykluczynski.stapi.model.occupation.dto.OccupationRequestDTO;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseSoapMapper.class, RequestSortSoapMapper.class})
public interface OccupationFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "artsOccupation", ignore = true)
	@Mapping(target = "communicationOccupation", ignore = true)
	@Mapping(target = "economicOccupation", ignore = true)
	@Mapping(target = "educationOccupation", ignore = true)
	@Mapping(target = "entertainmentOccupation", ignore = true)
	@Mapping(target = "illegalOccupation", ignore = true)
	@Mapping(target = "legalOccupation", ignore = true)
	@Mapping(target = "medicalOccupation", ignore = true)
	@Mapping(target = "scientificOccupation", ignore = true)
	@Mapping(target = "sportsOccupation", ignore = true)
	@Mapping(target = "victualOccupation", ignore = true)
	@Mapping(target = "sort", ignore = true)
	OccupationRequestDTO mapFull(OccupationFullRequest occupationFullRequest);

	OccupationFull mapFull(Occupation occupation);

}
