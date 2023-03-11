package com.cezarykluczynski.stapi.server.common.mapper;

import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2Type;
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

	default com.cezarykluczynski.stapi.client.rest.model.Gender mapGenderFromEntityEnumToRestEnum(Gender gender) {
		return gender == null ? null : com.cezarykluczynski.stapi.client.rest.model.Gender.valueOf(gender.name());
	}

	default Gender mapGenderFromRestEnumToEntityEnum(com.cezarykluczynski.stapi.client.rest.model.Gender gender) {
		return gender == null ? null : Gender.valueOf(gender.name());
	}

	default com.cezarykluczynski.stapi.client.rest.model.MaritalStatus mapMaritalStatusFromEntityEnumToRestEnum(MaritalStatus maritalStatus) {
		return maritalStatus == null ? null : com.cezarykluczynski.stapi.client.rest.model.MaritalStatus.valueOf(maritalStatus.name());
	}

	default com.cezarykluczynski.stapi.client.rest.model.BloodType mapBloodTypeFromEntityEnumToRestEnum(BloodType bloodType) {
		return bloodType == null ? null : com.cezarykluczynski.stapi.client.rest.model.BloodType.valueOf(bloodType.name());
	}

	default com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectType mapAstronomicalObjectTypeFromEntityEnumToRestEnum(
			AstronomicalObjectType astronomicalObjectType) {
		try {
			return astronomicalObjectType == null ? null : com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectType
					.valueOf(astronomicalObjectType.name());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	default AstronomicalObjectV2Type mapAstronomicalObjectTypeFromEntityEnumToRestV2Enum(
			AstronomicalObjectType astronomicalObjectType) {
		return astronomicalObjectType == null ? null : AstronomicalObjectV2Type.valueOf(astronomicalObjectType.name());
	}

	default AstronomicalObjectType mapAstronomicalObjectTypeFromRestEnumToEntityEnum(
			com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectType astronomicalObjectType) {
		return astronomicalObjectType == null ? null : AstronomicalObjectType.valueOf(astronomicalObjectType.name());
	}

	default AstronomicalObjectType mapAstronomicalObjectV2TypeFromRestEnumToEntityEnum(AstronomicalObjectV2Type astronomicalObjectType) {
		return astronomicalObjectType == null ? null : AstronomicalObjectType.valueOf(astronomicalObjectType.name());
	}

	default com.cezarykluczynski.stapi.client.rest.model.ReferenceType mapReferenceTypeFromEntityEnumToRestEnum(
			ReferenceType referenceType) {
		return referenceType == null ? null : com.cezarykluczynski.stapi.client.rest.model.ReferenceType.valueOf(referenceType.name());
	}

	default com.cezarykluczynski.stapi.client.rest.model.VideoReleaseFormat mapVideoReleaseFormatFromEntityEnumToRestEnum(
			VideoReleaseFormat videoReleaseFormat) {
		return videoReleaseFormat == null ? null : com.cezarykluczynski.stapi.client.rest.model.VideoReleaseFormat
				.valueOf(videoReleaseFormat.name());
	}

	default com.cezarykluczynski.stapi.client.rest.model.ProductionRunUnit mapProductionRunUnitFromEntityEnumToRestEnum(
			ProductionRunUnit productionRunUnit) {
		return productionRunUnit == null ? null : com.cezarykluczynski.stapi.client.rest.model.ProductionRunUnit
				.valueOf(productionRunUnit.name());
	}

	default ProductionRunUnit mapProductionRunUnitFromRestEnumToEntityEnum(
			com.cezarykluczynski.stapi.client.rest.model.ProductionRunUnit productionRunUnit) {
		return productionRunUnit == null ? null : ProductionRunUnit.valueOf(productionRunUnit.name());
	}

}
