package com.cezarykluczynski.stapi.server.common.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectTypeEnum;
import com.cezarykluczynski.stapi.client.v1.soap.BloodTypeEnum;
import com.cezarykluczynski.stapi.client.v1.soap.GenderEnum;
import com.cezarykluczynski.stapi.client.v1.soap.MaritalStatusEnum;
import com.cezarykluczynski.stapi.client.v1.soap.ProductionRunUnitEnum;
import com.cezarykluczynski.stapi.client.v1.soap.ReferenceTypeEnum;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFormatEnum;
import com.cezarykluczynski.stapi.model.astronomical_object.entity.enums.AstronomicalObjectType;
import com.cezarykluczynski.stapi.model.common.entity.enums.BloodType;
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender;
import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus;
import com.cezarykluczynski.stapi.model.reference.entity.enums.ReferenceType;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.enums.ProductionRunUnit;
import com.cezarykluczynski.stapi.model.video_release.entity.enums.VideoReleaseFormat;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
@SuppressWarnings("ClassFanOutComplexity")
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

	default com.cezarykluczynski.stapi.client.v1.rest.model.MaritalStatus mapMaritalStatusFromEntityEnumToRestEnum(MaritalStatus maritalStatus) {
		return maritalStatus == null ? null : com.cezarykluczynski.stapi.client.v1.rest.model.MaritalStatus.valueOf(maritalStatus.name());
	}

	default BloodTypeEnum mapBloodTypeFromEntityEnumToSoapEnum(BloodType bloodType) {
		return bloodType == null ? null : BloodTypeEnum.valueOf(bloodType.name());
	}

	default com.cezarykluczynski.stapi.client.v1.rest.model.BloodType mapBloodTypeFromEntityEnumToRestEnum(BloodType bloodType) {
		return bloodType == null ? null : com.cezarykluczynski.stapi.client.v1.rest.model.BloodType.valueOf(bloodType.name());
	}

	default AstronomicalObjectTypeEnum mapAstronomicalObjectTypeFromEntityEnumToSoapEnum(AstronomicalObjectType astronomicalObjectType) {
		return astronomicalObjectType == null ? null : AstronomicalObjectTypeEnum.valueOf(astronomicalObjectType.name());
	}

	default AstronomicalObjectType mapAstronomicalObjectTypeFromSoapEnumToEntityEnum(AstronomicalObjectTypeEnum astronomicalObjectTypeEnum) {
		return astronomicalObjectTypeEnum == null ? null : AstronomicalObjectType.valueOf(astronomicalObjectTypeEnum.name());
	}

	default com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectType mapAstronomicalObjectTypeFromEntityEnumToRestEnum(
			AstronomicalObjectType astronomicalObjectType) {
		return astronomicalObjectType == null ? null : com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectType
				.valueOf(astronomicalObjectType.name());
	}

	default AstronomicalObjectType mapAstronomicalObjectTypeFromRestEnumToEntityEnum(
			com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectType astronomicalObjectType) {
		return astronomicalObjectType == null ? null : AstronomicalObjectType.valueOf(astronomicalObjectType.name());
	}

	default ReferenceTypeEnum mapReferenceTypeFromEntityEnumToSoapEnum(ReferenceType referenceType) {
		return referenceType == null ? null : ReferenceTypeEnum.valueOf(referenceType.name());
	}

	default com.cezarykluczynski.stapi.client.v1.rest.model.ReferenceType mapReferenceTypeFromEntityEnumToRestEnum(
			ReferenceType referenceType) {
		return referenceType == null ? null : com.cezarykluczynski.stapi.client.v1.rest.model.ReferenceType.valueOf(referenceType.name());
	}

	default VideoReleaseFormatEnum mapVideoReleaseFormatFromEntityEnumToSoapEnum(VideoReleaseFormat videoReleaseFormat) {
		if (videoReleaseFormat == null) {
			return null;
		}

		switch (videoReleaseFormat) {
			case BLU_RAY_4K_UHD:
				return VideoReleaseFormatEnum.BLU_RAY_4_K_UHD;
			default:
				return VideoReleaseFormatEnum.valueOf(videoReleaseFormat.name());
		}
	}

	default com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseFormat mapVideoReleaseFormatFromEntityEnumToRestEnum(
			VideoReleaseFormat videoReleaseFormat) {
		return videoReleaseFormat == null ? null : com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseFormat
				.valueOf(videoReleaseFormat.name());
	}

	default ProductionRunUnitEnum mapProductionRunUnitFromEntityEnumToSoapEnum(ProductionRunUnit productionRunUnit) {
		return productionRunUnit == null ? null : ProductionRunUnitEnum.valueOf(productionRunUnit.name());
	}

	default ProductionRunUnit mapProductionRunUnitFromSoapEnumToEntityEnum(ProductionRunUnitEnum productionRunUnitEnum) {
		return productionRunUnitEnum == null ? null : ProductionRunUnit.valueOf(productionRunUnitEnum.name());
	}

	default com.cezarykluczynski.stapi.client.v1.rest.model.ProductionRunUnit mapProductionRunUnitFromEntityEnumToRestEnum(
			ProductionRunUnit productionRunUnit) {
		return productionRunUnit == null ? null : com.cezarykluczynski.stapi.client.v1.rest.model.ProductionRunUnit
				.valueOf(productionRunUnit.name());
	}

	default ProductionRunUnit mapProductionRunUnitFromRestEnumToEntityEnum(
			com.cezarykluczynski.stapi.client.v1.rest.model.ProductionRunUnit productionRunUnit) {
		return productionRunUnit == null ? null : ProductionRunUnit.valueOf(productionRunUnit.name());
	}

}
