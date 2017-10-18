package com.cezarykluczynski.stapi.server.medical_condition.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBase;
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBaseRequest;
import com.cezarykluczynski.stapi.model.medical_condition.dto.MedicalConditionRequestDTO;
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface MedicalConditionBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	MedicalConditionRequestDTO mapBase(MedicalConditionBaseRequest medicalConditionBaseRequest);

	MedicalConditionBase mapBase(MedicalCondition medicalCondition);

	List<MedicalConditionBase> mapBase(List<MedicalCondition> medicalConditionList);

}
