package com.cezarykluczynski.stapi.server.medical_condition.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionBase;
import com.cezarykluczynski.stapi.model.medical_condition.dto.MedicalConditionRequestDTO;
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.medical_condition.dto.MedicalConditionRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface MedicalConditionBaseRestMapper {

	MedicalConditionRequestDTO mapBase(MedicalConditionRestBeanParams medicalConditionRestBeanParams);

	MedicalConditionBase mapBase(MedicalCondition medicalCondition);

	List<MedicalConditionBase> mapBase(List<MedicalCondition> medicalConditionList);

}
