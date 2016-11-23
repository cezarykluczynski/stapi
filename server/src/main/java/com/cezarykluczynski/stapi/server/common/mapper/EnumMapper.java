package com.cezarykluczynski.stapi.server.common.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.BloodTypeEnum;
import com.cezarykluczynski.stapi.client.v1.soap.GenderEnum;
import com.cezarykluczynski.stapi.client.v1.soap.MaritalStatusEnum;
import com.cezarykluczynski.stapi.model.common.entity.BloodType;
import com.cezarykluczynski.stapi.model.common.entity.Gender;
import com.cezarykluczynski.stapi.model.common.entity.MaritalStatus;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface EnumMapper {

	default GenderEnum mapGenderFromEntityEnumToSoapEnum(Gender gender) {
		return gender == null ? null : GenderEnum.valueOf(gender.name());
	}

	default Gender mapGenderFromSoapEnumToEntityEnum(GenderEnum genderEnum) {
		return genderEnum == null ? null : Gender.valueOf(genderEnum.name());
	}

	default com.cezarykluczynski.stapi.client.v1.rest.model.Gender mapGenderFromEntityEnumToRestEnum(Gender gender) {
		return gender == null ? null : com.cezarykluczynski.stapi.client.v1.rest.model.Gender.valueOf(gender.name());
	}

	default Gender mapGenderFromRestEnumToEntityEnum(com.cezarykluczynski.stapi.client.v1.rest.model.Gender gender) {
		return gender == null ? null : Gender.valueOf(gender.name());
	}

	default MaritalStatusEnum mapMaritalStatusFromEntityEnumToSoapEnum(MaritalStatus maritalStatus) {
		return maritalStatus == null ? null : MaritalStatusEnum.valueOf(maritalStatus.name());
	}

	default MaritalStatus mapMaritalStatusFromSoapEnumToEntityEnum(MaritalStatusEnum maritalStatusEnum) {
		return maritalStatusEnum == null ? null : MaritalStatus.valueOf(maritalStatusEnum.name());
	}

	default com.cezarykluczynski.stapi.client.v1.rest.model.MaritalStatus mapMaritalStatusFromEntityEnumToRestEnum(MaritalStatus maritalStatus) {
		return maritalStatus == null ? null : com.cezarykluczynski.stapi.client.v1.rest.model.MaritalStatus.valueOf(maritalStatus.name());
	}

	default MaritalStatus mapMaritalStatusFromRestEnumToEntityEnum(com.cezarykluczynski.stapi.client.v1.rest.model.MaritalStatus maritalStatus) {
		return maritalStatus == null ? null : MaritalStatus.valueOf(maritalStatus.name());
	}

	default BloodTypeEnum mapBloodTypeFromEntityEnumToSoapEnum(BloodType bloodType) {
		return bloodType == null ? null : BloodTypeEnum.valueOf(bloodType.name());
	}

	default BloodType mapBloodTypeFromSoapEnumToEntityEnum(BloodTypeEnum bloodTypeEnum) {
		return bloodTypeEnum == null ? null : BloodType.valueOf(bloodTypeEnum.name());
	}

	default com.cezarykluczynski.stapi.client.v1.rest.model.BloodType mapBloodTypeFromEntityEnumToRestEnum(BloodType bloodType) {
		return bloodType == null ? null : com.cezarykluczynski.stapi.client.v1.rest.model.BloodType.valueOf(bloodType.name());
	}

	default BloodType mapBloodTypeFromRestEnumToEntityEnum(com.cezarykluczynski.stapi.client.v1.rest.model.BloodType bloodType) {
		return bloodType == null ? null : BloodType.valueOf(bloodType.name());
	}

}
