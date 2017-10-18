package com.cezarykluczynski.stapi.server.medical_condition.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFull;
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFullRequest;
import com.cezarykluczynski.stapi.model.medical_condition.dto.MedicalConditionRequestDTO;
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface MedicalConditionFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "psychologicalCondition", ignore = true)
	@Mapping(target = "sort", ignore = true)
	MedicalConditionRequestDTO mapFull(MedicalConditionFullRequest medicalConditionFullRequest);

	MedicalConditionFull mapFull(MedicalCondition medicalCondition);

}
