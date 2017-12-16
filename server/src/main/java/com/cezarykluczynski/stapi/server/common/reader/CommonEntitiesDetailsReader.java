package com.cezarykluczynski.stapi.server.common.reader;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.service.EntityMetadataProvider;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO;
import liquibase.util.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
class CommonEntitiesDetailsReader {

	private final EntityMetadataProvider entityMetadataProvider;

	private Map<String, String> simpleClassNameToSymbolMap;

	private Map<String, ClassMetadata> classNameToMetadataMap;

	CommonEntitiesDetailsReader(EntityMetadataProvider entityMetadataProvider) {
		this.entityMetadataProvider = entityMetadataProvider;
	}

	RestEndpointDetailsDTO details() {
		init();

		List<RestEndpointDetailDTO> restEndpointDetailsDTOList = classNameToMetadataMap
				.entrySet()
				.stream()
				.map(this::map)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());

		return new RestEndpointDetailsDTO(restEndpointDetailsDTOList);
	}

	private synchronized void init() {
		if (simpleClassNameToSymbolMap == null) {
			simpleClassNameToSymbolMap = getSimpleClassNameToSymbolMap();
		}

		if (classNameToMetadataMap == null) {
			classNameToMetadataMap = entityMetadataProvider.provideClassNameToMetadataMap();
		}
	}

	private Map<String, String> getSimpleClassNameToSymbolMap() {
		return entityMetadataProvider.provideClassNameToSymbolMap()
				.entrySet()
				.stream()
				.map(entry -> {
					String[] entityFullNameParts = entry.getKey().split("\\.");
					String entitySimpleName = entityFullNameParts[entityFullNameParts.length - 1];
					return Pair.of(entitySimpleName, entry.getValue());
				})
				.collect(Collectors.toMap(Pair::getKey, Pair::getValue));
	}

	private RestEndpointDetailDTO map(Map.Entry<String, ClassMetadata> entry) {
		RestEndpointDetailDTO restEndpointDetailDTO = new RestEndpointDetailDTO();
		Class clazz = entry.getValue().getMappedClass();
		TrackedEntity trackedEntity = (TrackedEntity) clazz.getAnnotation(TrackedEntity.class);
		if (!trackedEntity.apiEntity()) {
			return null;
		}

		String entityName = clazz.getSimpleName();

		restEndpointDetailDTO.setName(entityName);
		restEndpointDetailDTO.setType(trackedEntity.type());
		restEndpointDetailDTO.setApiEndpointSuffix(StringUtils.lowerCaseFirst(entityName));
		restEndpointDetailDTO.setSymbol(simpleClassNameToSymbolMap.get(entityName));
		restEndpointDetailDTO.setSingularName(trackedEntity.singularName());
		restEndpointDetailDTO.setPluralName(trackedEntity.pluralName());
		return restEndpointDetailDTO;
	}

}
