package com.cezarykluczynski.stapi.server.medical_condition.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionHeader;
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicalConditionHeaderRestMapper {

	MedicalConditionHeader map(MedicalCondition medicalCondition);

	List<MedicalConditionHeader> map(List<MedicalCondition> medicalCondition);

}
