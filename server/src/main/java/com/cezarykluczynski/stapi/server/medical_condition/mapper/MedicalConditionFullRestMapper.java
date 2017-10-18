package com.cezarykluczynski.stapi.server.medical_condition.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionFull;
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface MedicalConditionFullRestMapper {

	MedicalConditionFull mapFull(MedicalCondition medicalCondition);

}
